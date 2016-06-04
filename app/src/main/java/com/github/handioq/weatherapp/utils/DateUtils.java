package com.github.handioq.weatherapp.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM,yy");
    //private static final DateFormat TIME_FORMAT = new SimpleDateFormat("dd MMM,yy ")

    public static String getToday(){
        Date today = new Date();
        Calendar tempcal = new GregorianCalendar();
        tempcal.setTime(today);

        return DATE_FORMAT.format(tempcal.getTime());
    }

    public static String convertTimestampToDate(long timestamp)
    {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());

        return date.toString();
    }

}
