package com.github.handioq.weatherapp.saver;

import android.os.Environment;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CityWeather;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving CityWeather to JSON file.
 * @see CityWeather
 */
public class CityWeatherSaver implements ISaver {

    private CityWeather cityWeather;

    public CityWeatherSaver() {
    }

    public CityWeatherSaver(CityWeather cityWeather) {
        this.cityWeather = cityWeather;
    }

    @Override
    public void save() {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DIRECTORY);
        String filename = cityWeather.getCityName() + "_" + cityWeather.getCountry() + PathConstants.JSON_DEFAULT_EXT;
        File file = new File(direct, filename);

        JSONObject obj = new JSONObject();

        try {
            obj.put("name", cityWeather.getCityName());
            obj.put("currentTemp", cityWeather.getCurrentTemp());
            obj.put("iconID", cityWeather.getIconID());
            obj.put("country", cityWeather.getCountry());
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
