/*
package com.github.handioq.weatherapp.loader;


import android.os.Environment;

import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.models.CityWeather;
import com.survivingwithandroid.weather.lib.WeatherCode;
import com.survivingwithandroid.weather.lib.model.BaseWeather;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.Location;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.provider.IWeatherCodeProvider;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapCodeProvider;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonCityWeatherLoader implements ILoader<List<CityWeather>> {

    private String filename;

    public JsonCityWeatherLoader(JsonLoadParams jsonLoadParams) {
        this.filename = jsonLoadParams.getFilename();
    }

    @Override
    public List<CityWeather> load() {
        List<CityWeather> cityWeathers = new ArrayList<>();
        List<City> cities = new ArrayList<>();

        String jsonStr = readFile(PathConstants.APP_DIRECTORY, PathConstants.CITIES_FILE_NAME);

        CurrentWeather cWeather = new CurrentWeather();
        Weather weather = new Weather();

        IWeatherCodeProvider codeProvider = new OpenweathermapCodeProvider();
        BaseWeather.WeatherUnit units = new BaseWeather.WeatherUnit();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonCityWeathers = jsonObject.getJSONArray("cityWeathers");

            for (int i = 0; i < jsonCityWeathers.length(); i++) {
                JSONObject jsonCityWeather = jsonCityWeathers.getJSONObject(i);

                // We start extracting the info
                Location loc = new Location();

                JSONObject coordObj = jsonCityWeather.getJSONObject("coord");
                //loc.setLatitude(jsonCityWeather.getDouble("lat"));
                //loc.setLongitude(jsonCityWeather.getFloat("lon"));

                JSONObject sysObj = jsonCityWeather.getJSONObject("sys");
                loc.setCountry(sysObj.getString("country"));
                loc.setSunrise(sysObj.getInt("sunrise"));
                loc.setSunset(sysObj.getInt("sunset"));
                loc.setCity(sysObj.getString("name"));

                weather.location = loc;

                // We get weather info (This is an array)
                JSONArray jArr = jsonCityWeather.getJSONArray("weather");

                // We use only the first value
                JSONObject JSONWeather = jArr.getJSONObject(0);
                weather.currentCondition.setWeatherId(JSONWeather.getInt("id"));

                // Convert internal code
                try {
                    weather.currentCondition.setWeatherCode(codeProvider.getWeatherCode(String.valueOf(weather.currentCondition.getWeatherId())));
                }
                catch(Throwable t) {
                    weather.currentCondition.setWeatherCode(WeatherCode.NOT_AVAILABLE);
                }

                weather.currentCondition.setDescr(JSONWeather.getString("description"));
                weather.currentCondition.setCondition(JSONWeather.getString("main"));
                weather.currentCondition.setIcon(JSONWeather.getString("icon"));

                JSONObject mainObj = jsonCityWeather.getJSONObject("main");
                weather.currentCondition.setHumidity(mainObj.getInt("humidity"));
                weather.currentCondition.setPressure((float) mainObj.getDouble("pressure")); //#18
                weather.currentCondition.setPressureGroundLevel((float) mainObj.optDouble("grnd_level"));
                weather.currentCondition.setPressureSeaLevel((float) mainObj.optDouble("sea_level"));
                weather.temperature.setMaxTemp((float) mainObj.getDouble("temp_max"));
                weather.temperature.setMinTemp((float) mainObj.getDouble("temp_min"));
                weather.temperature.setTemp((float) mainObj.getDouble("temp"));

                // Wind
                JSONObject wObj = jsonCityWeather.getJSONObject("wind");
                weather.wind.setSpeed((float) wObj.getDouble("speed"));
                weather.wind.setDeg((float) wObj.getDouble("deg"));
            /*
            try {
                weather.wind.setGust(getFloat("gust", wObj));
            } catch (Throwable t) {
            }


                weather.wind.setGust((float) wObj.optDouble("gust"));
                // Clouds
                JSONObject cObj = jsonCityWeather.getJSONObject("clouds");
                weather.clouds.setPerc(cObj.getInt("all"));

                // Rain (use opt to handle option parameters
                JSONObject rObj = jsonCityWeather.optJSONObject("rain");
                if (rObj != null) {

                    float amm1 = (float) rObj.optDouble("1h");
                    if (amm1 > 0) {
                        weather.rain[0].setAmmount(amm1);
                        weather.rain[0].setTime("1h");
                    }

                    float amm3 = (float) rObj.optDouble("3h");
                    if (amm3 > 0) {
                        weather.rain[1].setAmmount(amm3);
                        weather.rain[1].setTime("3h");
                    }
                }

                // Snow
                JSONObject sObj = jsonCityWeather.optJSONObject("snow");
                if (sObj != null) {
                    weather.snow.setAmmount((float) sObj.optDouble("3h"));
                    weather.snow.setTime("3h");
                }

                City.CityBuilder builder = new City.CityBuilder();
                //cities.add(builder.name(jsonObject.getString("name"))
                //        .country(sysObj.getString("country"))
                //        .id(jsonCityWeather.getString("id")).build());

                cWeather.setUnit(units);
                cWeather.weather = weather;

                cityWeathers.add(new CityWeather(builder.name(jsonCityWeather.getString("name"))
                                .country(sysObj.getString("country"))
                                .id(jsonCityWeather.getString("id")).build(), cWeather));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cityWeathers;
    }


    private String readFile(String directory, String filename) {
        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard.getAbsolutePath() + directory, filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }
}
*/