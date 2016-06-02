package com.github.handioq.weatherapp.loader;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CurrentCityWeather;

import org.json.JSONArray;
import org.json.JSONObject;

public class CurrentCityWeatherLoader implements ILoader<CurrentCityWeather>{

    private String filename;

    public CurrentCityWeatherLoader() {

    }

    public CurrentCityWeatherLoader(CurrentCityWeatherLoadParams currentCityWeatherLoadParams)
    {
        this.filename = currentCityWeatherLoadParams.getFilename();
    }

    @Override
    public CurrentCityWeather load() {
        CurrentCityWeather currentCityWeather = new CurrentCityWeather();

        StringFileLoader stringFileLoader = new StringFileLoader(PathConstants.APP_DIRECTORY, filename);
        String jsonStr = stringFileLoader.load();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            String name = jsonObject.getString("name");
            float currentTemp = (float) jsonObject.getDouble("currentTemp");
            String iconID = jsonObject.getString("iconID");
            String country = jsonObject.getString("country");
            // TODO: ADD LAST UPDATE TIME

            currentCityWeather = new CurrentCityWeather(name, country, currentTemp, iconID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return currentCityWeather;
    }
}
