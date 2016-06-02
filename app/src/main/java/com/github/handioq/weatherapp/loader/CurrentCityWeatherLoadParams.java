package com.github.handioq.weatherapp.loader;

public class CurrentCityWeatherLoadParams extends LoadParams {

    private String filename;

    public CurrentCityWeatherLoadParams() {
    }

    public CurrentCityWeatherLoadParams(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
