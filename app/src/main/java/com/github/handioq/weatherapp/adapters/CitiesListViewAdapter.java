package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.CurrentCityWeatherLoadParams;
import com.github.handioq.weatherapp.loader.CurrentCityWeatherLoader;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.models.CurrentCityWeather;
import com.github.handioq.weatherapp.saver.CurrentCityWeatherSaveParams;
import com.github.handioq.weatherapp.saver.CurrentCityWeatherSaver;
import com.github.handioq.weatherapp.saver.ISaver;
import com.github.handioq.weatherapp.utils.IconUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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

        if (city != null)
        {
            final ImageView cityImage = (ImageView) view.findViewById(R.id.cityImage);
            final TextView cityTitle = (TextView) view.findViewById(R.id.cityTitle);
            final TextView cityWeather = (TextView) view.findViewById(R.id.cityWeather);

            final ImageLoader imageLoader = ImageLoader.getInstance();

            final Resources res = context.getResources();

            weatherClient.getCurrentCondition(new WeatherRequest(city.getId()), new WeatherClient.WeatherEventListener() {
                @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                    float currentTemp = currentWeather.weather.temperature.getTemp();
                    String iconID = currentWeather.weather.currentCondition.getIcon();

                    Log.d("WL", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");

                    String temp = String.format(res.getString(R.string.temperature), Math.round(currentTemp))
                            + context.getResources().getString(R.string.degree_celsius);
                    String title = String.format(res.getString(R.string.main_cities_listview_title), city.getName(), city.getCountry());

                    cityWeather.setText(temp);
                    imageLoader.displayImage(IconUtils.getQueryImageURL(iconID), cityImage); // can be replaced for complete usage, if need it
                    cityTitle.setText(title);

                    CurrentCityWeatherSaveParams currentCityWeatherSaveParams =
                            new CurrentCityWeatherSaveParams(new CurrentCityWeather(city.getName(), city.getCountry(), currentTemp, iconID));
                    ISaver toFileSaver = new CurrentCityWeatherSaver(currentCityWeatherSaveParams);
                    toFileSaver.Save(); // save cities data to file for get it when no connection available
                    // transfer to new class (~ OfflineAppManager)
                }

                @Override public void onWeatherError(WeatherLibException e) {
                    Log.d("WL", "Weather Error - parsing data");
                    e.printStackTrace();
                }

                @Override public void onConnectionError(Throwable throwable) {
                    Log.d("WL", "Connection error"); // if no connection to the internet
                    throwable.printStackTrace();
                    // load city weather data from file

                    // TODO: MAKE SOME KIND OF CHECK FOR FIRST RUN WITHOUT INTERNET
                    // IF CITIES IN EMPTY or... IF FILES WITH CITY NOT FOUND

                    CurrentCityWeather currentCityWeather = new CurrentCityWeather(context);

                    CurrentCityWeatherLoadParams currentCityWeatherLoadParams =
                            new CurrentCityWeatherLoadParams(city.getName() + "_" + city.getCountry() + PathConstants.JSON_DEFAULT_EXT);
                    ILoader<CurrentCityWeather> fromFileLoader = new CurrentCityWeatherLoader(currentCityWeatherLoadParams);
                    currentCityWeather = fromFileLoader.load();

                    String temp = String.format(res.getString(R.string.temperature), Math.round(currentCityWeather.getCurrentTemp()))
                            + context.getResources().getString(R.string.degree_celsius);
                    String title = String.format(res.getString(R.string.main_cities_listview_title),
                            currentCityWeather.getCityName(),
                            currentCityWeather.getCountry());

                    cityWeather.setText(temp);
                    cityTitle.setText(title);
                    cityImage.setImageDrawable(currentCityWeather.getIconFromDrawable(context));

                }
            });


        }

        return view;
    }
}
