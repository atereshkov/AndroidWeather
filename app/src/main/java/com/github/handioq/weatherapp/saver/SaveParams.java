package com.github.handioq.weatherapp.saver;

import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public abstract class SaveParams {

    private String filename;
    private List<City> cities;

    public SaveParams() { }

    public SaveParams(String filename, List<City> cities)
    {
        this.filename = filename;
        this.cities = cities;
    }

    public String getFilename() {
        return filename;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
