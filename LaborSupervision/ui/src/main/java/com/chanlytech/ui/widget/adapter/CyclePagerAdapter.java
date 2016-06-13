package com.chanlytech.ui.widget.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 循环的PagerAdapter
 * Created by Carlton on 2014/10/9.
 */
public abstract class CyclePagerAdapter<T> extends PagerAdapter implements CyclePagerAdapterHelper.OnAdapterListener
{
    private static final String TAG = "CyclePagerAdapter";
    private CyclePagerAdapterHelper mCycleAdapterHelper;

    public CyclePagerAdapter(Context context, List<T> data)
    {
        mCycleAdapterHelper = new CyclePagerAdapterHelper(this, context, data);
    }

    public int getFirstItemPosition()
    {
        return mCycleAdapterHelper.getFirstItemPosition();
    }

    @Override
    public int getCount()
    {
        return mCycleAdapterHelper.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return mCycleAdapterHelper.isViewFromObject(view, o);
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
        return mCycleAdapterHelper.getPosition(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        return mCycleAdapterHelper.instantiateItem(container, position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        mCycleAdapterHelper.destroyItem(container, position, object);
    }

    public void autoScroll(ViewPager viewPager, int delay, long period)
    {
        mCycleAdapterHelper.autoScroll(viewPager, delay, period);
    }

    public void cancelScroll()
    {
        mCycleAdapterHelper.cancelScroll();
    }

}
