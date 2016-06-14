package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.utils.IconUtils;
import com.github.handioq.weatherapp.utils.MeasurementUnitsConverter;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DayForecastExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Map<String, DayForecast>> weatherGroups;
    private Context mContext;

    private SharedPreferences sharedPrefs;

    public DayForecastExpListAdapter(Context context, ArrayList<Map<String, DayForecast>> weatherGroups) {
        this.mContext = context;
        this.weatherGroups = weatherGroups;
    }

    @Override
    public int getGroupCount() {
        return weatherGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return weatherGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return weatherGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return weatherGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_forecast_group_view, null);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textDayGroup);
        TextView textGroupInfo = (TextView) convertView.findViewById(R.id.textDayGroupInfo);
        TextView textGroupTemp = (TextView) convertView.findViewById(R.id.textDayGroupTemp);
        TextView textGroupTemp2 = (TextView) convertView.findViewById(R.id.textDayGroupTemp2);
        ImageView weatherImageDay = (ImageView) convertView.findViewById(R.id.weatherImageDay);

        List<String> timeList = new ArrayList<>(weatherGroups.get(groupPosition).keySet());
        String time = timeList.get(0); // get time

        List<DayForecast> weatherList = new ArrayList<>(weatherGroups.get(groupPosition).values());
        DayForecast currentDayForecast = weatherList.get(0); // get weather for this day
        Resources res = mContext.getResources();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String unit = sharedPrefs.getString("units_list", res.getString(R.string.degree_celsius));

        int nightT;
        int dayT;

        if (unit.equals(res.getString(R.string.degree_celsius))) {
            nightT = Math.round(currentDayForecast.forecastTemp.night);
            dayT = Math.round(currentDayForecast.forecastTemp.day);
        }
        else {
            nightT = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(currentDayForecast.forecastTemp.night));
            dayT = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(currentDayForecast.forecastTemp.day));
        }

        String nightTemp = nightT + unit;
        String dayTemp = dayT + unit;
        String condition = currentDayForecast.weather.currentCondition.getCondition();

        String groupTempDay = String.format(res.getString(R.string.day_group_info_title_day), dayTemp);
        String groupTempNight = String.format(res.getString(R.string.day_group_info_title_night), nightTemp);
        String groupText = String.format(res.getString(R.string.day_group_info_center), condition);

        textGroupTemp.setText(groupTempNight);
        textGroupTemp2.setText(groupTempDay);
        textGroup.setText(time);
        textGroupInfo.setText(groupText);
        weatherImageDay.setImageDrawable(IconUtils.getIconFromDrawable(mContext, currentDayForecast.weather.currentCondition.getIcon()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_forecast_child_view, null);
        }

        TextView tempMornView = (TextView) convertView.findViewById(R.id.tempMornText);
        TextView tempDayView = (TextView) convertView.findViewById(R.id.tempDayText);
        TextView tempEvenView = (TextView) convertView.findViewById(R.id.tempEvenText);
        TextView tempNightView = (TextView) convertView.findViewById(R.id.tempNightText);
        TextView pressureText = (TextView) convertView.findViewById(R.id.pressureText);
        TextView humidityText = (TextView) convertView.findViewById(R.id.humidityText);
        TextView condText = (TextView) convertView.findViewById(R.id.condText);
        TextView condDescrText = (TextView) convertView.findViewById(R.id.condDescrText);
        TextView windSpeedText = (TextView) convertView.findViewById(R.id.windSpeedText);

        List<DayForecast> weatherList = new ArrayList<DayForecast>(weatherGroups.get(groupPosition).values());
        DayForecast dayForecast = weatherList.get(0);

        Resources res = mContext.getResources();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String unit = sharedPrefs.getString("units_list", res.getString(R.string.degree_celsius));

        int morningTemp;
        int dayTemp;
        int eveTemp;
        int nightTemp;
        if (unit.equals(res.getString(R.string.degree_celsius))) {
            morningTemp = Math.round(dayForecast.forecastTemp.morning);
            dayTemp = Math.round(dayForecast.forecastTemp.day);
            eveTemp = Math.round(dayForecast.forecastTemp.eve);
            nightTemp = Math.round(dayForecast.forecastTemp.night);
        }
        else {
            morningTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(dayForecast.forecastTemp.morning));
            dayTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(dayForecast.forecastTemp.day));
            eveTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(dayForecast.forecastTemp.eve));
            nightTemp = Math.round(MeasurementUnitsConverter.celsiusToFahrenheit(dayForecast.forecastTemp.night));
        }

        String tempMorning = String.format(res.getString(R.string.day_temp_morning), morningTemp) + unit;
        String tempDay = String.format(res.getString(R.string.day_temp_day), dayTemp) + unit;
        String tempEvening = String.format(res.getString(R.string.day_temp_evening), eveTemp) + unit;
        String tempNight = String.format(res.getString(R.string.day_temp_night), nightTemp) + unit;

        tempMornView.setText(tempMorning);
        tempDayView.setText(tempDay);
        tempEvenView.setText(tempEvening);
        tempNightView.setText(tempNight);

        String condition = String.format(res.getString(R.string.condition), dayForecast.weather.currentCondition.getCondition());
        String conditionDescr = String.format(res.getString(R.string.condition_description), dayForecast.weather.currentCondition.getDescr());
        String wind = String.format(res.getString(R.string.wind), Float.toString(dayForecast.weather.wind.getSpeed()))
                + mContext.getResources().getString(R.string.meters_per_second);
        String pressure = String.format(res.getString(R.string.pressure), Float.toString(dayForecast.weather.currentCondition.getHumidity()))
                +  mContext.getResources().getString(R.string.percent);
        String humidity = String.format(res.getString(R.string.humidity), Float.toString(Math.round(MeasurementUnitsConverter.hpaToMmHg(dayForecast.weather.currentCondition.getPressure())))
                + mContext.getResources().getString(R.string.mm_hg));

        condDescrText.setText(conditionDescr);
        condText.setText(condition);
        windSpeedText.setText(wind);
        pressureText.setText(pressure);
        humidityText.setText(humidity);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
