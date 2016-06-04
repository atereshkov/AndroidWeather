package com.github.handioq.weatherapp.utils;

public class MeasurementUnitsConverter {

    public static Float hpaToMmHg(Float hPa)
    {
        return hPa / (float) 1.3332239;
    }
}
