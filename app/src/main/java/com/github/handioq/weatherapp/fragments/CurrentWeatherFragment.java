package com.github.handioq.weatherapp.fragments;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.handioq.weatherapp.client.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.client.OfflineWeatherClient;
import com.github.handioq.weatherapp.models.Condition;
import com.github.handioq.weatherapp.models.Location;
import com.github.handioq.weatherapp.models.Temperature;
import com.github.handioq.weatherapp.utils.IconUtils;
import com.github.handioq.weatherapp.utils.MeasurementUnitsConverter;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

public class CurrentWeatherFragment extends Fragment {

    AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
    private City selectedCity;

    private TextView tempTextView;
    private TextView condDescrTextView;
    private TextView condTextView;
    private TextView windSpeedTextView;
    private TextView pressureTextView;
    private TextView humidityTextView;
    private WeatherIconView weatherIcon;

    private SharedPreferences sharedPrefs;
    private OfflineWeatherClient offlineWeatherClient = new OfflineWeatherClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.current_weather_layout, container, false);

        selectedCity = appWeatherClient.getSelectedCity();

        getActivity().setTitle(selectedCity.getName());

        tempTextView = (TextView) V.findViewById(R.id.tempTextView);
        condDescrTextView = (TextView) V.findViewById(R.id.condDescrTextView);
        condTextView = (TextView) V.findViewById(R.id.condTextView);
        windSpeedTextView = (TextView) V.findViewById(R.id.windSpeedTextView);
        humidityTextView = (TextView) V.findViewById(R.id.humidityTextView);
        pressureTextView = (TextView) V.findViewById(R.id.pressureTextView);
        weatherIcon = (WeatherIconView) V.findViewById(R.id.mainWeatherIcon);

        appWeatherClient.getClient().getCurrentCondition(new WeatherRequest(selectedCity.getId()), new WeatherClient.WeatherEventListener() {
            @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                float currentTemp = currentWeather.weather.temperature.getTemp();
                String cond = currentWeather.weather.currentCondition.getCondition();
                String condDescription = currentWeather.weather.currentCondition.getDescr();
                int weatherID = currentWeather.weather.currentCondition.getWeatherId();
                String iconID = currentWeather.weather.currentCondition.getIcon();
                float windSpeed = currentWeather.weather.wind.getSpeed();
                float humid = currentWeather.weather.currentCondition.getHumidity();
                float press = currentWeather.weather.currentCondition.getPressure();

                Log.d("CWF", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");

                Resources res = getActivity().getResources();
                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String unit = sharedPrefs.getString("units_list", res.getString(R.string.degree_celsius));

                int degreeTemp;
                if (unit.equals(res.getString(R.string.degree_celsius))) {
                    degreeTemp = Math.round(currentTemp);
                }
                else {
                    degreeTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(currentTemp));
                }

                String temp = degreeTemp + unit;

                String condition = String.format(res.getString(R.string.condition), cond);
                String conditionDescr = String.format(res.getString(R.string.condition_description), condDescription);
                String wind = String.format(res.getString(R.string.wind), Float.toString(windSpeed))
                        + getActivity().getResources().getString(R.string.meters_per_second);
                String pressure = String.format(res.getString(R.string.pressure), Float.toString(humid))
                        +  getActivity().getResources().getString(R.string.percent);
                String humidity = String.format(res.getString(R.string.humidity), Float.toString(Math.round(MeasurementUnitsConverter.hpaToMmHg(press))))
                        + getActivity().getResources().getString(R.string.mm_hg);

                tempTextView.setText(temp);
                windSpeedTextView.setText(wind);
                pressureTextView.setText(pressure);
                humidityTextView.setText(humidity);

                condDescrTextView.setText(condition);
                condTextView.setText(conditionDescr);

                weatherIcon.setIconResource(IconUtils.getIconResource(getActivity(), weatherID, iconID));

                Condition conditionToSave = new Condition(condition, condDescription, windSpeed, press, humid);
                Location locationToSave = new Location(selectedCity.getName(), selectedCity.getCountry());

                Temperature temperatureToSave = new Temperature(currentTemp);

                com.github.handioq.weatherapp.models.CurrentWeather currWeather =
                        new com.github.handioq.weatherapp.models.CurrentWeather(conditionToSave, locationToSave, temperatureToSave, iconID, weatherID);
                offlineWeatherClient.saveCurrentWeather(currWeather); // save current weather to file
            }

            @Override public void onWeatherError(WeatherLibException e) {
                Log.d("WL", "Weather Error - parsing data");
                e.printStackTrace();
            }

            @Override public void onConnectionError(Throwable throwable) {
                Log.d("WL", "Connection error");
                throwable.printStackTrace();

                com.github.handioq.weatherapp.models.CurrentWeather currentWeather = offlineWeatherClient.loadCurrentWeather(selectedCity);

                float currentTemp = currentWeather.getTemperature().getTemp();
                String cond = currentWeather.getCondition().getCondition();
                String condDescription = currentWeather.getCondition().getConditionDescription();
                int weatherID = currentWeather.getWeatherID();
                String iconID = currentWeather.getIconID();
                float windSpeed = currentWeather.getCondition().getWindSpeed();
                float humid = currentWeather.getCondition().getHumidity();
                float press = currentWeather.getCondition().getPressure();

                Log.d("CWF_OFFLINE", "City ["+selectedCity.getName()+"] Current temp ["+currentTemp+"]");

                Resources res = getActivity().getResources();
                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String unit = sharedPrefs.getString("units_list", res.getString(R.string.degree_celsius));

                int degreeTemp;
                if (unit.equals(res.getString(R.string.degree_celsius))) {
                    degreeTemp = Math.round(currentTemp);
                }
                else {
                    degreeTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(currentTemp));
                }

                String temp = degreeTemp + unit;

                String condition = String.format(res.getString(R.string.condition), cond);
                String conditionDescr = String.format(res.getString(R.string.condition_description), condDescription);
                String wind = String.format(res.getString(R.string.wind), Float.toString(windSpeed))
                        + getActivity().getResources().getString(R.string.meters_per_second);
                String pressure = String.format(res.getString(R.string.pressure), Float.toString(humid))
                        +  getActivity().getResources().getString(R.string.percent);
                String humidity = String.format(res.getString(R.string.humidity), Float.toString(Math.round(MeasurementUnitsConverter.hpaToMmHg(press))))
                        + getActivity().getResources().getString(R.string.mm_hg);

                tempTextView.setText(temp);
                windSpeedTextView.setText(wind);
                pressureTextView.setText(pressure);
                humidityTextView.setText(humidity);

                condDescrTextView.setText(condition);
                condTextView.setText(conditionDescr);

                weatherIcon.setIconResource(IconUtils.getIconResource(getActivity(), weatherID, iconID));
            }
        });

        return V;
    }

}
