package com.github.handioq.weatherapp.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class DateUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM,yy");
    private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss"); // HH:mm:ss z

    public static String getToday(){
        Date today = new Date();
        Calendar tempcal = new GregorianCalendar();
        tempcal.setTime(today);

        return DATE_FORMAT.format(tempcal.getTime());
    }

    public static String convertTimestampToDate(long timestamp)
    {
        //Timestamp stamp = new Timestamp(timestamp);
        //Date date = new Date(stamp.getTime());

        Date date = new Date(timestamp*1000L); // *1000 is to convert seconds to milliseconds
        //SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        String formattedDate = TIMESTAMP_FORMAT.format(date);
        //System.out.println(formattedDate);

        return formattedDate;
    }

}
