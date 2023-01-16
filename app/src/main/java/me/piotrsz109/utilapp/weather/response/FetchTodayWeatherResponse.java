package me.piotrsz109.utilapp.weather.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.piotrsz109.utilapp.weather.dtos.TodayWeatherDto;
import me.piotrsz109.utilapp.weather.formats.TemperatureFormat;

public class FetchTodayWeatherResponse extends BaseResponse {

    public FetchTodayWeatherResponse(String json, TemperatureFormat format) {
        super(json, format);
    }

    public TodayWeatherDto translate() {
        try {
            JSONObject obj = new JSONObject(json);
            double[] temperatures = new double[24];
            int[] weatherCodes = new int[24];
            JSONArray tempArray = obj.getJSONObject("hourly").getJSONArray("temperature_2m");
            JSONArray codesArray = obj.getJSONObject("hourly").getJSONArray("weathercode");
            for (int i=0; i<24;i++) {
                temperatures[i] = CalculateTemperature(tempArray.getDouble(i), format);
                weatherCodes[i] = codesArray.getInt(i);
            }

            return new TodayWeatherDto(temperatures, weatherCodes);
        } catch (JSONException e) {
            return null;
        }
    }
}
