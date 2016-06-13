package com.chanlytech.ui.widget.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 循环的数据适配器
 * Created by Carlton on 2014/10/9.
 */
public class BaseAdapterHelper
{
    private static final String TAG       = "CycleAdapterHelper";
    private final        int    COUNT     = 50;
    /**
     * 最小循环的条目数量
     */
    private final        int    MIN_COUNT = 3;
    private int mLength;

    public BaseAdapterHelper(int length)
    {
        mLength = length;
    }

    public int getFirstItemPosition()
    {
        return mLength >= MIN_COUNT ? (COUNT * mLength) : 0;
    }

    public int getCount()
    {
        return mLength >= MIN_COUNT ? Integer.MAX_VALUE : mLength;
    }

    /**
     * 获取相对位置
     *
     * @param position
     *
     * @return
     */
    public int getPosition(int position)
    {
        return position % mLength;
    }

    /**
     * 自动滚动计时
     */
    private Timer mTimer;

    /**
     * 自动滚动
     *
     * @param viewPager
     */
    public void autoScroll(final ViewPager viewPager, int delay, long period)
    {
        if (viewPager == null)
        {
            return;
        }
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.sendEmptyMessage(0);
            }
        }, delay, period);
    }

    /**
     * 取消自动滚动
     */
    public void cancelScroll()
    {
        if (mTimer != null)
        {
            mTimer.cancel();
        }
    }
}
