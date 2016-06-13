package com.chanlytech.ui.utils;

import android.view.View;

/**
 * 视图工具
 * Created by Carlton on 2014/12/5.
 */
public class ViewUtils
{
    /**
     * 延迟启动
     *
     * @param view
     * @param time
     */
    public static void delayEnable(final View view, int time)
    {
        view.setEnabled(false);
        view.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                view.setEnabled(true);
            }
        }, time);
    }
}
