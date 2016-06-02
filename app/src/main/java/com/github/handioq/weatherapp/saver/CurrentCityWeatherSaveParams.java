package com.github.handioq.weatherapp.saver;

import com.github.handioq.weatherapp.models.CurrentCityWeather;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public class CurrentCityWeatherSaveParams extends SaveParams{

    private CurrentCityWeather currentCityWeather;

    public CurrentCityWeatherSaveParams() {
    }

    public CurrentCityWeatherSaveParams(CurrentCityWeather currentCityWeather) {
        this.currentCityWeather = currentCityWeather;
    }

    public CurrentCityWeather getCurrentCityWeather() {
        return currentCityWeather;
    }
}
