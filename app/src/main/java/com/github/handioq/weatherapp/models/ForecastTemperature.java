package com.github.handioq.weatherapp.models;

public class ForecastTemperature {

    private float day;
    private float night;
    private float eve;
    private float morning;

    public ForecastTemperature() {
    }

    public ForecastTemperature(float day, float night, float eve, float morning) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morning = morning;
    }

    public float getDay() {
        return day;
    }

    public float getNight() {
        return night;
    }

    public float getEve() {
        return eve;
    }

    public float getMorning() {
        return morning;
    }
}
