package com.github.handioq.weatherapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.handioq.weatherapp.activities.CityWeatherActivity;
import com.github.handioq.weatherapp.adapters.CitiesListViewAdapter;
import com.github.handioq.weatherapp.constants.PathConstants;
import com.github.handioq.weatherapp.loader.ILoader;
import com.github.handioq.weatherapp.loader.JsonFileLoader;
import com.github.handioq.weatherapp.loader.JsonLoadParams;
import com.github.handioq.weatherapp.saver.ISaver;
import com.github.handioq.weatherapp.saver.JsonFileSaver;
import com.github.handioq.weatherapp.saver.JsonSaveParams;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView citiesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        citiesListView = (ListView) findViewById(R.id.citiesListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        JsonLoadParams fileLoadParams = new JsonLoadParams(PathConstants.CITIES_FILE_NAME);
        ILoader<List<City>> fromFileLoader = new JsonFileLoader(fileLoadParams);
        List<City> cities = new ArrayList<City>(fromFileLoader.load());

        //List<City> cities = new ArrayList<City>();
        //City.CityBuilder builder1 = new City.CityBuilder();
        //cities.add(builder1.name("Minsk").country("BY").id("625144").build());
        //cities.add(builder1.name("New York City").country("US").id("5128581").build());

        AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();
        appWeatherClient.setCities(cities);

        JsonSaveParams jsonSaveParams = new JsonSaveParams(PathConstants.CITIES_FILE_NAME, cities);
        ISaver toFileSaver = new JsonFileSaver(jsonSaveParams);
        toFileSaver.Save();

        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();

        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        config.lang = "en"; // english
        config.maxResult = 5; // max number of cities retrieved
        config.numDays = 6; // max num of days in the forecast
        config.ApiKey = "863661c7cccfddd038691c9d714a0266";

        //client.updateWeatherConfig(config);

        WeatherClient client = new WeatherDefaultClient();

        try {
            client = builder.attach(this)
                    .provider(new OpenweathermapProviderType()) // use openweathermapAPI
                    .httpClient(com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient.class)
                    .config(config)
                    .build();
        } catch (WeatherProviderInstantiationException e) {
            e.printStackTrace();
        }

        appWeatherClient.setClient(client);
        appWeatherClient.setConfig(config);

        CitiesListViewAdapter citiesListViewAdapter = new CitiesListViewAdapter(this,
                android.R.layout.simple_list_item_1, appWeatherClient.getCities(), appWeatherClient.getClient());
        citiesListView.setAdapter(citiesListViewAdapter);

        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                Intent selectedCityIntent = new Intent(MainActivity.this, CityWeatherActivity.class);
                //passwordInformIntent.putExtra("password", parent.getItemAtPosition(position));
                startActivity(selectedCityIntent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
