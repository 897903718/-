package com.chanlytech.unicorn.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 */
public class UinToast
{
    private static   String mLastMsg     = "";
    private static   int    lastDuration = 0;
    protected static Toast  mToast       = null;
    private static   long   mLastTime    = 0;

    /**
     * 显示Toast，如果重复的Toast，那么显示的时候如果时间没有超过最后显示的时间，那么就忽略这条消息
     *
     * @param context
     *         Context
     * @param message
     *         显示的字符
     * @param duration
     *         显示时长，单位毫秒duration
     */
    public static void showToast(Context context, String message, int duration)
    {
        mToast = Toast.makeText(context, message, duration);
        lastDuration = duration;

        if (mLastMsg.equals(message))
        {
            long nowTime = System.currentTimeMillis();
            if (nowTime - mLastTime > lastDuration * 1000)
            {

                mToast.show();
                mLastTime = nowTime;
            }
        }
        else
        {
            mToast.show();
            mLastTime = System.currentTimeMillis();
            mLastMsg = message;
        }
    }

    /**
     * 显示Toast
     *
     * @param context
     *         Context
     * @param resId
     *         资源ID
     * @param duration
     *         显示时长，单位毫秒
     */
    public static void showToast(Context context, int resId, int duration)
    {
        showToast(context, context.getString(resId), duration);
    }
}
