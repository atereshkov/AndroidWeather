package com.github.handioq.weatherapp.loader;

import android.os.Environment;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.survivingwithandroid.weather.lib.model.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileLoader implements ILoader<List<City>> {

    private String filename;

    public JsonFileLoader(JsonLoadParams jsonLoadParams) {
        this.filename = jsonLoadParams.getFilename();
    }

    @Override
    public List<City> load() {
        List<City> cities = new ArrayList<>();

        StringFileLoader stringFileLoader = new StringFileLoader(PathConstants.APP_DIRECTORY, PathConstants.CITIES_FILE_NAME);
        String jsonStr = stringFileLoader.load();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonCities = jsonObject.getJSONArray("cities");

            for (int i = 0; i < jsonCities.length(); i++) {
                JSONObject jsonCity = jsonCities.getJSONObject(i);
                String id = jsonCity.getString("id");
                String name = jsonCity.getString("name");
                String country = jsonCity.getString("country");

                City.CityBuilder builder = new City.CityBuilder();
                cities.add(builder.name(name).country(country).id(id).build());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return cities;
    }

}
