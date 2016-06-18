package com.github.handioq.weatherapp.saver;

import android.os.Environment;
import android.util.Log;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CurrentWeather;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving CurrentWeather to file.
 * @see CurrentWeather
 */
public class CurrentWeatherSaver implements ISaver {

    private CurrentWeather currentWeather;

    public CurrentWeatherSaver() {
    }

    public CurrentWeatherSaver(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    @Override
    public void save() {
        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ PathConstants.APP_DATA_DIRECTORY);
        String filename = currentWeather.getLocation().getName() + "_" + currentWeather.getLocation().getCountry()
                + PathConstants.JSON_DEFAULT_EXT;

        if(!direct.exists())
        {
            direct.mkdir();
            Log.i("Warning", "Directory not created");
        }

        File file = new File(direct, filename);

        JSONObject obj = new JSONObject();

        try {
            obj.put("temp", currentWeather.getTemperature().getTemp());
            obj.put("iconID", currentWeather.getIconID());
            obj.put("weatherID", currentWeather.getWeatherID());
            obj.put("condition", currentWeather.getCondition().getCondition());
            obj.put("conditionDescr", currentWeather.getCondition().getConditionDescription());
            obj.put("humidity", currentWeather.getCondition().getHumidity());
            obj.put("pressure", currentWeather.getCondition().getPressure());
            obj.put("windSpeed", currentWeather.getCondition().getWindSpeed());
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
