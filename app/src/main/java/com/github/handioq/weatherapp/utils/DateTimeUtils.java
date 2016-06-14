package com.github.handioq.weatherapp.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM,yy");
    private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss"); // HH:mm:ss z
    private static final DateFormat TIMESTAMP_FORMAT_DAY = new SimpleDateFormat("dd MMM yyyy");

    public static String getToday(){
        Date today = new Date();
        Calendar tempcal = new GregorianCalendar();
        tempcal.setTime(today);

        return DATE_FORMAT.format(tempcal.getTime());
    }

    public static String convertTimestampToDate(long timestamp, String format)
    {
        String formattedDate = "";
        Date date = new Date(timestamp*1000L); // *1000 is to convert seconds to milliseconds

        if (format.equals("") || format.equals("hours"))
        {
            //sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            formattedDate = TIMESTAMP_FORMAT.format(date);
        }
        else
           if (format.equals("days"))
           {
               //TIMESTAMP_FORMAT_DAY.setTimeZone(TimeZone.getTimeZone("GMT+3"));
               formattedDate = TIMESTAMP_FORMAT_DAY.format(date);
           }

        return formattedDate;
    }



}
