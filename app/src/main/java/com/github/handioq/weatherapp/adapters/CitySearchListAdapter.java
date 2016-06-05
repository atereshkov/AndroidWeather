package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.handioq.weatherapp.R;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public class CitySearchListAdapter extends ArrayAdapter<City> {

    private List<City> cityList;
    private Context context;

    public CitySearchListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CitySearchListAdapter(Context context, int resource, List<City> cityList) {
        super(context, resource, cityList);
        this.cityList = cityList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.search_city_item, null);
        }

        City city = getItem(position);

        if (city != null)
        {
            TextView cityTitle = (TextView) view.findViewById(R.id.cityTitle);
            TextView cityCountry = (TextView) view.findViewById(R.id.cityCountry);

            cityTitle.setText(city.getName());
            cityCountry.setText(city.getCountry());
        }

        return view;
    }

}
