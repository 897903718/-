package com.chanlytech.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;


/**
 * 旋转的ImageView
 * Created by Carlton on 14-3-24.
 */
public class RotateImageView extends ImageView
{
    /**
     * 旋转的动画
     */
    private Animation rotateAnimation;
    /**
     * 是否自动旋转
     */
    private boolean isAutoRotate = true;
    /**
     * 旋转一圈的时间
     */
    private int     duration     = 1000;

    public RotateImageView(Context context)
    {
        super(context);
        init();
    }

    public RotateImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化动画
     */
    private void init()
    {
        if (isAutoRotate)
        {
            createAnimation();
            startRotate();
        }
    }

    /**
     * 创建动画
     *
     * @param repeatCount
     */
    public void createAnimation(int repeatCount)
    {
        rotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(repeatCount);
    }

    /**
     * 创建动画
     */
    private void createAnimation()
    {
        createAnimation(Animation.INFINITE);
    }

    /**
     * 停止动画
     */
    public void stopRotate()
    {
        rotateAnimation.cancel();
        clearAnimation();
    }

    /**
     * 开始旋转
     */
    public void startRotate()
    {
        startAnimation(rotateAnimation);
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
