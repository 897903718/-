package com.chanlytech.ui.widget.pullrefresh;

/**
 * 数据加载监听
 * Created by Carlton on 2014/7/17.
 */
public interface IDataLoadListener
{
    public void onRefresh(PullToRefreshBase refreshView);

    public void onLoadMore(int i);
}
