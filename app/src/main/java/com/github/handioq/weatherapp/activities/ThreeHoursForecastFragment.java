package com.github.handioq.weatherapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.handioq.weatherapp.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;


public class ThreeHoursForecastFragment  extends Fragment {

    AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
    private City selectedCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.three_hour_forecast_layout, container, false);

        selectedCity = appWeatherClient.getSelectedCity();

        getActivity().setTitle(selectedCity.getName());

        appWeatherClient.getClient().getHourForecastWeather(new WeatherRequest(selectedCity.getId()), new WeatherClient.HourForecastWeatherEventListener()
        {
            @Override
            public void onWeatherRetrieved(WeatherHourForecast weatherHourForecast) {
                List<HourForecast> hourList = weatherHourForecast.getHourForecast();

                //for (HourForecast hourForecast: hourList)
                for (int i = 0; i < hourList.size(); i++)
                {
                    Weather weather = weatherHourForecast.getHourForecast(i).weather;
                    long timestamp = weatherHourForecast.getHourForecast(i).timestamp;
                }

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
