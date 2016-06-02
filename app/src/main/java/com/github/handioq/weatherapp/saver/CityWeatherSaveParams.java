package com.github.handioq.weatherapp.saver;

import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public class CityWeatherSaveParams extends SaveParams{

    private String filename;
    private City city;
    private float currentTemp;
    private String iconID;

    public CityWeatherSaveParams() {
    }

    public CityWeatherSaveParams(String filename, City city, float currentTemp, String iconID) {
        this.city = city;
        this.currentTemp = currentTemp;
        this.iconID = iconID;
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    public String getIconID() {
        return iconID;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public City getCity() {
        return city;
    }
}
