package com.github.handioq.weatherapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class IconUtils {

    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String IMG_FORMAT = ".png";

    public static Drawable getIconFromDrawable(Context ctx, String iconID)
    {
        String uri = "mipmap/icon_" + iconID;
        // int imageResource = R.drawable.icon;
        int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());

        Drawable image = ctx.getResources().getDrawable(imageResource);
        return image;
    }

    public static String getIconResource(Context ctx, int weatherID, String iconID)
    {
        String uri = "";
        int imageResource = 0;
        iconID = String.valueOf(iconID.charAt(iconID.length() - 1));

        if (iconID.equals("d")) // if day
        {
            uri = "wi_owm_" + weatherID;
        }
        else
        {
            uri = "wi_owm_day_" + weatherID;
        }

        try {
            imageResource = ctx.getResources().getIdentifier(uri, "string", ctx.getPackageName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ctx.getString(imageResource);
    }

    public static String getQueryImageURL(String icon) {
        return IMG_URL + icon + IMG_FORMAT;
    }
}
