package com.github.handioq.weatherapp.loader;

/**
 * Load params for CityWeatherLoader
 * @see CityWeatherLoader
 */
public class CityWeatherLoadParams extends LoadParams {

    private String filename;

    public CityWeatherLoadParams() {
    }

    public CityWeatherLoadParams(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
