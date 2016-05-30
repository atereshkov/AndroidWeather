package com.github.handioq.weatherapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.handioq.weatherapp.R;

import java.util.List;

public class WeatherPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private Context context;

    public WeatherPagerAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public Fragment getItem(int arg0) {
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.page_title_now);
            case 1:
                return context.getResources().getString(R.string.page_title_three_hours);
            case 2:
                return context.getResources().getString(R.string.page_title_five_days);
        }
        return null;
    }

}
