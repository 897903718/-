package com.chanlytech.unicorn.log;

import android.content.Context;
import android.widget.Toast;

import com.chanlytech.unicorn.log.inf.ILogger;

/**
 * 把日志用Toast的方式显示出来
 * Created by higgses on 14-5-29.
 */
public class UinToastLogger implements ILogger
{
    private Context mContext;

    public UinToastLogger(Context context)
    {
        mContext = context;
    }

    @Override
    public int v(String tag, String message)
    {

        return 0;
    }

    @Override
    public int v(String tag, String msg, Throwable tr)
    {
        return 0;
    }

    @Override
    public int d(String tag, String message)
    {
        Toast.makeText(mContext, tag + " | " + message, Toast.LENGTH_SHORT).show();
        return 0;
    }

    @Override
    public int d(String tag, String msg, Throwable tr)
    {
        return 0;
    }

    @Override
    public int i(String tag, String message)
    {

        return 0;
    }

    @Override
    public int i(String tag, String msg, Throwable tr)
    {
        return 0;
    }

    @Override
    public int w(String tag, String message)
    {

        return 0;
    }

    @Override
    public int w(String tag, String msg, Throwable tr)
    {
        return 0;
    }

    @Override
    public int e(String tag, String message)
    {

        return 0;
    }

    @Override
    public int e(String tag, String msg, Throwable tr)
    {
        return 0;
    }

    @Override
    public void open()
    {

    }

    @Override
    public void close()
    {

    }

    @Override
    public void println(int priority, String tag, String message)
    {

    }

    @Override
    public boolean isOpen()
    {
        return true;
    }
}
