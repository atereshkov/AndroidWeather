package com.github.handioq.weatherapp.models;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;

public class CityWeather {

    private City city;
    private boolean dataAvailable = false;
    private CurrentWeather weather;

    public CityWeather() {
    }

    public void setDataAvailable(boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    public boolean isDataAvailable() {
        return dataAvailable;
    }



}
