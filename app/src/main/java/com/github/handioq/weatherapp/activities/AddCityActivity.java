package com.github.handioq.weatherapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.github.handioq.weatherapp.R;
import com.github.handioq.weatherapp.adapters.CitySearchListAdapter;
import com.github.handioq.weatherapp.client.AppWeatherClient;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.City;

import java.util.List;

public class AddCityActivity extends AppCompatActivity { // TODO: IMPLEMENT INTERNET CONNECTION CHECKING

    private EditText cityEditText;
    private ListView citySearchListView;

    AppWeatherClient appWeatherClient;

    private CitySearchListAdapter citySearchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cityEditText = (EditText) findViewById(R.id.cityEditText);
        citySearchListView = (ListView) findViewById(R.id.citySearchListView);

        appWeatherClient = AppWeatherClient.getInstance();

        cityEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //if (count > 3)
                appWeatherClient.getClient().searchCity(s.toString(), new WeatherClient.CityEventListener() {
                    @Override
                    public void onCityListRetrieved(List<City> cityList) {
                        if (!cityList.isEmpty())
                        {
                            citySearchListAdapter = new CitySearchListAdapter(AddCityActivity.this,
                                    android.R.layout.simple_list_item_1, cityList);
                            citySearchListView.setAdapter(citySearchListAdapter);
                        }
                    }

                    @Override
                    public void onWeatherError(WeatherLibException t) {
                        //t.printStackTrace();
                        // cities not found for this query
                    }

                    @Override
                    public void onConnectionError(Throwable t) {
                        t.printStackTrace(); // internet problems?
                    }
                });
            }
        });

        citySearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int cityPosition = position;
                String question = getApplicationContext().getResources().getString(R.string.add_question);
                String answerYes = getApplicationContext().getResources().getString(R.string.answer_yes);
                String answerNo = getApplicationContext().getResources().getString(R.string.answer_no);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddCityActivity.this);
                builder1.setMessage(question);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        answerYes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Object obj = citySearchListView.getAdapter().getItem(cityPosition);
                                appWeatherClient.addCity((City) obj);
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
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_city_activity, menu);
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

        return super.onOptionsItemSelected(item);
    }

}
