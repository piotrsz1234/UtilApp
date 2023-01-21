package me.piotrsz109.utilapp.weather.dtos;

import me.piotrsz109.utilapp.R;

public final class WeatherType {

    private String weatherDesc;
    private int iconRes;

    private WeatherType(String desc, int icon) {
        weatherDesc = desc;
        iconRes = icon;
    }

    public int getIconId() {
        return iconRes;
    }

    public static WeatherType ClearSky = new WeatherType("Clear sky", R.drawable.ic_sunny);
    public static WeatherType MainlyClear = new WeatherType("Mainly clear", R.drawable.ic_cloudy);
    public static WeatherType PartlyCloudy = new WeatherType(
            "Partly cloudy", R.drawable.ic_cloudy);

    public static WeatherType Overcast = new WeatherType("Overcast", R.drawable.ic_cloudy);

    public static WeatherType Foggy = new WeatherType("Foggy", R.drawable.ic_very_cloudy);

    public static WeatherType DepositingRimeFog = new WeatherType("Depositing rime fog", R.drawable.ic_very_cloudy);

    public static WeatherType LightDrizzle = new WeatherType("Light drizzle", R.drawable.ic_rainshower);

    public static WeatherType ModerateDrizzle = new WeatherType("Moderate drizzle", R.drawable.ic_rainshower);

    public static WeatherType DenseDrizzle = new WeatherType("Dense drizzle", R.drawable.ic_rainshower);

    public static WeatherType LightFreezingDrizzle = new WeatherType("Slight freezing drizzle", R.drawable.ic_snowyrainy);

    public static WeatherType DenseFreezingDrizzle = new WeatherType("Dense freezing drizzle", R.drawable.ic_snowyrainy);

    public static WeatherType SlightRain = new WeatherType("Slight rain", R.drawable.ic_rainy);

    public static WeatherType ModerateRain = new WeatherType("Rainy", R.drawable.ic_rainy);

    public static WeatherType HeavyRain = new WeatherType("Heavy rain", R.drawable.ic_rainy);

    public static WeatherType HeavyFreezingRain = new WeatherType("Heavy freezing rain", R.drawable.ic_snowyrainy);

    public static WeatherType SlightSnowFall = new WeatherType("Slight snow fall", R.drawable.ic_snowy);

    public static WeatherType ModerateSnowFall = new WeatherType("Moderate snow fall", R.drawable.ic_heavysnow);

    public static WeatherType HeavySnowFall = new WeatherType("Heavy snow fall", R.drawable.ic_heavysnow);

    public static WeatherType SnowGrains = new WeatherType("Snow grains", R.drawable.ic_heavysnow);

    public static WeatherType SlightRainShowers = new WeatherType("Slight rain showers", R.drawable.ic_rainshower);

    public static WeatherType ModerateRainShowers = new WeatherType("Moderate rain showers", R.drawable.ic_rainshower);

    public static WeatherType ViolentRainShowers = new WeatherType("Violent rain showers", R.drawable.ic_rainshower);

    public static WeatherType SlightSnowShowers = new WeatherType("Light snow showers", R.drawable.ic_snowy);

    public static WeatherType HeavySnowShowers = new WeatherType("Heavy snow showers", R.drawable.ic_snowy);

    public static WeatherType ModerateThunderstorm = new WeatherType("Moderate thunderstorm", R.drawable.ic_thunder);

    public static WeatherType SlightHailThunderstorm = new WeatherType("Thunderstorm with slight hail", R.drawable.ic_rainythunder);

    public static WeatherType HeavyHailThunderstorm = new WeatherType("Thunderstorm with heavy hail", R.drawable.ic_rainythunder);

    public static WeatherType fromWMO(int code) {
        switch (code) {
            case 1:
                return MainlyClear;
            case 2:
                return PartlyCloudy;
            case 3:
                return Overcast;
            case 45:
                return Foggy;
            case 48:
                return DepositingRimeFog;
            case 51:
                return LightDrizzle;
            case 53:
                return ModerateDrizzle;
            case 55:
                return DenseDrizzle;
            case 56:
                return LightFreezingDrizzle;
            case 57:
                return DenseFreezingDrizzle;
            case 61:
                return SlightRain;
            case 63:
                return ModerateRain;
            case 65:
                return HeavyRain;
            case 66:
                return LightFreezingDrizzle;
            case 67:
                return HeavyFreezingRain;
            case 71:
                return SlightSnowFall;
            case 73:
                return ModerateSnowFall;
            case 75:
                return HeavySnowFall;
            case 77:
                return SnowGrains;
            case 80:
                return SlightRainShowers;
            case 81:
                return ModerateRainShowers;
            case 82:
                return ViolentRainShowers;
            case 85:
                return SlightSnowShowers;
            case 86:
                return HeavySnowShowers;
            case 95:
                return ModerateThunderstorm;
            case 96:
                return SlightHailThunderstorm;
            case 99:
                return HeavyHailThunderstorm;
            default:
                return ClearSky;
        }
    }
}
