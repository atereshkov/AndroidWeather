package com.github.handioq.weatherapp.loader;

import android.os.Environment;

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

        String jsonStr = readFile(PathConstants.APP_DIRECTORY, PathConstants.CITIES_FILE_NAME);

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


    private String readFile(String directory, String filename)
    {
        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath() + directory, filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null)
            {
                text.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
