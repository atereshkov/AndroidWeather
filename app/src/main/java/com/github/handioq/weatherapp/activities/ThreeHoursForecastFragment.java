package com.github.handioq.weatherapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.github.handioq.weatherapp.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.adapters.HourForecastExpListAdapter;
import com.github.handioq.weatherapp.utils.DateUtils;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThreeHoursForecastFragment  extends Fragment {

    AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
    private City selectedCity;

    ExpandableListView expandableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.three_hour_forecast_layout, container, false);

        selectedCity = appWeatherClient.getSelectedCity();
        getActivity().setTitle(selectedCity.getName());
        expandableListView = (ExpandableListView) V.findViewById(R.id.expandableListView);

        appWeatherClient.getClient().getHourForecastWeather(new WeatherRequest(selectedCity.getId()), new WeatherClient.HourForecastWeatherEventListener()
        {
            @Override
            public void onWeatherRetrieved(WeatherHourForecast weatherHourForecast) {
                List<HourForecast> hourList = weatherHourForecast.getHourForecast();

                ArrayList<Map<String, Weather>> groupDataList = new ArrayList<>();
                Map<String, Weather> map;

                //for (HourForecast hourForecast: hourList)
                for (int i = 0; i < hourList.size(); i++)
                {
                    Weather weather = weatherHourForecast.getHourForecast(i).weather;
                    long timestamp = weatherHourForecast.getHourForecast(i).timestamp;
                    map = new HashMap<>();
                    map.put(DateUtils.convertTimestampToDate(timestamp), weather); // время года
                    groupDataList.add(map);
                }

                HourForecastExpListAdapter adapter = new HourForecastExpListAdapter(getContext(), groupDataList);
                expandableListView.setAdapter(adapter);

            }

            @Override
            public void onWeatherError(WeatherLibException e) {

            }

            @Override public void onConnectionError(Throwable throwable) {

            }
        });

        return V;
    }

}
