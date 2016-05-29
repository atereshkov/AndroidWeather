package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.handioq.weatherapp.R;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesListViewAdapter extends ArrayAdapter<City> {

    private List<City> items;

    public CitiesListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CitiesListViewAdapter(Context context, int resource, List<City> items) {
        super(context, resource, items);
        this.items = items;
    }

    /**
     * Checks if the List is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.city_item, null);
        }

        City city = getItem(position);

        if (city != null)
        {
            //ImageView cityImage = (ImageView) view.findViewById(R.id.cityImage);
            TextView cityTitle = (TextView) view.findViewById(R.id.cityTitle);
            TextView cityWeather = (TextView) view.findViewById(R.id.cityWeather);

            cityTitle.setText(city.getName() + ", " + city.getCountry());
            cityWeather.setText("19 °С");
        }

        return view;
    }

}
