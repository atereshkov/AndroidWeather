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

    public CityWeather(City city, CurrentWeather weather) {
        this.city = city;
        this.weather = weather;
    }

    public CityWeather(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public CurrentWeather getWeather() {
        return weather;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setWeather(CurrentWeather weather) {
        this.weather = weather;
    }

    public void setDataAvailable(boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    public boolean isDataAvailable() {
        return dataAvailable;
    }



}
