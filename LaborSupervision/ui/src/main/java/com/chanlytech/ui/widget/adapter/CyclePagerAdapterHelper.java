package com.chanlytech.ui.widget.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 循环的数据适配器
 * Created by Carlton on 2014/10/9.
 */
public class CyclePagerAdapterHelper extends BaseAdapterHelper
{
    private static final String TAG = "CycleAdapterHelper";
    private Context           mContext;
    private SparseArray<View> mCacheView;
    private OnAdapterListener mListener;

    public CyclePagerAdapterHelper(OnAdapterListener listener, Context context, List data)
    {
        super(data == null ? 0 : data.size());
        mListener = listener;
        mContext = context;
        mCacheView = new SparseArray<>();
    }

    public boolean isViewFromObject(View view, Object o)
    {
        return view == o;
    }

    public Object instantiateItem(ViewGroup container, int position)
    {
        int relativePosition = getPosition(position);
        View cacheView = mCacheView.get(relativePosition);
        if (cacheView == null)
        {
            cacheView = LayoutInflater.from(mContext).inflate(mListener.getLayoutId(), container, false);
            mCacheView.put(relativePosition, cacheView);
            Log.d(TAG, "创建条目:position_" + position + " | relativePosition_" + relativePosition);
        }
        mListener.createItem(cacheView, position);
        container.removeView(cacheView);
        container.addView(cacheView);
        return cacheView;
    }

    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    public static interface OnAdapterListener
    {
        /**
         * 创建条目
         *
         * @param cacheView
         * @param position
         */
        public void createItem(View cacheView, int position);

        /**
         * 获取布局的资源ID
         *
         * @return
         */
        public int getLayoutId();
    }
}
