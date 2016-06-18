package com.github.handioq.weatherapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Class for getting icons from drawable and from resources
 */
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

    /**
     * Get icon from resource for specific weather ID and icon ID.
     * @param ctx Context
     * @param weatherID
     * @param iconID
     * @return String
     */
    public static String getIconResource(Context ctx, int weatherID, String iconID)
    {
        String uri = "";
        String resultResource = "";
        int imageResource = 0;

        if (iconID != null)
        {
            iconID = String.valueOf(iconID.charAt(iconID.length() - 1));

            if (iconID.equals("d")) // if day
            {
                uri = "wi_owm_" + weatherID;
            }
            else
            {
                uri = "wi_owm_day_" + weatherID;
            }
        }

        try {
            imageResource = ctx.getResources().getIdentifier(uri, "string", ctx.getPackageName());
            resultResource = ctx.getString(imageResource);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return resultResource;
    }

    public static String getQueryImageURL(String icon) {
        return IMG_URL + icon + IMG_FORMAT;
    }
}
