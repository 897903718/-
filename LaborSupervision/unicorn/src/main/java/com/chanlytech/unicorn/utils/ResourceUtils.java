package com.chanlytech.unicorn.utils;

import android.content.Context;

public class ResourceUtils
{
    public static final String DRAWABLE = "drawable";
    public static final String STYLE    = "style";
    public static final String LAYOUT   = "layout";
    public static final String ID       = "id";
    public static final String STRING   = "string";

    public static int getIdentifierByName(Context context, String name, String defType)
    {
        if (context == null)
        {
            return 0;
        }
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

    public static int getDrawableIdentifier(Context context, String name)
    {
        return getIdentifierByName(context, name, DRAWABLE);
    }

    public static int getStyleIdentifier(Context context, String name)
    {
        return getIdentifierByName(context, name, STYLE);
    }

    public static int getLayoutIdentifier(Context context, String name)
    {
        return getIdentifierByName(context, name, LAYOUT);
    }

    public static int getIdIdentifier(Context context, String name)
    {
        return getIdentifierByName(context, name, ID);
    }

    public static int getStringIdentifier(Context context, String name)
    {
        return getIdentifierByName(context, name, STRING);
    }
}