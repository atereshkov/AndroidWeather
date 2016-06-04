package com.github.handioq.weatherapp.utils;

public class MeasurementUnitsConverter {

    public static Float hPaToMmHg(Float hPa)
    {
        return hPa / (float) 1.3332239;
    }
}
