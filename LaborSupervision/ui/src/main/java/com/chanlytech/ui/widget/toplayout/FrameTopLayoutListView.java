package com.chanlytech.ui.widget.toplayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.chanlytech.ui.utils.AndroidVersion;


/**
 * 针对包含一个ListView的FrameTopLayout
 * Created by Carlton on 2014/8/5.
 */
public class FrameTopLayoutListView extends FrameTopLayout
{
    private int pageCount = 10;

    public FrameTopLayoutListView(Context context)
    {
        super(context);
    }

    public FrameTopLayoutListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FrameTopLayoutListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * 设置ListView
     *
     * @param listView
     */
    public void setListView(final ListView listView)
    {
        if (listView == null)
        {
            return;
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem >= pageCount * 0.75)
                {
                    setEnable(true);
                }
                else
                {
                    setEnable(false);
                    hide();
                }
            }
        });
        setBtnClickListener(new TopClickListener()
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v)
            {
                if (listView.getFirstVisiblePosition() > 20)
                {
                    listView.setSelection(20);
                }
                if (AndroidVersion.hasHoneycomb())
                {
                    listView.smoothScrollToPositionFromTop(0, 0);
                }
                else
                {
                    listView.smoothScrollToPosition(0);
                }
            }
        });
    }
}
