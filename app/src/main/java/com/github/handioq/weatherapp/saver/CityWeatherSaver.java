package com.github.handioq.weatherapp.saver;

import android.os.Environment;
import android.util.Log;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.survivingwithandroid.weather.lib.model.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CityWeatherSaver implements ISaver {

    private String filename;
    private City city;
    private float currentTemp;
    private String iconID;

    private final static String FILE_EXT = ".json";

    public CityWeatherSaver() {
    }

    public CityWeatherSaver(CityWeatherSaveParams cityWeatherSaveParams) {
        this.filename = cityWeatherSaveParams.getFilename();
        this.city = cityWeatherSaveParams.getCity();
        this.currentTemp = cityWeatherSaveParams.getCurrentTemp();
        this.iconID = cityWeatherSaveParams.getIconID();
    }

    @Override
    public void Save() {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DIRECTORY);
        File file = new File(direct, filename + FILE_EXT);

        /*
        if(!direct.exists())
        {
            direct.mkdir();
            Log.i("Warning", "Directory not created");
        }
        */

        JSONObject obj = new JSONObject();
        //JSONArray jsonCities = new JSONArray();

        try {
            //JSONObject jsonCity = new JSONObject();
            obj.put("name", city.getName());
            obj.put("currentTemp", currentTemp);
            obj.put("iconID", iconID);

            //jsonCities.put(jsonCity);

            //obj.put("city", jsonCities);
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
