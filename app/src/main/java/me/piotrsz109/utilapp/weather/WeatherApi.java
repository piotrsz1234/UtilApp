package me.piotrsz109.utilapp.weather;

import android.content.Context;

import org.chromium.net.CronetEngine;
import org.chromium.net.UrlRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.weather.callbacks.FetchTodayWeatherCallback;
import me.piotrsz109.utilapp.weather.callbacks.FetchWeekWeatherCallback;
import me.piotrsz109.utilapp.weather.callbacks.OnFailedUserCallback;
import me.piotrsz109.utilapp.weather.callbacks.OnSucceededUserCallback;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class WeatherApi {

    private static CronetEngine cronetEngine;

    public static void fetchTodayWeather(Context context, double latitude, double longitude, OnSucceededUserCallback successCallback, OnFailedUserCallback failedCallback, TemperatureFormat format) {
        if (cronetEngine == null) {
            CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
            cronetEngine = myBuilder.build();
        }
        String url = String.format(context.getString(R.string.todayWeatherUrl), latitude, longitude);
        Executor executor = Executors.newSingleThreadExecutor();

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                url, new FetchTodayWeatherCallback(context, successCallback, failedCallback, format), executor);

        UrlRequest request = requestBuilder.build();

        request.start();
    }

    public static void fetchWeekWeather(Context context, double latitude, double longitude, Date startDate,
                                        OnSucceededUserCallback successCallback, OnFailedUserCallback failedCallback, TemperatureFormat format) {
        if (cronetEngine == null) {
            CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
            cronetEngine = myBuilder.build();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = c.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String url = String.format(context.getString(R.string.weekWeatherUrl), latitude, longitude, dateFormat.format(startDate),
                dateFormat.format(endDate));
        Executor executor = Executors.newSingleThreadExecutor();

        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder(
                url, new FetchWeekWeatherCallback(context, successCallback, failedCallback, format), executor);

        UrlRequest request = requestBuilder.build();

        request.start();
    }
}
