package com.github.handioq.weatherapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.handioq.weatherapp.client.AppWeatherClient;
import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.adapters.DayForecastExpListAdapter;
import com.github.handioq.weatherapp.utils.AnimatingRefreshButtonManager;
import com.github.handioq.weatherapp.utils.ConnectionDetector;
import com.github.handioq.weatherapp.utils.DateTimeUtils;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FiveDaysForecastFragment extends Fragment {

    AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
    private City selectedCity;

    private SwipeRefreshLayout swipeDayRefreshLayout;
    private AnimatingRefreshButtonManager mRefreshButtonManager;

    ExpandableListView expandableDayListView;
    DayForecastExpListAdapter adapter;

    private TextView errorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.five_days_forecast_layout, container, false);

        selectedCity = appWeatherClient.getSelectedCity();
        getActivity().setTitle(selectedCity.getName());
        expandableDayListView = (ExpandableListView) V.findViewById(R.id.expandableDayListView);

        swipeDayRefreshLayout = (SwipeRefreshLayout) V.findViewById(R.id.swipeDayLayout); // TODO: reworking
        swipeDayRefreshLayout.setOnRefreshListener(onRefreshListener);

        errorTextView = (TextView) V.findViewById(R.id.errorTextView);
        if (errorTextView != null)
            errorTextView.setVisibility(View.INVISIBLE);

        appWeatherClient.getClient().getForecastWeather(new WeatherRequest(selectedCity.getId()), new WeatherClient.ForecastWeatherEventListener() {

            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
                if (errorTextView != null)
                    errorTextView.setVisibility(View.GONE);

                List dayForecastList = forecast.getForecast();

                ArrayList<Map<String, DayForecast>> groupDataList = new ArrayList<>();
                Map<String, DayForecast> map;

                for (int i = 0; i < dayForecastList.size(); i++)
                {
                    DayForecast dayForecast = forecast.getForecast(i);
                    long timestamp = forecast.getForecast(i).timestamp;
                    map = new HashMap<>();
                    map.put(DateTimeUtils.convertTimestampToDate(timestamp, "days"), dayForecast);
                    groupDataList.add(map);
                }

                adapter = new DayForecastExpListAdapter(getContext(), groupDataList);
                expandableDayListView.setAdapter(adapter);
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                t.printStackTrace();
            }

            @Override
            public void onConnectionError(Throwable t) {
                t.printStackTrace();

                if (errorTextView != null)
                    errorTextView.setVisibility(View.VISIBLE);
            }

        });


        return V;
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {

            refreshCitiesListView();

            //simulate doing update for 1000 ms
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    swipeDayRefreshLayout.setRefreshing(false);
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
