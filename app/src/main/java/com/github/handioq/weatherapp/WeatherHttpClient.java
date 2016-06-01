package com.github.handioq.weatherapp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String IMG_FORMAT = ".png";

    public WeatherHttpClient() {
    }

    public String getQueryImageURL(String icon) {
        return IMG_URL + icon + IMG_FORMAT;
    }

}
