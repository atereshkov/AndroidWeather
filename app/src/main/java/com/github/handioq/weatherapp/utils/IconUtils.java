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

    public static String getQueryImageURL(String icon) {
        return IMG_URL + icon + IMG_FORMAT;
    }

}
