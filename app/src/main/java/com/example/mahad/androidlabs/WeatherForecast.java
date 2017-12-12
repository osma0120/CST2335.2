package com.example.mahad.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//import static com.example.mahad.androidlabs.WeatherForecast.CLASS_NAME;
import static com.example.mahad.androidlabs.ChatDatabaseHelper.CLASS_NAME;
import static java.lang.System.in;

public class WeatherForecast extends Activity {
    protected static String ACTIVITY_NAME = WeatherForecast.class.getSimpleName();
    ProgressBar weatherProgessBar;
    TextView currentText;
    TextView minText;
    TextView maxText;
    ImageView weatherImage;
    String urlString ="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric.";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        setTitle(ACTIVITY_NAME);
        currentText = findViewById(R.id.currentTemp);
        minText = findViewById(R.id.MinTemp);
        maxText = findViewById(R.id.MaxTemp);
        weatherImage = findViewById(R.id.weatherImage);
        weatherProgessBar = findViewById(R.id.weatherBar);
        weatherProgessBar.setVisibility(View.VISIBLE);
        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute(urlString);




    }


    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        String min;
        String max;
        String current;
        Bitmap currentImage;

        @Override
        protected String doInBackground(String... strings){
            try {
                URL url = new URL(strings [0]);
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000/*millseconds*/);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // start the query
                conn.connect();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(),null);
                parser.nextTag();

                while(parser.next() != XmlPullParser.END_DOCUMENT){
                    if (parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }
                    String name = parser.getName();

                    if(name.equals("temperature")) {
                        current = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null,"max");
                        publishProgress(75);
                    } else if(name.equals("weather")){
                        String iconName = parser.getAttributeValue(null, "icon") + ".png";
                        String urlIcon = "http://openweathermap.org/img/w/" + iconName;

                        if(fileExistance(iconName)){
                            Log.i(CLASS_NAME, "Icon Already found");
                            FileInputStream fis = null;
                            try{
                                fis = openFileInput(iconName);
                            }catch (FileNotFoundException e){
                                e.printStackTrace();
                            }
                            currentImage = BitmapFactory.decodeStream(fis);
                        } else {
                            Log.i(CLASS_NAME, "Icon File not Found, downloading");
                            Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(urlIcon).getContent());
                            FileOutputStream outputStream = openFileOutput(iconName , Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            currentImage=image;
                        }
                    }
                }

            }catch(IOException exception){

            }catch(XmlPullParserException pullException){

            }

            return null;



        }

        public void onProgressUpdate(Integer... value){
            weatherProgessBar.setProgress(value [0]);
        }

        public void onPostExecute(String f){
            currentText.setText("Current Temp:" + current);
            minText.setText("Low: " + min);
            maxText.setText("HighL: "+ max);
            weatherImage.setImageBitmap(currentImage);
            weatherProgessBar.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

    }



}
