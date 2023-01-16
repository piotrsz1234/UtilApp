package me.piotrsz109.utilapp.weather.callbacks;

import android.content.Context;
import android.util.Log;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;
import me.piotrsz109.utilapp.weather.response.FetchWeekWeatherResponse;

public class FetchWeekWeatherCallback extends UrlRequest.Callback {
    private static final String loggerTag = "FetchWeekWeather";
    private Context context;
    private OnSucceededUserCallback succeededUserCallback;
    private OnFailedUserCallback failedUserCallback;
    private TemperatureFormat format;
    private FetchWeekWeatherResponse response;

    public FetchWeekWeatherCallback(Context context, OnSucceededUserCallback succeededUserCallback,
                                    OnFailedUserCallback failedUserCallback, TemperatureFormat format) {
        this.context = context;
        this.succeededUserCallback = succeededUserCallback;
        this.failedUserCallback = failedUserCallback;
        this.format = format;
    }

    @Override
    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
        Log.i(loggerTag, "onRedirectReceived method called.");
        request.followRedirect();
    }

    @Override
    public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
        Log.i(loggerTag, "onResponseStarted method called.");
        request.read(ByteBuffer.allocateDirect(128 * 1024));
    }

    @Override
    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
        Log.i(loggerTag, "onReadCompleted method called.");
        if(byteBuffer.hasArray()) {
            byte[] array = byteBuffer.array();
            response = new FetchWeekWeatherResponse(new String(array, StandardCharsets.UTF_8), format);
        }
        request.read(byteBuffer);
    }

    @Override
    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
        Log.i(loggerTag, "onSucceeded method called.");

        if(succeededUserCallback != null) {
            succeededUserCallback.call(response.translate());
        }
    }

    @Override
    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
        Log.e(loggerTag,error.getMessage());

        if(failedUserCallback != null)
            failedUserCallback.call();
    }
}
