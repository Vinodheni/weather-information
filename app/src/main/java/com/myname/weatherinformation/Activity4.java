package com.myname.weatherinformation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;
import android.os.AsyncTask;
import android.app.ProgressDialog;



public class Activity4 extends Activity {
    EditText ed1,ed2,ed3,ed4;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    private String finalUrl = "http://api.openweathermap.org/data/2.5/weather?q=tokyo&mode=xml";
    private String iurl1="http://openweathermap.org/img/w/";
    private String iurl2=".png";
    private String iurl;
    private String finaliurl;
    private HandleXML obj;
    Button b7;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        b7=(Button)findViewById(R.id.button7);

        img = (ImageView)findViewById(R.id.img);

        ed1=(EditText)findViewById(R.id.editText13);
        ed2=(EditText)findViewById(R.id.editText14);
        ed3=(EditText)findViewById(R.id.editText15);
        ed4=(EditText)findViewById(R.id.editText16);



        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed2.setText(finalUrl);

                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;
                ed1.setText("Country     : "+obj.getCountry());
                ed2.setText("Temperature : "+obj.getTemperature());
                ed3.setText("Humidity    : "+obj.getHumidity()+"%");
                ed4.setText("Pressure    : "+obj.getPressure()+"hPa");
                iurl = obj.getIcon();
                finaliurl = iurl1 + iurl + iurl2;


                // TODO Auto-generated method stub
                new LoadImage().execute(finaliurl);

            }




        });
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity4.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                ImageView imgView=(ImageView) findViewById(R.id.img);
                Drawable drawable  = getResources().getDrawable(R.drawable.img3);
                imgView.setImageDrawable(drawable);

            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}