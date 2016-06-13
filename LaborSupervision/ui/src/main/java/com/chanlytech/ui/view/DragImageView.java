package com.chanlytech.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * *
 * 这里你要明白几个方法执行的流程： 首先ImageView是继承自View的子类.
 * onLayout方法：是一个回调方法.该方法会在在View中的layout方法中执行，在执行layout方法前面会首先执行setFrame方法.
 * layout方法：
 * setFrame方法：判断我们的View是否发生变化，如果发生变化，那么将最新的l，t，r，b传递给View，然后刷新进行动态更新UI.
 * 并且返回ture.没有变化返回false.
 * <p/>
 * invalidate方法：用于刷新当前控件,
 *
 * @author iaiai
 */
public class DragImageView extends ImageView
{
    /**
     * 记录两指同时放在屏幕上时，中心点的横坐标值
     */
    private float centerPointX;

    /**
     * 记录两指同时放在屏幕上时，中心点的纵坐标值
     */
    private float centerPointY;

    public DragImageView(Context context)
    {
        super(context);
    }

    public DragImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DragImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_POINTER_DOWN:
                handlerPointerDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2)
                {
                    handlerPointerMove2(event);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void handlerPointerMove2(MotionEvent event)
    {

    }

    private float mOneStart, mSecondStart;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void handlerPointerDown(MotionEvent event)
    {
        // 有两个手指按在屏幕上移动时，为缩放状态
        centerPointBetweenFingers(event);
        double fingerDis = distanceBetweenFingers(event);
        setScaleX((float) fingerDis);
        setScaleY((float) fingerDis);

    }

    /**
     * 计算两个手指之间的距离。
     *
     * @param event
     *
     * @return 两个手指之间的距离
     */
    private double distanceBetweenFingers(MotionEvent event)
    {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(disX * disX + disY * disY);
    }

    /**
     * 计算两个手指之间中心点的坐标。
     *
     * @param event
     */
    private void centerPointBetweenFingers(MotionEvent event)
    {
        float xPoint0 = event.getX(0);
        float yPoint0 = event.getY(0);
        float xPoint1 = event.getX(1);
        float yPoint1 = event.getY(1);
        centerPointX = (xPoint0 + xPoint1) / 2;
        centerPointY = (yPoint0 + yPoint1) / 2;
    }
}
