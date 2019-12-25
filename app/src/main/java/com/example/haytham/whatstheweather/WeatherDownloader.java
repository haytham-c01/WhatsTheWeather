package com.example.haytham.whatstheweather;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherDownloader extends AsyncTask<URL, Void, String> {
    private MainActivity activity;

    WeatherDownloader(MainActivity activity){
        this.activity= activity;
    }

    @Override
    protected String doInBackground(URL... urls) {
        try {
            HttpURLConnection connection= (HttpURLConnection) urls[0].openConnection();
            InputStream in= connection.getInputStream();

            BufferedReader br= new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            StringBuilder sb= new StringBuilder();
            while((line= br.readLine()) != null)
                sb.append(line).append("\n");

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject= new JSONObject(s);
            JSONObject weather= jsonObject.getJSONArray("weather").getJSONObject(0);
            StringBuilder sb= new StringBuilder();
            String main= weather.getString("main");
            String description= weather.getString("description");

            if(main != "" && description != "") {
                sb.append("main").append(": ").append(main).append("\n");
                sb.append("description").append(": ").append(description).append("\n");
            }

            activity.infoTextView.setText(sb.toString());

        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(), "Weather inf not found!", Toast.LENGTH_SHORT).show();
            activity.infoTextView.setText(" ");
            e.printStackTrace();
        }
    }
}
