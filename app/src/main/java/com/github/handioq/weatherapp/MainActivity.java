package com.github.handioq.weatherapp;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textView2;
    private String testCityID;
    private TextView textView4;

    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView hum;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.imgView);

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

        WeatherClient.ClientBuilder builder = new WeatherClient.ClientBuilder();
        WeatherConfig config = new WeatherConfig();

        config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;
        config.lang = "en"; // If you want to use english
        config.maxResult = 5; // Max number of cities retrieved
        config.numDays = 6; // Max num of days in the forecast
        config.ApiKey = "863661c7cccfddd038691c9d714a0266";

        //client.updateWeatherConfig(config);

        WeatherClient client = new WeatherDefaultClient();

        try {
            client = builder.attach(this)
                    .provider(new OpenweathermapProviderType())
                    .httpClient(com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient.class)
                    .config(config)
                    .build();
        } catch (WeatherProviderInstantiationException e) {
            e.printStackTrace();
        }

        client.searchCity("Minsk,BY", new WeatherClient.CityEventListener() {
            @Override
            public void onCityListRetrieved(List<City> cityList) {
                // When the data is ready you can implement your logic here
                textView2.setText(cityList.get(0).getName() + " | id: " + cityList.get(0).getId());
                testCityID = cityList.get(0).getId();
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                textView2.setText("onWeather Error");
                t.printStackTrace();
            }

            @Override
            public void onConnectionError(Throwable t) {
                textView2.setText("onConnectionError");
            }
        });

        client.getCurrentCondition(new WeatherRequest(testCityID), new WeatherClient.WeatherEventListener() {
            @Override public void onWeatherRetrieved(CurrentWeather currentWeather) {
                float currentTemp = currentWeather.weather.temperature.getTemp();
                Log.d("WL", "City ["+currentWeather.weather.location.getCity()+"] Current temp ["+currentTemp+"]");
                textView4.setText("Current temp [" + currentTemp + "]");

                //currentWeather.weather.iconData = ((new WeatherDefaultClient()).getImage(currentWeather.weather.currentCondition.getIcon()));

                if (currentWeather.weather.iconData != null && currentWeather.weather.iconData.length > 0) {
                    Bitmap img = BitmapFactory.decodeByteArray(currentWeather.weather.iconData, 0, currentWeather.weather.iconData.length);
                    imgView.setImageBitmap(img);
                }

                cityText.setText(currentWeather.weather.location.getCity() + "," + currentWeather.weather.location.getCountry());
                condDescr.setText(currentWeather.weather.currentCondition.getCondition() + "(" + currentWeather.weather.currentCondition.getDescr() + ")");
                temp.setText("" + Math.round((currentWeather.weather.temperature.getTemp())) + "C");
                hum.setText("" + currentWeather.weather.currentCondition.getHumidity() + "%");
                press.setText("" + currentWeather.weather.currentCondition.getPressure() + " hPa");
                windSpeed.setText("" + currentWeather.weather.wind.getSpeed() + " mps");
                windDeg.setText("" + currentWeather.weather.wind.getDeg() + "");

            }

            @Override public void onWeatherError(WeatherLibException e) {
                Log.d("WL", "Weather Error - parsing data");
                e.printStackTrace();
            }

            @Override public void onConnectionError(Throwable throwable) {
                Log.d("WL", "Connection error");
                throwable.printStackTrace();
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
