package com.example.haytham.whatstheweather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
     TextView infoTextView;

    public void getWeather(View v){
        EditText cityEditText= findViewById(R.id.cityEditText);

        // removing keyboard after click
        InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityEditText.getWindowToken(), 0);

        WeatherDownloader weatherDownloader= new WeatherDownloader(this);
        String apiKey= "2fa6a327dc19e98c3a1237ec345e2e10";
        String encodedCityName= null;
        try {
            encodedCityName = URLEncoder.encode(cityEditText.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            weatherDownloader.execute(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=" + apiKey));
        } catch (MalformedURLException e) {
            Toast.makeText(this, "Weather inf not found!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView= findViewById(R.id.infoTextView);
    }
}
