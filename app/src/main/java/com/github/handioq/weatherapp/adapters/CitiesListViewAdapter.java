package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.client.OfflineWeatherClient;
import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.CityWeatherLoadParams;
import com.github.handioq.weatherapp.loader.CityWeatherLoader;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.models.CityWeather;
import com.github.handioq.weatherapp.saver.CityWeatherSaveParams;
import com.github.handioq.weatherapp.saver.CityWeatherSaver;
import com.github.handioq.weatherapp.saver.ISaver;
import com.github.handioq.weatherapp.utils.IconUtils;
import com.github.handioq.weatherapp.utils.MeasurementUnitsConverter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;

public class CitiesListViewAdapter extends ArrayAdapter<City> {

    private List<City> items;
    private WeatherClient weatherClient;
    private Context context;

    private SharedPreferences sharedPrefs;

    private OfflineWeatherClient offlineWeatherClient = new OfflineWeatherClient();

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

        final City city = getItem(position);
        final ImageView cityImage = (ImageView) view.findViewById(R.id.cityImage);
        final TextView cityTitle = (TextView) view.findViewById(R.id.cityTitle);
        final TextView cityWeatherInfo = (TextView) view.findViewById(R.id.cityWeather);

        if (city != null)
        {
            final ImageLoader imageLoader = ImageLoader.getInstance();

            final Resources res = context.getResources();
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            final String unit = sharedPrefs.getString("units_list", res.getString(R.string.degree_celsius));

            weatherClient.getCurrentCondition(new WeatherRequest(city.getId()), new WeatherClient.WeatherEventListener() {
                @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    Log.d("WL", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");

                    String temp;
                    if (unit.equals(res.getString(R.string.degree_celsius))) {
                        temp = String.format(res.getString(R.string.temperature), Math.round(currentTemp)) + unit;
                    }
                    else {
                       temp = String.format(res.getString(R.string.temperature),
                               Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(currentTemp))) + unit;
                    }

                    String title = String.format(res.getString(R.string.main_cities_listview_title), city.getName(), city.getCountry());
                    String iconID = currentWeather.weather.currentCondition.getIcon();

                    cityWeatherInfo.setText(temp);
                    imageLoader.displayImage(IconUtils.getQueryImageURL(iconID), cityImage); // can be replaced for complete usage, if need it
                    cityTitle.setText(title);

                    CityWeather cityWeather = new CityWeather(city.getName(), city.getCountry(), currentTemp, iconID);
                    offlineWeatherClient.saveCityWeather(cityWeather);
                }

                @Override public void onWeatherError(WeatherLibException e) {
                    Log.d("WL", "Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override public void onConnectionError(Throwable throwable) {
                    Log.d("WL", "Connection error"); // if no connection to the internet
                    throwable.printStackTrace();

                    CityWeather cityWeather = offlineWeatherClient.loadCityWeather(city);
                    String temp;
                    if (unit.equals(res.getString(R.string.degree_celsius))) {
                        temp = String.format(res.getString(R.string.temperature), Math.round(cityWeather.getCurrentTemp())) + unit;
                    }
                    else
                    {
                        temp = String.format(res.getString(R.string.temperature),
                                Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(cityWeather.getCurrentTemp()))) + unit;
                    }
                    String title = String.format(res.getString(R.string.main_cities_listview_title), cityWeather.getCityName(), cityWeather.getCountry());

                    cityWeatherInfo.setText(temp);
                    cityTitle.setText(title);
                    cityImage.setImageDrawable(cityWeather.getIconFromDrawable(context));
                }
            });


        }
        return view;
    }
}
