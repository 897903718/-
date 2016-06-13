package com.chanlytech.ui.utils;

import android.widget.BaseAdapter;

import com.chanlytech.ui.widget.pullrefresh.PullToRefreshListView;

import java.util.List;

/**
 * 刷新工具
 * Created by Carlton on 2014/12/3.
 */
public class PullRefreshUtils
{
    public static void finish(PullToRefreshListView listView, List oldData, List newData)
    {
        if (listView.getLoadType() == PullToRefreshListView.LoadType.REFRESH)
        {
            oldData.clear();
            oldData.addAll(newData);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
        else
        {
            oldData.addAll(newData);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
        listView.onFinish(newData);
    }
}
