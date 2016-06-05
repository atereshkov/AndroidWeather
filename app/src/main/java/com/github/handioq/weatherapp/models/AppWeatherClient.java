package com.github.handioq.weatherapp.models;

import android.content.Context;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.loader.JsonFileLoader;
import com.github.handioq.weatherapp.loader.JsonLoadParams;
import com.github.handioq.weatherapp.saver.ISaver;
import com.github.handioq.weatherapp.saver.JsonFileSaver;
import com.github.handioq.weatherapp.saver.JsonSaveParams;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;

import java.util.List;

/**
 * Singleton for using cities, weather and forecasts around application.
 */
public class AppWeatherClient {

    private static AppWeatherClient me;
    private Context ctx;
    private WeatherClient client;
    private WeatherConfig config;
    private WeatherForecast forecast;
    private CurrentWeather weather;
    private List<City> cities;
    private City selectedCity;

    private AppWeatherClient() {
    }

    public static AppWeatherClient getInstance() {
        if (me == null) {
            me = new AppWeatherClient();
        }

        return me;
    }

    public void addCity(City city)
    {
        if (!cities.contains(city))
        {
            cities.add(city);
        }
        saveCities();
    }

    public void removeSelectedCity()
    {
        if (cities.contains(selectedCity))
        {
            cities.remove(selectedCity);
        }
        saveCities();
    }

    public CurrentWeather getCurrentWeather() {
        return this.weather;
    }

    public void setCurrentWeather(CurrentWeather weather) {
        this.weather = weather;
    }

    public WeatherForecast getForecast() {
        return forecast;
    }

    public void setForecast(WeatherForecast forecast) {
        this.forecast = forecast;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public WeatherClient getClient() {
        return client;
    }

    public void setClient(WeatherClient client) {
        this.client = client;
    }

    public WeatherConfig getConfig() {
        return config;
    }

    public void setConfig(WeatherConfig config) {
        this.config = config;
    }

    public void saveCities()
    {
        JsonSaveParams jsonSaveParams = new JsonSaveParams(PathConstants.CITIES_FILE_NAME, cities);
        ISaver toFileSaver = new JsonFileSaver(jsonSaveParams);
        toFileSaver.Save();
    }

    public void loadCities()
    {
        JsonLoadParams fileLoadParams = new JsonLoadParams(PathConstants.CITIES_FILE_NAME);
        ILoader<List<City>> fromFileLoader = new JsonFileLoader(fileLoadParams);
        cities = fromFileLoader.load();
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

}
