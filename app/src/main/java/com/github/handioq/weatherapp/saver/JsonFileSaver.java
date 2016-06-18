package com.github.handioq.weatherapp.saver;


import android.os.Environment;
import android.util.Log;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.survivingwithandroid.weather.lib.model.City;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for saving cities to JSON file.
 * @see City
 */
public class JsonFileSaver implements ISaver{

    private String filename;
    private List<City> cities;

    public JsonFileSaver(JsonSaveParams jsonSaveParams) {
        this.filename = jsonSaveParams.getFilename();
        this.cities = jsonSaveParams.getCities();
    }

    @Override
    public void save() {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DIRECTORY);
        File file = new File(direct, filename);

        if(!direct.exists())
        {
            direct.mkdir();
            Log.i("Warning", "Directory not created");
        }

        if (!file.exists()) {

            createDefaultBase(filename);
        }

        JSONObject obj = new JSONObject();
        JSONArray jsonCities = new JSONArray();

        try {
            for (City city : cities)
            {
                JSONObject jsonCity = new JSONObject();
                jsonCity.put("id", city.getId());
                jsonCity.put("name", city.getName());
                jsonCity.put("country", city.getCountry());
                jsonCities.put(jsonCity);
            }

            obj.put("cities", jsonCities);
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

    /**
     * Make an empty file.
     * @param filename
     */
    private void createDefaultBase(String filename)
    {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DIRECTORY);

        if(!direct.exists())
        {
            direct.mkdir();
        }

        File file = new File(direct, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("Error", "******* File not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
