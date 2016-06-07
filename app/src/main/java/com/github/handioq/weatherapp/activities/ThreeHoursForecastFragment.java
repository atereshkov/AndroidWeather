package com.github.handioq.weatherapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.github.handioq.weatherapp.models.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.adapters.HourForecastExpListAdapter;
import com.github.handioq.weatherapp.utils.ConnectionDetector;
import com.github.handioq.weatherapp.utils.DateUtils;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.HourForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherHourForecast;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThreeHoursForecastFragment  extends Fragment {

    AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
    private City selectedCity;

    private SwipeRefreshLayout swipeRefreshLayout;

    ExpandableListView expandableListView;
    HourForecastExpListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.three_hour_forecast_layout, container, false);

        selectedCity = appWeatherClient.getSelectedCity();
        getActivity().setTitle(selectedCity.getName());
        expandableListView = (ExpandableListView) V.findViewById(R.id.expandableListView);

        swipeRefreshLayout = (SwipeRefreshLayout) V.findViewById(R.id.swipeHourLayout); // TODO: reworking
        if (swipeRefreshLayout != null)
        {
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }

        appWeatherClient.getClient().getHourForecastWeather(new WeatherRequest(selectedCity.getId()), new WeatherClient.HourForecastWeatherEventListener()
        {
            @Override
            public void onWeatherRetrieved(WeatherHourForecast weatherHourForecast) {
                List<HourForecast> hourList = weatherHourForecast.getHourForecast();

                ArrayList<Map<String, Weather>> groupDataList = new ArrayList<>();
                Map<String, Weather> map;

                for (int i = 0; i < hourList.size(); i++)
                {
                    Weather weather = weatherHourForecast.getHourForecast(i).weather;
                    long timestamp = weatherHourForecast.getHourForecast(i).timestamp;
                    map = new HashMap<>();
                    map.put(DateUtils.convertTimestampToDate(timestamp, "hours"), weather);
                    groupDataList.add(map);
                }

                adapter = new HourForecastExpListAdapter(getContext(), groupDataList);
                expandableListView.setAdapter(adapter);
            }

            @Override
            public void onWeatherError(WeatherLibException e) {
                e.printStackTrace();
            }

            @Override public void onConnectionError(Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        return V;
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {

            //mRefreshButtonManager.onRefreshBeginning();
            refreshCitiesListView();
            //mRefreshButtonManager.onRefreshComplete();

            //simulate doing update for 1000 ms
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }

            }, 1000);
        }};

    private void refreshCitiesListView()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getContext());
        if (!connectionDetector.checkInternetConnection())
        {
            Toast.makeText(getContext(), getResources().getString(R.string.refresh_error_nointernet), Toast.LENGTH_LONG).show();
        }
        else
        {
            adapter.notifyDataSetChanged();  // citiesListView will be updated
        }
    }

}
