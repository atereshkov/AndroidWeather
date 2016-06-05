package com.github.handioq.weatherapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class IconUtils {

    public static Drawable getIconFromDrawable(Context ctx, String iconID)
    {
        String uri = "mipmap/icon_" + iconID;
        // int imageResource = R.drawable.icon;
        int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());

        Drawable image = ctx.getResources().getDrawable(imageResource);
        return image;
    }
}
