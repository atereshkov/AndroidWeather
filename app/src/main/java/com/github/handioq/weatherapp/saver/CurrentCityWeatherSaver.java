package com.github.handioq.weatherapp.saver;

import android.os.Environment;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CurrentCityWeather;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CurrentCityWeatherSaver implements ISaver {

    private CurrentCityWeather currentCityWeather;

    //private final static String FILE_EXT = ".json";

    public CurrentCityWeatherSaver() {
    }

    public CurrentCityWeatherSaver(CurrentCityWeatherSaveParams currentCityWeatherSaveParams) {
        this.currentCityWeather = currentCityWeatherSaveParams.getCurrentCityWeather();
    }

    @Override
    public void Save() {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DIRECTORY);
        String filename = currentCityWeather.getCityName() + "_" + currentCityWeather.getCountry() + PathConstants.JSON_DEFAULT_EXT;
        File file = new File(direct, filename);

        JSONObject obj = new JSONObject();

        try {
            obj.put("name", currentCityWeather.getCityName());
            obj.put("currentTemp", currentCityWeather.getCurrentTemp());
            obj.put("iconID", currentCityWeather.getIconID());
            obj.put("country", currentCityWeather.getCountry());
            // TODO: ADD LAST UPDATE TIME
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(obj.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
