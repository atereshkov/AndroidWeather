package com.github.handioq.weatherapp.saver;

import com.github.handioq.weatherapp.models.CityWeather;

public class CityWeatherSaveParams extends SaveParams{

    private CityWeather cityWeather;

    public CityWeatherSaveParams() {
    }

    public CityWeatherSaveParams(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
    }

    public CityWeather getCityWeather() {
        return cityWeather;
    }
}
