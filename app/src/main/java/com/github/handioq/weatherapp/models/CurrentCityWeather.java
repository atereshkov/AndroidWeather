package com.github.handioq.weatherapp.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;

public class CurrentCityWeather {

    private String cityName;
    private String country;
    private float currentTemp;
    private String iconID;
    private Context ctx;

    public CurrentCityWeather() {
    }

    public CurrentCityWeather(Context ctx) {
        this.ctx = ctx;
    }

    public CurrentCityWeather(String cityName, String country, float currentTemp, String iconID) {
        this.cityName = cityName;
        this.country = country;
        this.currentTemp = currentTemp;
        this.iconID = iconID;
    }

    public String getCityName() {
        return cityName;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public String getIconID() {
        return iconID;
    }

    public String getCountry() {
        return country;
    }

    public Drawable getIconFromDrawable(Context ctx)
    {
        String uri = "mipmap/icon_" + getIconID();
        // int imageResource = R.drawable.icon;
        int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());

        Drawable image = ctx.getResources().getDrawable(imageResource);
        return image;
    }
}
