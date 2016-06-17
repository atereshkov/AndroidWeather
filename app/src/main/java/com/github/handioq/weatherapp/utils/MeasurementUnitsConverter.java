package com.github.handioq.weatherapp.utils;

/**
 * Class for measurement conversion
 */
public class MeasurementUnitsConverter {

    public static Float hpaToMmHg(Float hPa)
    {
        return hPa / (float) 1.3332239;
    }

    public static Float celsiusToFahrenheit(Float celsius)
    {
        return (celsius * (float) 1.8000) + 32;
    }
}
