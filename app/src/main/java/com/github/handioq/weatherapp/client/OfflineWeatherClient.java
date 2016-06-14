package com.github.handioq.weatherapp.client;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.CityWeatherLoadParams;
import com.github.handioq.weatherapp.loader.CityWeatherLoader;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.models.CityWeather;
import com.github.handioq.weatherapp.saver.CityWeatherSaveParams;
import com.github.handioq.weatherapp.saver.CityWeatherSaver;
import com.github.handioq.weatherapp.saver.ISaver;
import com.survivingwithandroid.weather.lib.model.City;

public class OfflineWeatherClient {

    public OfflineWeatherClient() { }

    public void saveCityWeather(CityWeather cityWeather)
    {
        CityWeatherSaveParams cityWeatherSaveParams = new CityWeatherSaveParams(cityWeather);
        ISaver toFileSaver = new CityWeatherSaver(cityWeatherSaveParams);
        toFileSaver.Save();
    }

    public CityWeather loadCityWeather(City city)
    {
        CityWeatherLoadParams cityWeatherLoadParams =
                new CityWeatherLoadParams(city.getName() + "_" + city.getCountry() + PathConstants.JSON_DEFAULT_EXT);
        ILoader<CityWeather> fromFileLoader = new CityWeatherLoader(cityWeatherLoadParams);
        CityWeather cityWeather = fromFileLoader.load();

        return cityWeather;
    }

}
