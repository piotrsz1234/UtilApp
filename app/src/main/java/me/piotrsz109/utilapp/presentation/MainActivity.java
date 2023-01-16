package me.piotrsz109.utilapp.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.time.LocalDate;
import java.util.Date;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.weather.WeatherApi;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}