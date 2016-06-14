package com.github.handioq.weatherapp.loader;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CityWeather;

import org.json.JSONObject;

public class CityWeatherLoader implements ILoader<CityWeather>{

    private String filename;

    public CityWeatherLoader() {

    }

    public CityWeatherLoader(CityWeatherLoadParams cityWeatherLoadParams)
    {
        this.filename = cityWeatherLoadParams.getFilename();
    }

    @Override
    public CityWeather load() {
        CityWeather cityWeather = new CityWeather();

        StringFileLoader stringFileLoader = new StringFileLoader(PathConstants.APP_DIRECTORY, filename);
        String jsonStr = stringFileLoader.load();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            String name = jsonObject.getString("name");
            float currentTemp = (float) jsonObject.getDouble("currentTemp");
            String iconID = jsonObject.getString("iconID");
            String country = jsonObject.getString("country");
            // TODO: ADD LAST UPDATE TIME

            cityWeather = new CityWeather(name, country, currentTemp, iconID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return cityWeather;
    }
}
