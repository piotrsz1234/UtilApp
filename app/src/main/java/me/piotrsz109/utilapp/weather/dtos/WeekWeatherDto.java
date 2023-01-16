package me.piotrsz109.utilapp.weather.dtos;

public class WeekWeatherDto implements WeatherDto {
    private int[] weatherCodes;
    private double[] minTemperatures;
    private double[] maxTemperatures;

    public WeekWeatherDto(int[] weatherCodes, double[] minTemperatures, double[] maxTemperatures) {
        this.weatherCodes = weatherCodes;
        this.minTemperatures = minTemperatures;
        this.maxTemperatures = maxTemperatures;
    }

    public WeatherType getWeatherType(int day) {
        return WeatherType.fromWMO(weatherCodes[day]);
    }

    public double getMinTemperature(int day) {
        return minTemperatures[day];
    }

    public double getMaxTemperature(int day) {
        return maxTemperatures[day];
    }
}
