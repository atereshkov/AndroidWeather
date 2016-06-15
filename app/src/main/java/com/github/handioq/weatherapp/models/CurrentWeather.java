package com.github.handioq.weatherapp.models;

public class CurrentWeather {

    private String iconID;
    private int weatherID;
    private Condition condition = new Condition();
    private Location location = new Location();
    private Temperature temperature = new Temperature();

    public CurrentWeather() {
    }

    public CurrentWeather(Condition condition, Location location, Temperature temperature, String iconID, int weatherID) {
        this.iconID = iconID;
        this.weatherID = weatherID;
        this.condition = condition;
        this.location = location;
        this.temperature = temperature;
    }

    public String getIconID() {
        return iconID;
    }

    public int getWeatherID() {
        return weatherID;
    }

    public Condition getCondition() {
        return condition;
    }

    public Location getLocation() {
        return location;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}
