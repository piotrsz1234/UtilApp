package me.piotrsz109.utilapp.weather.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.piotrsz109.utilapp.weather.dtos.TodayWeatherDto;
import me.piotrsz109.utilapp.weather.dtos.WeatherDto;
import me.piotrsz109.utilapp.weather.dtos.WeekWeatherDto;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class FetchWeekWeatherResponse extends BaseResponse {
    public FetchWeekWeatherResponse(String json, TemperatureFormat format) {
        super(json, format);
    }

    public WeekWeatherDto translate() {
        try {
            JSONObject obj = new JSONObject(json);
            double[] maxTemperatures = new double[7];
            double[] minTemperatures = new double[7];
            int[] weatherCodes = new int[7];
            JSONArray minTempArray = obj.getJSONObject("daily").getJSONArray("temperature_2m_min");
            JSONArray codesArray = obj.getJSONObject("daily").getJSONArray("weathercode");
            JSONArray maxTempArray = obj.getJSONObject("daily").getJSONArray("temperature_2m_max");

            for (int i = 0; i < 7; i++) {
                maxTemperatures[i] = CalculateTemperature(maxTempArray.getDouble(i), format);
                minTemperatures[i] = CalculateTemperature(minTempArray.getDouble(i), format);
                weatherCodes[i] = codesArray.getInt(i);
            }

            return new WeekWeatherDto(weatherCodes, minTemperatures, maxTemperatures);
        } catch (JSONException e) {
            return null;
        }
    }
}
