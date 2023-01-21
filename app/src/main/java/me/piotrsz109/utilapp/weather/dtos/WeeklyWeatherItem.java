package me.piotrsz109.utilapp.weather.dtos;

public class WeeklyWeatherItem {
    private double _minTemperature;
    private double _maxTemperature;
    private int _weatherCode;

    public WeeklyWeatherItem(double minTemperature, double maxTemperature, int weatherCode) {
        _minTemperature = minTemperature;
        _maxTemperature = maxTemperature;
        _weatherCode = weatherCode;
    }


    public int getWeatherCode() {
        return _weatherCode;
    }

    public double getMinTemperature() {
        return _minTemperature;
    }

    public double getMaxTemperature() {
        return _maxTemperature;
    }
}
