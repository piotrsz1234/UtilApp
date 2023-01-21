package me.piotrsz109.utilapp.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocoderWrapper {

    public static Address getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            return addresses.size() > 0 ? addresses.get(0) : null;
        } catch (IOException e) {
            return null;
        }
    }
}
