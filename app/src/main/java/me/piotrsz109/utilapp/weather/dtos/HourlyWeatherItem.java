package me.piotrsz109.utilapp.weather.dtos;

public class HourlyWeatherItem {
    private double _temperature;
    private int _weatherCode;

    public HourlyWeatherItem(double temperature, int weatherCode) {
        _temperature = temperature;
        _weatherCode = weatherCode;
    }

    public double getTemperature() {
        return _temperature;
    }

    public int getWeatherCode() {
        return _weatherCode;
    }
}
