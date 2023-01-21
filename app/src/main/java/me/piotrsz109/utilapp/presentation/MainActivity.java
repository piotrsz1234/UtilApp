package me.piotrsz109.utilapp.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.location.GeocoderWrapper;
import me.piotrsz109.utilapp.location.SimpleLocationListener;
import me.piotrsz109.utilapp.weather.WeatherApi;
import me.piotrsz109.utilapp.weather.dtos.TodayWeatherDto;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class MainActivity extends AppCompatActivity {
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 1500; // 500 meters to update
    private LocationManager locationManager;
    private RecyclerView hourlyWeatherItemsView;
    private ImageView _currentWeatherImage;
    private TextView _currentWeatherTextView;
    private TextView _currentHourTextView;
    private SimpleLocationListener _locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hourlyWeatherItemsView = findViewById(R.id.hourlyWeather);
        _currentWeatherImage = findViewById(R.id.imgWeatherType);
        _currentWeatherTextView = findViewById(R.id.lblCurrentTemperature);
        _currentHourTextView = findViewById(R.id.lblCurrentHour);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        _locationListener = new SimpleLocationListener(((latitude, longitude) -> {
            fetchHourlyWeather(latitude, longitude);
            setAddress(latitude, longitude);
        }));

        fetchCurrentLocation();
    }

    private void fetchHourlyWeather(double latitude, double longitude) {
        WeatherApi.fetchTodayWeather(this, latitude, longitude, dto -> {
            if(dto instanceof TodayWeatherDto) {
                TodayWeatherDto weather = (TodayWeatherDto) dto;
                setTodayWeather(weather);
            }
        }, () -> {
            Toast.makeText(this, R.string.failedToFetchWeather, Toast.LENGTH_LONG).show();
        }, TemperatureFormat.Celsius);
    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.noPermissionToLocation, Toast.LENGTH_LONG).show();
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, _locationListener);
    }

    private void setTodayWeather(TodayWeatherDto weather) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        _currentHourTextView.setText(String.format("%d:00", calendar.get(Calendar.HOUR_OF_DAY)));
        _currentWeatherImage.setBackgroundResource(weather.getWeatherType(calendar.get(Calendar.HOUR_OF_DAY)).getIconId());
    }

    private void setAddress(double latitude, double longitude) {
        Address address = GeocoderWrapper.getAddress(this, latitude, longitude);
    }
}