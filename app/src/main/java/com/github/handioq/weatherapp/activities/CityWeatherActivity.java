package com.github.handioq.weatherapp.activities;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.adapters.WeatherPagerAdapter;
import com.github.handioq.weatherapp.client.AppWeatherClient;

import java.util.List;
import java.util.Vector;

public class CityWeatherActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private WeatherPagerAdapter mWeatherPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // http://openweathermap.org/price provides a free plan with 5 days / 3 hour forecast API, so...
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, CurrentWeatherFragment.class.getName())); // for current weather
        fragments.add(Fragment.instantiate(this, ThreeHoursForecastFragment.class.getName())); // for three hours forecast
        fragments.add(Fragment.instantiate(this, FiveDaysForecastFragment.class.getName())); // for 5 days forecast
        mWeatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), fragments, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        if (mViewPager != null) {
            mViewPager.setAdapter(mWeatherPagerAdapter);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mViewPager);
        }

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home)
        {
            finish();
        }

        if (id == R.id.action_delete)
        {
            removeCity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeCity()
    {
        String question = getApplicationContext().getResources().getString(R.string.remove_question);
        String answerYes = getApplicationContext().getResources().getString(R.string.answer_yes);
        String answerNo = getApplicationContext().getResources().getString(R.string.answer_no);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CityWeatherActivity.this);
        builder1.setMessage(question);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                answerYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
                        appWeatherClient.removeSelectedCity();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                answerNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
