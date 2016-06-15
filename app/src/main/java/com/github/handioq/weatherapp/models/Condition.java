package com.github.handioq.weatherapp.models;

public class Condition {

    private String condition;
    private String conditionDescription;
    private float windSpeed;
    private float pressure;
    private float humidity;

    public Condition() {
    }

    public Condition(String condition, String conditionDescription, float windSpeed, float pressure, float humidity) {
        this.condition = condition;
        this.conditionDescription = conditionDescription;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getCondition() {
        return condition;
    }

    public String getConditionDescription() {
        return conditionDescription;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }
}
