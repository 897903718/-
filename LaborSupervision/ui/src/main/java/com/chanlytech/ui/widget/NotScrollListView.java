package com.chanlytech.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

public class NotScrollListView extends android.widget.ListView
{

	public NotScrollListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public NotScrollListView(Context context)
	{
		super(context);
	}

	public NotScrollListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}