package me.piotrsz109.utilapp.weather.dtos;

public class TodayWeatherDto implements WeatherDto {

    private double[] temperatures;
    private int[] weatherCodes;


    public TodayWeatherDto(double[] temperatures, int[] weatherCodes) {
        this.temperatures = temperatures;
        this.weatherCodes = weatherCodes;
    }

    public double[] getTemperatures() {
        return temperatures;
    }

    public int[] getWeatherCodes() {
        return weatherCodes;
    }

    public WeatherType getWeatherType(int hour) {
        return WeatherType.fromWMO(weatherCodes[hour]);
    }

    public double getTemperature(int hour) {
        return temperatures[hour];
    }
}
