package com.github.handioq.weatherapp.models;

public class Location {

    private String country;
    private String name;
    private String id;

    public Location() {
    }

    public Location(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }
}
