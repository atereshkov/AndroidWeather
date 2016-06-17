package com.github.handioq.weatherapp.loader;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.Condition;
import com.github.handioq.weatherapp.models.CurrentWeather;
import com.github.handioq.weatherapp.models.Location;
import com.github.handioq.weatherapp.models.Temperature;

import org.json.JSONObject;

/**
 * Class to parse CurrentWeather from a JSON file
 * @see CurrentWeather
 */
public class CurrentWeatherLoader implements ILoader<CurrentWeather>{

    private String filename;

    public CurrentWeatherLoader() {
    }

    public CurrentWeatherLoader(String filename) {
        this.filename = filename;
    }

    @Override
    public CurrentWeather load() {
        CurrentWeather currentWeather = new CurrentWeather();

        StringFileLoader stringFileLoader = new StringFileLoader(PathConstants.APP_DATA_DIRECTORY, filename);
        String jsonStr = stringFileLoader.load();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            float temp = (float) jsonObject.getDouble("temp");
            String iconID = jsonObject.getString("iconID");
            int weatherID = jsonObject.getInt("weatherID");
            String cond = jsonObject.getString("condition");
            String condDescr = jsonObject.getString("conditionDescr");
            float humidity = (float) jsonObject.getDouble("humidity");
            float pressure = (float) jsonObject.getDouble("pressure");
            float windSpeed = (float) jsonObject.getDouble("windSpeed");

            Location location = new Location(); // get from filename (parse) todo
            Condition condition = new Condition(cond, condDescr, windSpeed, pressure, humidity);
            Temperature temperature = new Temperature(temp);
            currentWeather = new CurrentWeather(condition, location, temperature, iconID, weatherID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return currentWeather;
    }
}
