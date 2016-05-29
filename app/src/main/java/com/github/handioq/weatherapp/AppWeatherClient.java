package com.github.handioq.weatherapp;

import android.content.Context;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;

public class AppWeatherClient {

    private static AppWeatherClient me;
    private Context ctx;
    private WeatherClient client;
    private WeatherConfig config;
    private WeatherForecast forecast;
    private CurrentWeather weather;

    private AppWeatherClient() {
    }

    public static AppWeatherClient getInstance() {
        if (me == null)
            me = new AppWeatherClient();

        return me;
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
}
