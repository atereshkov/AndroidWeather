package com.github.handioq.weatherapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.handioq.weatherapp.models.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.utils.MeasurementUnitsConverter;
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

        appWeatherClient.getClient().getCurrentCondition(new WeatherRequest(selectedCity.getId()), new WeatherClient.WeatherEventListener() {
            @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                float currentTemp = currentWeather.weather.temperature.getTemp();
                Log.d("CWF", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");

                tempTextView.setText(Math.round(currentTemp) + getActivity().getResources().getString(R.string.degree_celsius));
                condDescrTextView.setText(currentWeather.weather.currentCondition.getCondition());
                condTextView.setText(currentWeather.weather.currentCondition.getDescr());
                windSpeedTextView.setText(Float.toString(currentWeather.weather.wind.getSpeed())
                        + getActivity().getResources().getString(R.string.meters_per_second));
                pressureTextView.setText(Float.toString(currentWeather.weather.currentCondition.getHumidity())
                        + getActivity().getResources().getString(R.string.percent));
                humidityTextView.setText(Float.toString(Math.round(MeasurementUnitsConverter.hpaToMmHg(currentWeather.weather.currentCondition.getPressure())))
                        + getActivity().getResources().getString(R.string.mm_hg));
            }

            @Override public void onWeatherError(WeatherLibException e) {
                Log.d("WL", "Weather Error - parsing data");
                e.printStackTrace();
            }

            @Override public void onConnectionError(Throwable throwable) {
                Log.d("WL", "Connection error");
                throwable.printStackTrace();
            }
        });

        return V;
    }

}
