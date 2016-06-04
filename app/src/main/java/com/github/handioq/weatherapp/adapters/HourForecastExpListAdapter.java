package com.github.handioq.weatherapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.github.handioq.weatherapp.R;
import com.survivingwithandroid.weather.lib.model.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HourForecastExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Map<String, Weather>> mGroups;
    private Context mContext;

    public HourForecastExpListAdapter(Context context, ArrayList<Map<String, Weather>> mGroups) {
        this.mContext = context;
        this.mGroups = mGroups;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
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
            convertView = inflater.inflate(R.layout.hour_forecast_group_view, null);
        }

        if (isExpanded){
            // change something if group expanded
        }
        else
        {
            // otherwise
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        List<String> timeList = new ArrayList<String>(mGroups.get(groupPosition).keySet());
        String time = timeList.get(0);
        textGroup.setText(time);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hour_forecast_child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
        List<Weather> weatherList = new ArrayList<Weather>(mGroups.get(groupPosition).values());

        textChild.setText("Temp: " + weatherList.get(0).temperature.getTemp());

        /*Button button = (Button)convertView.findViewById(R.id.buttonChild);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"button is pressed",5000).show();
            }
        });*/

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}