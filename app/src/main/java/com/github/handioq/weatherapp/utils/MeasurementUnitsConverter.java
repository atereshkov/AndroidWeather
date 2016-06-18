package com.github.handioq.weatherapp.utils;

/**
 * Class for measurement conversion
 */
public class MeasurementUnitsConverter {

    /**
     * Convert from hpa to mmHG
     * @param hPa float hpa unit
     * @return float mmHg
     */
    public static Float hpaToMmHg(Float hPa)
    {
        return hPa / (float) 1.3332239;
    }

    /**
     * Convert from celsius to fahrenheit units.
     * @param celsius float unit
     * @return Float fahrenheit
     */
    public static Float celsiusToFahrenheit(Float celsius)
    {
        return (celsius * (float) 1.8000) + 32;
    }
}
