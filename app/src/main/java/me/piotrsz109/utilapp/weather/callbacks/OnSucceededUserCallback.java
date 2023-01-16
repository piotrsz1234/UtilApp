package me.piotrsz109.utilapp.weather.callbacks;

import me.piotrsz109.utilapp.weather.dtos.WeatherDto;

public interface OnSucceededUserCallback {
    void call(WeatherDto dto);
}
