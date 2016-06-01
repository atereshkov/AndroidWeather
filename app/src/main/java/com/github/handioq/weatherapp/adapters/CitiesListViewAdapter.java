package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.handioq.weatherapp.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.WeatherHttpClient;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class CitiesListViewAdapter extends ArrayAdapter<City> {

    private List<City> items;
    private WeatherClient weatherClient;
    private Context context;

    public CitiesListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CitiesListViewAdapter(Context context, int resource, List<City> items, WeatherClient weatherClient) {
        super(context, resource, items);
        this.items = items;
        this.weatherClient = weatherClient;
        this.context = context;
    }

    /**
     * Checks if the List is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.city_item, null);
        }

        City city = getItem(position);

        if (city != null)
        {
            final ImageView cityImage = (ImageView) view.findViewById(R.id.cityImage);
            TextView cityTitle = (TextView) view.findViewById(R.id.cityTitle);
            final TextView cityWeather = (TextView) view.findViewById(R.id.cityWeather);

            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            final WeatherHttpClient weatherHttpClient = new WeatherHttpClient();

            weatherClient.getCurrentCondition(new WeatherRequest(city.getId()), new WeatherClient.WeatherEventListener() {
                @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    Log.d("WL", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");

                    cityWeather.setText(Math.round((currentWeather.weather.temperature.getTemp())) + "°С");

                    imageLoader.displayImage(weatherHttpClient.getQueryImageURL(currentWeather.weather.currentCondition.getIcon()),
                            cityImage); // can be replaced for complete usage, if need it
                }

                @Override public void onWeatherError(WeatherLibException e) {
                    Log.d("WL", "Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override public void onConnectionError(Throwable throwable) {
                    Log.d("WL", "Connection error");
                    throwable.printStackTrace();
                }
            });

            cityTitle.setText(city.getName() + ", " + city.getCountry());
        }

        return view;
    }
}
