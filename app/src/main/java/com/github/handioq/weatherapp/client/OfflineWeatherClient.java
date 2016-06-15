package com.github.handioq.weatherapp.client;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.CityWeatherLoadParams;
import com.github.handioq.weatherapp.loader.CityWeatherLoader;
import com.github.handioq.weatherapp.loader.CurrentWeatherLoader;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.models.CityWeather;
import com.github.handioq.weatherapp.models.CurrentWeather;
import com.github.handioq.weatherapp.saver.CityWeatherSaver;
import com.github.handioq.weatherapp.saver.CurrentWeatherSaver;
import com.github.handioq.weatherapp.saver.ISaver;
import com.survivingwithandroid.weather.lib.model.City;

public class OfflineWeatherClient {

    public OfflineWeatherClient() { }

    public void saveCityWeather(CityWeather cityWeather)
    {
        ISaver cityWeatherSaver = new CityWeatherSaver(cityWeather);
        cityWeatherSaver.save();
    }

    public CityWeather loadCityWeather(City city)
    {
        CityWeatherLoadParams cityWeatherLoadParams =
                new CityWeatherLoadParams(city.getName() + "_" + city.getCountry() + PathConstants.JSON_DEFAULT_EXT);
        ILoader<CityWeather> fromFileLoader = new CityWeatherLoader(cityWeatherLoadParams);
        CityWeather cityWeather = fromFileLoader.load();

        return cityWeather;
    }

    public void saveCurrentWeather(CurrentWeather currentWeather)
    {
        ISaver currentWeatherSaver = new CurrentWeatherSaver(currentWeather);
        currentWeatherSaver.save();
    }

    public CurrentWeather loadCurrentWeather(City city)
    {
        ILoader<CurrentWeather> fromFileLoader = new CurrentWeatherLoader(city.getName() + "_" + city.getCountry() +
                PathConstants.JSON_DEFAULT_EXT);
        CurrentWeather currentWeather = fromFileLoader.load();

        return currentWeather;
    }

}
