package me.piotrsz109.utilapp.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.database.DatabaseHelper;
import me.piotrsz109.utilapp.location.GeocoderWrapper;
import me.piotrsz109.utilapp.location.SimpleLocationListener;
import me.piotrsz109.utilapp.presentation.components.HourlyWeatherItemsAdapter;
import me.piotrsz109.utilapp.presentation.components.WeeklyWeatherItemsAdapter;
import me.piotrsz109.utilapp.weather.WeatherApi;
import me.piotrsz109.utilapp.weather.dtos.TodayWeatherDto;
import me.piotrsz109.utilapp.weather.dtos.WeekWeatherDto;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class WeatherActivity extends AppCompatActivity {
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 1500; // 500 meters to update
    private Address _currentAddress;
    private ProgressDialog _loadingDialog;

    private LocationManager locationManager;
    private RecyclerView hourlyWeatherItemsView;
    private RecyclerView weeklyWeatherItemsView;
    private ImageView _currentWeatherImage;
    private TextView _currentWeatherTextView;
    private TextView _currentHourTextView;
    private TextView _currentLocationTextView;
    private SimpleLocationListener _locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        hourlyWeatherItemsView = findViewById(R.id.hourlyWeather);
        weeklyWeatherItemsView = findViewById(R.id.weeklyWeather);
        _currentWeatherImage = findViewById(R.id.imgWeatherType);
        _currentWeatherTextView = findViewById(R.id.lblCurrentTemperature);
        _currentHourTextView = findViewById(R.id.lblCurrentHour);
        _currentLocationTextView = findViewById(R.id.lblCurrentLocation);

        _currentLocationTextView.setOnClickListener(view -> {
            if (_currentAddress == null) return;
            String uri = String.format(Locale.getDefault(), "geo:0,0?q=%s", _currentAddress.getAddressLine(0));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            this.startActivity(intent);
        });

        Context context = this;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        _locationListener = new SimpleLocationListener(((latitude, longitude) -> {
            if(WeatherApi.TodayWeather != null && WeatherApi.WeekWeather != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(context.getString(R.string.reloadWeather))
                        .setPositiveButton(R.string.yes, (dialog, id) -> {
                            fetchHourlyWeather(latitude, longitude);
                            fetchWeeklyWeather(latitude, longitude);
                            dialog.dismiss();
                        })
                        .setNegativeButton(R.string.no, (dialog, id) -> {
                            setTodayWeather(WeatherApi.TodayWeather);
                            setWeekWeather(WeatherApi.WeekWeather);
                            dialog.dismiss();
                        });
                builder.create().show();
            }else {
                fetchHourlyWeather(latitude, longitude);
                fetchWeeklyWeather(latitude, longitude);
            }

            setAddress(latitude, longitude);
        }));

        _loadingDialog = new ProgressDialog(this);
        _loadingDialog.setMessage(getString(R.string.fetchWeatherInfo));
        _loadingDialog.setCancelable(false);
        _loadingDialog.setInverseBackgroundForced(true);
        _loadingDialog.show();

        fetchCurrentLocation();
    }

    private void fetchHourlyWeather(double latitude, double longitude) {
        WeatherApi.fetchTodayWeather(this, latitude, longitude, dto -> {
            if (dto instanceof TodayWeatherDto) {
                TodayWeatherDto weather = (TodayWeatherDto) dto;
                setTodayWeather(weather);
            }
        }, () -> {
            Toast.makeText(this, R.string.failedToFetchWeather, Toast.LENGTH_LONG).show();
        }, TemperatureFormat.Celsius);
    }

    private void fetchWeeklyWeather(double latitude, double longitude) {
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, 1);
        d = c.getTime();
        WeatherApi.fetchWeekWeather(this, latitude, longitude, d, dto -> {
            if (dto instanceof WeekWeatherDto) {
                WeekWeatherDto weather = (WeekWeatherDto) dto;
                setWeekWeather(weather);
            }
        }, () -> {
            Toast.makeText(this, R.string.failedToFetchWeather, Toast.LENGTH_LONG).show();
        }, TemperatureFormat.Celsius);
    }

    private void fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET},
                    5);

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, _locationListener);
    }

    private void setTodayWeather(TodayWeatherDto weather) {
        Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(new Date());
                _currentHourTextView.setText(String.format(context.getString(R.string.hourFormat), calendar.get(Calendar.HOUR_OF_DAY)));
                _currentWeatherImage.setImageResource(weather.getWeatherType(calendar.get(Calendar.HOUR_OF_DAY)).getIconId());
                _currentWeatherTextView.setText(String.format(context.getString(R.string.temperatureFormat),
                        WeatherApi.formatTemperature(weather.getTemperature(calendar.get(Calendar.HOUR_OF_DAY)))));
                HourlyWeatherItemsAdapter hourlyAdapter = new HourlyWeatherItemsAdapter(weather.getHourlyWeather());
                hourlyWeatherItemsView.setAdapter(hourlyAdapter);
                hourlyWeatherItemsView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            }
        });
    }

    private void setWeekWeather(WeekWeatherDto weather) {
        Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(new Date());
                WeeklyWeatherItemsAdapter adapter = new WeeklyWeatherItemsAdapter(weather.getWeeklyWeather());
                weeklyWeatherItemsView.setAdapter(adapter);
                weeklyWeatherItemsView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            }
        });

        _loadingDialog.dismiss();
    }

    private void setAddress(double latitude, double longitude) {
        Address address = GeocoderWrapper.getAddress(this, latitude, longitude);
        if (address == null) return;
        _currentAddress = address;
        _currentLocationTextView.setText(address.getLocality());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != 5) return;

        List<String> wrapper = Arrays.asList(permissions);

        if (wrapper.contains(Manifest.permission.ACCESS_COARSE_LOCATION) && wrapper.contains(Manifest.permission.ACCESS_FINE_LOCATION)
                && wrapper.contains(Manifest.permission.INTERNET)) {
            fetchCurrentLocation();
        }
    }
}