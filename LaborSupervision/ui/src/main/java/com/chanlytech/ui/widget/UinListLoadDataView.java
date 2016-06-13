package com.chanlytech.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * ListView的加载数据的视图
 *
 * @author Higgses
 *
 */
public class UinListLoadDataView extends UinLoadDataView
{

	public UinListLoadDataView(Context context)
	{
		super(context);
	}

	public UinListLoadDataView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public UinListLoadDataView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected void loading()
	{
		super.loading();
		setListEmptyView(GONE);
	}

	@Override
	protected void loadingFail()
	{
		super.loadingFail();
		setListEmptyView(GONE);
	}

	@Override
	protected void invalid()
	{
		super.invalid();
		ListView listView = (ListView) getContentView();
		if (listView.getAdapter().isEmpty())
		{
			setListEmptyView(VISIBLE);
		}
	}

	/**
	 * 设置空视图状态
	 *
	 * @param status
	 */
	private void setListEmptyView(int status)
	{
		ListView listView = (ListView) getContentView();
		View emptyView = listView.getEmptyView();
		if (emptyView != null)
		{
			emptyView.setVisibility(status);
		}
	}
}
