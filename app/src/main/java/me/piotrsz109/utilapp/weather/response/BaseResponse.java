package me.piotrsz109.utilapp.weather.response;

import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public abstract class BaseResponse {
    protected String json;
    protected TemperatureFormat format;

    public BaseResponse(String json, TemperatureFormat format) {
        int firstIndex = json.indexOf("{");
        int lastIndex = json.lastIndexOf("}");
        this.json = json.substring(firstIndex, lastIndex + 1);
        this.format = format;
    }

    protected double CalculateTemperature(double celsius, TemperatureFormat format) {
        if(format == TemperatureFormat.Celsius) return celsius;

        return (celsius * 9 / 5) + 32;
    }
}
