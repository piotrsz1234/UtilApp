package me.piotrsz109.utilapp.presentation.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.weather.WeatherApi;
import me.piotrsz109.utilapp.weather.dtos.HourlyWeatherItem;
import me.piotrsz109.utilapp.weather.dtos.WeatherType;
import me.piotrsz109.utilapp.weather.dtos.WeeklyWeatherItem;

public class WeeklyWeatherItemsAdapter extends RecyclerView.Adapter<WeeklyWeatherItemsAdapter.ViewHolder> {

    private WeeklyWeatherItem[] weatherItems;

    public WeeklyWeatherItemsAdapter(WeeklyWeatherItem[] items) {
        weatherItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.weather_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.WeatherItemImage.setImageResource(WeatherType.fromWMO(weatherItems[position].getWeatherCode()).getIconId());
        Context context = holder.WeatherItemImage.getContext();
        holder.WeatherItemTemperatureTextView.setText(String.format(context.getString(R.string.weeklyTemperatureFormat),
                WeatherApi.formatTemperature(weatherItems[position].getMaxTemperature()),
                WeatherApi.formatTemperature(weatherItems[position].getMinTemperature())));
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, position + 1);
        Date d = calendar.getTime();
        holder.WeatherItemHourTextView.setText(new SimpleDateFormat(String.valueOf(context.getText(R.string.DateFormat))).format(d));
    }

    @Override
    public int getItemCount() {
        return weatherItems.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView WeatherItemImage;
        public TextView WeatherItemTemperatureTextView;
        public TextView WeatherItemHourTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            WeatherItemImage = itemView.findViewById(R.id.imgItemWeatherType);
            WeatherItemTemperatureTextView = itemView.findViewById(R.id.lblItemTemperature);
            WeatherItemHourTextView = itemView.findViewById(R.id.lblItemHour);
        }
    }
}
