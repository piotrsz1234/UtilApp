package me.piotrsz109.utilapp.presentation.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.piotrsz109.utilapp.R;
import me.piotrsz109.utilapp.weather.WeatherApi;
import me.piotrsz109.utilapp.weather.dtos.WeatherItem;
import me.piotrsz109.utilapp.weather.dtos.WeatherType;

public class WeatherItemsAdapter extends RecyclerView.Adapter<WeatherItemsAdapter.ViewHolder> {

    private WeatherItem[] weatherItems;

    public WeatherItemsAdapter(WeatherItem[] items) {
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
        holder.WeatherItemTemperatureTextView.setText(String.format(context.getString(R.string.temperatureFormat),
                WeatherApi.formatTemperature(weatherItems[position].getTemperature())));
        holder.WeatherItemHourTextView.setText(String.format(String.valueOf(context.getText(R.string.hourFormat)), position));
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
