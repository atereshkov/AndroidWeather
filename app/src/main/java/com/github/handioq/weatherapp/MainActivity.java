package com.github.handioq.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.handioq.weatherapp.activities.AddCityActivity;
import com.github.handioq.weatherapp.activities.CityWeatherActivity;
import com.github.handioq.weatherapp.activities.SettingsActivity;
import com.github.handioq.weatherapp.adapters.CitiesListViewAdapter;
import com.github.handioq.weatherapp.constants.KeyStore;
import com.github.handioq.weatherapp.models.AppWeatherClient;
import com.github.handioq.weatherapp.utils.AnimatingRefreshButtonManager;
import com.github.handioq.weatherapp.utils.ConnectionDetector;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView citiesListView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CitiesListViewAdapter citiesListViewAdapter;
    private AnimatingRefreshButtonManager mRefreshButtonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        citiesListView = (ListView) findViewById(R.id.citiesListView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipelayout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    Intent addCityIntent = new Intent(MainActivity.this, AddCityActivity.class);
                    startActivity(addCityIntent);
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        //List<City> cities = new ArrayList<City>();
        //City.CityBuilder builder1 = new City.CityBuilder();
        //cities.add(builder1.name("Minsk").country("BY").id("625144").build());
        //cities.add(builder1.name("New York City").country("US").id("5128581").build());

        ImageLoader imageLoader = ImageLoader.getInstance();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration configLoader = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions).build();
        imageLoader.init(configLoader);

        final AppWeatherClient appWeatherClient = AppWeatherClient.getInstance();

        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();
        config.ApiKey = KeyStore.OPENWEATHERMAP_API_KEY;

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
        appWeatherClient.loadCities();

        citiesListViewAdapter = new CitiesListViewAdapter(this,
                android.R.layout.simple_list_item_1, appWeatherClient.getCities(), appWeatherClient.getClient());
        citiesListView.setAdapter(citiesListViewAdapter);

        citiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                Intent selectedCityIntent = new Intent(MainActivity.this, CityWeatherActivity.class);
                startActivity(selectedCityIntent);
                appWeatherClient.setSelectedCity(appWeatherClient.getCities().get(position));

            }
        });
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {

            mRefreshButtonManager.onRefreshBeginning();
            refreshCitiesListView();
            mRefreshButtonManager.onRefreshComplete();

            //simulate doing update for 1000 ms
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }

            }, 1000);
        }};

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
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

        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        mRefreshButtonManager = new AnimatingRefreshButtonManager(this, refreshItem);

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
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
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
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onRefreshClick(MenuItem item){
        mRefreshButtonManager.onRefreshBeginning();
        refreshCitiesListView();
        mRefreshButtonManager.onRefreshComplete();
    }

    private void refreshCitiesListView()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if (!connectionDetector.checkInternetConnection())
        {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.refresh_error_nointernet), Toast.LENGTH_LONG).show();
        }
        else
        {
            citiesListViewAdapter.notifyDataSetChanged();  // citiesListView will be updated
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mRefreshButtonManager.onRefreshBeginning();
        refreshCitiesListView();
        //mRefreshButtonManager.onRefreshComplete();
    }
}
