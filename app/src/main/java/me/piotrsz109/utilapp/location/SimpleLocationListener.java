package me.piotrsz109.utilapp.location;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

public class SimpleLocationListener implements LocationListener {

    private LocationChangedCallback _callback;

    public SimpleLocationListener(LocationChangedCallback callback){
        _callback = callback;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        _callback.call(location.getLatitude(), location.getLongitude());
    }
}
