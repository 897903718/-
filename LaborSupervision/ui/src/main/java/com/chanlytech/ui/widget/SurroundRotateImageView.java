package com.chanlytech.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chanlytech.ui.R;


/**
 * 有环绕图片旋转图片的图片控件
 * Created by higgses on 14-5-18.
 */
public class SurroundRotateImageView extends RelativeLayout
{
    public final static int NORMAL   = 0;
    public final static int PREPARED = 1;
    public final static int START    = 2;

    private Drawable        mRotateDrawable;
    private Drawable        mNormalDrawable;
    private Drawable        mPressDrawable;
    private ImageView       mNormalImageView;
    private ImageView       mRotateImageView;
    private RotateAnimation mAnimation;
    /**
     * 状态
     */
    private int mState = NORMAL;

    public SurroundRotateImageView(Context context)
    {
        super(context);
        init(context, null, 0);
    }

    public SurroundRotateImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SurroundRotateImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * 初始化图片
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void init(Context context, AttributeSet attrs, int defStyle)
    {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SurroundRotateImageView, 0, 0);
        try
        {
            mNormalDrawable = array.getDrawable(R.styleable.SurroundRotateImageView_drawable_normal);
            mRotateDrawable = array.getDrawable(R.styleable.SurroundRotateImageView_drawable_rotate);
            mPressDrawable = array.getDrawable(R.styleable.SurroundRotateImageView_drawable_press);
        } catch (Exception e)
        {

        } finally
        {
            array.recycle();
        }

        mNormalImageView = new ImageView(context);
        mNormalImageView.setImageDrawable(mNormalDrawable);

        mRotateImageView = new ImageView(context);
        mRotateImageView.setVisibility(GONE);
        mRotateImageView.setImageDrawable(mRotateDrawable);

        addView(mNormalImageView);
        addView(mRotateImageView);
    }

    public void updateState()
    {
        switch (mState)
        {
            case NORMAL:
                prepared();
                break;
            case PREPARED:
                start();
                break;
            case START:
                reset();
                break;
        }
    }

    /**
     * 是否已经开始，如果状态是START则返回true
     *
     * @return
     */
    public boolean isStart()
    {
        return START == getState();
    }
    /**
     * 是否在准备状态，如果状态是PREPARED，则返回true
     */
    public boolean isPrepared()
    {
        return PREPARED == getState();
    }

    /**
     * 是否在重置状态或者正常状态，如果状态是NORMAL，则返回true
     * @return
     */
    public boolean isNormal()
    {
        return NORMAL == getState();
    }
    /**
     * 停止旋转,进入开始状态
     */
    public void start()
    {
        reset();
        mNormalImageView.setImageDrawable(mPressDrawable);
        mState = START;
    }

    /**
     * 重置状态
     */
    public void reset()
    {
        if (mAnimation != null)
        {
            mRotateImageView.clearAnimation();
        }
        mNormalImageView.setImageDrawable(mNormalDrawable);
        mRotateImageView.setVisibility(GONE);
        mState = NORMAL;
    }

    /**
     * 进入旋转状态
     */
    public void prepared()
    {
        if (mAnimation == null)
        {
            mAnimation = getRotateAnimation();
        }
        mNormalImageView.setImageDrawable(mPressDrawable);
        mRotateImageView.setVisibility(VISIBLE);
        mRotateImageView.startAnimation(mAnimation);
        mState = PREPARED;
    }

    /**
     * 返回一个旋转的动画
     *
     * @return
     */
    private RotateAnimation getRotateAnimation()
    {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360f, mNormalImageView.getWidth() * 0.5f, mNormalImageView.getHeight() * 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatMode(Animation.INFINITE);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(getContext(), android.R.anim.linear_interpolator);
        return rotateAnimation;
    }

    public int getState()
    {
        return mState;
    }
}
