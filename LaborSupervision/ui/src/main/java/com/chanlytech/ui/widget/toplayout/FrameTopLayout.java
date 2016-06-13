package com.chanlytech.ui.widget.toplayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chanlytech.ui.R;
import com.chanlytech.ui.widget.inf.IViewListener;

/**
 * 一个在底部可以包含一个按钮的布局
 */
public class FrameTopLayout extends FrameLayout implements View.OnTouchListener
{
    /**
     * 按钮的图片
     */
    private ImageView        mTopView;
    /**
     * 被包含的内容视图
     */
    private View             mContentView;
    /**
     * 按钮的点击事件
     */
    private TopClickListener mClickListener;
    /**
     * 点击到的位置
     */
    private PointF mFirstClickPosition = new PointF();
    /**
     * 滑动的距离
     */
    private float  mSlideSpace         = 50;
    /**
     * 按钮的下边距
     */
    private int      mBottomMargin;
    /**
     * 按钮的右边距
     */
    private int      mRightMargin;
    /**
     * 按钮的图片
     */
    private Drawable mBtnDrawable;
    /**
     * 动画持续时间
     */
    private long    mAnimationDuration = 300;
    /**
     * 是否开启
     */
    private boolean enable             = true;

    public FrameTopLayout(Context context)
    {
        super(context);
        init(null, 0);
    }

    public FrameTopLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public FrameTopLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    /**
     * 初始化
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle)
    {
        initStyleableData(attrs, defStyle);
        mTopView = new ImageView(getContext());
        mTopView.setId(R.id.frame_top_layout_btn);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        layoutParams.setMargins(0, 0, mRightMargin, mBottomMargin);
        mTopView.setLayoutParams(layoutParams);
        mTopView.setImageDrawable(mBtnDrawable);
        mTopView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mTopView.setVisibility(GONE);
                if (mClickListener != null)
                {
                    mClickListener.onClick(v);
                }
            }
        });
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                if (mContentView != null || getChildCount() < 1)
                {
                    return;
                }
                if (getChildCount() > 1)
                {
                    try
                    {
                        throw new InstantiationException("FrameTopLayout只能包含一个元素");
                    }
                    catch (InstantiationException e)
                    {
                        e.printStackTrace();
                    }
                }
                View childAt = getChildAt(0);
                if (childAt != null && childAt instanceof IViewListener)
                {
                    IViewListener viewListener = (IViewListener) childAt;
                    mContentView = viewListener.getContentView();
                }
                if (mContentView != null)
                {
                    mContentView.setOnTouchListener(FrameTopLayout.this);
                    mTopView.setVisibility(GONE);
                    addView(mTopView);
                }
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /**
     * 初始化配置文件中读取的数据
     *
     * @param attrs
     * @param defStyle
     */
    private void initStyleableData(AttributeSet attrs, int defStyle)
    {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FrameTopLayout, 0, 0);
        if (typedArray == null)
        {
            mBtnDrawable = getResources().getDrawable(R.drawable.uin_ic_backtotop);
            return;
        }
        mRightMargin = typedArray.getDimensionPixelSize(R.styleable.FrameTopLayout_right_margin, 0);
        mBottomMargin = typedArray.getDimensionPixelSize(R.styleable.FrameTopLayout_bottom_margin, 0);
        mBtnDrawable = typedArray.getDrawable(R.styleable.FrameTopLayout_drawable);
        typedArray.recycle();
    }

    public void setBtnClickListener(TopClickListener listener)
    {
        mClickListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (!enable)
        {
            return false;
        }
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mFirstClickPosition.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                float hor = event.getX() - mFirstClickPosition.x;
                float ver = event.getY() - mFirstClickPosition.y;
                float absHor = Math.abs(hor);
                float absVer = Math.abs(ver);
                if (absHor < 5 && absVer < mSlideSpace)
                {
                    break;
                }
                if (absHor > absVer)
                {
                    hide();
                }
                else if (ver > 0)
                {
                    show();
                    mFirstClickPosition.set(event.getX(), event.getY());
                }
                else
                {
                    hide();
                }
                break;
        }
        return false;
    }

    public boolean isEnable()
    {
        return enable;
    }

    public void setEnable(boolean enable)
    {
        this.enable = enable;
    }

    /**
     * 按钮的显示
     */
    public void show()
    {
        if (mTopView.getVisibility() == VISIBLE)
        {
            return;
        }
        mTopView.setVisibility(VISIBLE);
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(mAnimationDuration);
        mTopView.startAnimation(animation);
    }

    /**
     * 按钮的隐藏
     */
    public void hide()
    {
        if (mTopView.getVisibility() == GONE)
        {
            return;
        }
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(mAnimationDuration);
        mTopView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                mTopView.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    /**
     * 左右下角按钮的点击事件
     */
    public static interface TopClickListener
    {
        public void onClick(View v);
    }
}
