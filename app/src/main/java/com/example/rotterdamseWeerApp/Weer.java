package com.example.rotterdamseWeerApp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weer extends AppCompatActivity {
    public final static String WeatherURL = "https://api.openweathermap.org/data/2.5/weather?lat=51.9225&lon=4.4792&appid=2d7e37d4b3bac1c0ef54957a986ebdfe";
    public TextView txtView;
    public SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Zet de view om naar de weather layout, zodat deze later aangepast kan worden.
        setContentView(R.layout.weather);

        // Sla de view op, zodat deze later gemanipuleerd kan worden.
        txtView = findViewById(R.id.textView);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Start HTTP request
        new RetrieveFeedTask().execute(WeatherURL);
    }


    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urlin) {
            try {
                // Klaarmaken van de  http client
                URL url = new URL(urlin[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                // Read van de remote host
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder sb = new StringBuilder();

                // Stopt buffer in string
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                // Returned een raw string
                String ret = sb.toString();
                return ret;
            } catch (Exception e) {
                Log.e("Rotterdam", "exception", e);
            }

            // Alle return opvangen
            return "";
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                // Parse response als JSON
                JSONObject jsonObject = new JSONObject(json);
                Log.e("tag", jsonObject.toString(4));

                // Default waarde van 'weather' is de descripton. Laat temperatuur zien als je deze toggled
                if (!prefs.getBoolean("show_temp", false)) {
                    JSONArray newJSON = jsonObject.getJSONArray("weather");
                    txtView.setText(newJSON.getJSONObject(0).getString("description"));
                }
                else {
                    JSONObject newJSON = jsonObject.getJSONObject("main");
//                    float newTemp = Float.parseFloat("temp");
//                    float lestTemp = newTemp-273;
//                    string lestTempString = lestTemp.toString();

                    // Toont temperatuur in Kelvin
                    txtView.setText(newJSON.getString("temp") + " K");
                }
            } catch (JSONException e) {
                Log.e("Rotterdam", "exception", e);
            }
        }
    }
}
