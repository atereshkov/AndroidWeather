package com.github.handioq.weatherapp.saver;

import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public class JsonSaveParams extends SaveParams{

    public JsonSaveParams() {
    }

    public JsonSaveParams(String filename, List<City> cities) {
        super(filename, cities);
    }
}
