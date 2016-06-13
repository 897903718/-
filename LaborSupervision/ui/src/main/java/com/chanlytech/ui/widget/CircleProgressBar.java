package com.chanlytech.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.chanlytech.ui.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 圆形进度条
 * Created by Higgses on 14-3-28.
 */
public class CircleProgressBar extends View
{
    private static final String TAG                    = "CircleProgressBar";
    /**
     * 默认进度条宽度
     */
    private final        int    DEFAULT_PROGRESS_WIDTH = 30;
    /**
     * 进度条的宽度
     */
    private              float  mProgressWidth         = DEFAULT_PROGRESS_WIDTH;
    /**
     * 中间的角度
     */
    private              float  mCenterAngle           = 0;
    /**
     * 间隙角度
     */
    private              float  mSpaceAngle            = 0;
    /**
     * 开始角度
     */
    private              float  mStartAngle            = 0;
    /**
     * 结束的角度
     */
    private              float  mEndAngle              = 0;
    /**
     * 进度条的角度(这个角度是从mStartAngle的角度开始为0度的)
     */
    private              float  mProgressAngle         = 0;
    /**
     * 进度条的值
     */
    private              float  mProgressValue         = 0;
    /**
     * 进度条的结束角度
     */
    private              float  mProgressEndAngle      = 0;
    /**
     * 进度条颜色
     */
    private              int    mProgressColor         = 0;
    /**
     * 进度条背景颜色
     */
    private              int    mProgressBgColor       = 0;
    /**
     * 圆弧的内容部分区域
     */
    private RectF            mContentRect;
    /**
     * 动画的初始速度
     */
    private float            mInitSpeed;
    /**
     * 变速运动的时间片段
     */
    private double           mTime;
    /**
     * 进度监听
     */
    private ProgressListener mProgressListener;
    /**
     * 间距
     */
    private int mMargin = 2;
    /**
     * 总长度（距离）
     */
    private float mDuration;

    public CircleProgressBar(Context context)
    {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs)
    {
        mProgressListener = new SimpleProgressListener();
        TypedArray appearance = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        if (appearance != null)
        {
            int n = appearance.getIndexCount();
            for (int i = 0; i < n; ++i)
            {
                int attr = appearance.getIndex(i);
                if (attr == R.styleable.CircleProgressBar_center_angle)
                {
                    mCenterAngle = appearance.getFloat(attr, 0.0f);
                }
                else if (attr == R.styleable.CircleProgressBar_space_angle)
                {
                    mSpaceAngle = appearance.getFloat(attr, 0.0f);
                }
                else if (attr == R.styleable.CircleProgressBar_background_color)
                {
                    mProgressBgColor = appearance.getColor(attr, 0);
                }
                else if (attr == R.styleable.CircleProgressBar_progress_color)
                {
                    mProgressColor = appearance.getColor(attr, 0);
                }
                else if (attr == R.styleable.CircleProgressBar_progress_value)
                {
                    mProgressValue = appearance.getFloat(attr, 0.0f);
                }
                else if (attr == R.styleable.CircleProgressBar_progress_width)
                {
                    mProgressWidth = appearance.getDimensionPixelSize(attr, 0);
                }
                else if (attr == R.styleable.CircleProgressBar_progress_duration)
                {
                    mDuration = appearance.getFloat(attr, 0.0f);
                }
            }
            initValue();
        }
        mContentRect = new RectF();
        postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startAnimation();
            }
        }, 300);
    }

    /**
     * 初始化值
     */
    private void initValue()
    {
        mProgressAngle = 0;
        setProgressValue(mProgressValue);
        // 保证开始的角度，小于等于最后角度
        mStartAngle = mCenterAngle + mSpaceAngle * 0.5f;
        mEndAngle = mCenterAngle - mSpaceAngle * 0.5f;
        setEndAngle(mEndAngle);
        setProgressEndAngle(getAngle(mProgressValue));
    }

    /**
     * 返回角度值
     *
     * @param progressValue
     *
     * @return
     */
    public float getAngle(float progressValue)
    {
        float allAngle = 360 - mSpaceAngle;
        float angle = (float) Math.ceil(progressValue * allAngle / mDuration);
        //        UinLog.d(TAG, "百分比转角度:值_" + progressValue + "   角度:" + angle);
        return angle;
    }

    /**
     * 返回角度对应的值
     *
     * @param angle
     *
     * @return
     */
    public int getValueFormAngle(float angle)
    {
        float allAngle = 360 - mSpaceAngle;
        int progressValue = (int) Math.ceil(mDuration * angle / allAngle);
        if (progressValue >= mProgressValue)
        {
            progressValue = (int) mProgressValue;
        }
        return progressValue;
    }

    /**
     * 初始化动画
     */
    public void startAnimation()
    {
        //        setProgressEndAngle(getAngle(mProgressValue));
        mInitSpeed = getInitSpeed(mProgressValue);
        mTime = 0.01f;
        // 加速度
        final float a = 20.0f;
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                mTime = 0.01f;
                float v = (float) (mInitSpeed - a * mTime);
                float spaceS = (float) (mInitSpeed * mTime - a * mTime * mTime * 0.5f);
                mInitSpeed = v;
                float s = (float) Math.ceil(mProgressAngle + spaceS * 0.5f);
                if (s > mProgressEndAngle || s < 0)
                {
                    cancel();
                    return;
                }
                mProgressAngle = s;
                mHandler.sendEmptyMessage(0);
                postInvalidate();
                mTime *= 0.5f * 0.5f;
            }
        }, 0, 5);
    }

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            mProgressListener.progress(getValueFormAngle(mProgressAngle));
        }
    };

    /**
     * 返回初始速度
     *
     * @param progressValue
     *
     * @return
     */
    private float getInitSpeed(float progressValue)
    {
        //        float v = 445 * progressValue / mDuration;
        float v = 445;
        //        UinLog.d(TAG, "初速度:" + v);
        return v;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 初始化园的内容部分区域
        int canvasWidth = (int) (mProgressWidth * 0.5) + mMargin;
        int right = getMeasuredWidth() - canvasWidth;
        int bottom = getMeasuredHeight() - canvasWidth;
        mContentRect.set(canvasWidth, canvasWidth, right, bottom);
        // 开始绘制
        drawBgArc(canvas);
        drawProgressArc(canvas);
    }

    /**
     * 绘制背景的圆弧
     *
     * @param canvas
     */
    protected void drawBgArc(Canvas canvas)
    {
        drawArc(canvas, mStartAngle, mEndAngle, mProgressWidth, mProgressBgColor);
        drawBgCorner(canvas);
    }

    /**
     * 绘制背景边角（默认是圆点）
     */
    protected void drawBgCorner(Canvas canvas)
    {
        drawCorner(canvas, mStartAngle, mStartAngle - mSpaceAngle, mProgressWidth, mProgressBgColor);
    }

    /**
     * 绘制进度条的圆弧
     *
     * @param canvas
     */
    protected void drawProgressArc(Canvas canvas)
    {
        drawArc(canvas, mStartAngle, mProgressAngle, mProgressWidth, mProgressColor);
        drawProgressCorner(canvas);
    }

    /**
     * 绘制进度条的边角（默认是圆点）
     *
     * @param canvas
     */
    protected void drawProgressCorner(Canvas canvas)
    {
        drawCorner(canvas, mStartAngle, mStartAngle + mProgressAngle, mProgressWidth, mProgressColor);
    }

    /**
     * 绘制圆弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas, float startAngle, float endAngle, float width, int color)
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        paint.setColor(color);
        canvas.drawArc(mContentRect, startAngle, endAngle, false, paint);
    }

    /**
     * 绘制边角
     *
     * @param canvas
     * @param width
     * @param color
     */
    private void drawCorner(Canvas canvas, float startAngle, float endAngle, float width, int color)
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);
        paint.setColor(color);

        float halfProgressWidth = mProgressWidth * 0.5f;
        float r = getMeasuredHeight() * 0.5f - halfProgressWidth - mMargin;
        float x0 = getMeasuredHeight() * 0.5f;
        float y0 = getMeasuredHeight() * 0.5f;

        PointF bgStart = getCirclePosition(x0, y0, startAngle, r);
        canvas.drawCircle(bgStart.x, bgStart.y, halfProgressWidth, paint);
        PointF bgEnd = getCirclePosition(x0, y0, endAngle, r);
        canvas.drawCircle(bgEnd.x, bgEnd.y, halfProgressWidth, paint);
    }

    /**
     * 根据原点，半径，角度，找出圆上任意一点的位置
     *
     * @param x0
     *         原点x
     * @param y0
     *         原点y
     * @param angle
     *         角度（正东是0度）
     * @param r
     *         半径
     *
     * @return
     */
    protected PointF getCirclePosition(float x0, float y0, float angle, float r)
    {
        float x2 = (float) (x0 + r * Math.cos(angle * Math.PI / 180));
        float y2 = (float) (y0 + r * Math.sin(angle * Math.PI / 180));
        PointF pointF = new PointF(x2, y2);
        return pointF;
    }

    /**
     * 比onDraw先执行
     * <p/>
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
     * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p/>
     * 　　它常用的三个函数：
     * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
     * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
     * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        }
        else
        {
            width = widthSize + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        }
        else
        {
            height = heightSize + getPaddingTop() + getPaddingBottom();
        }
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
    }

    /**
     * 设置进度条监听
     */
    public void setProgressListener(ProgressListener listener)
    {
        mProgressListener = listener;
    }

    /**
     * 进度条事件
     */
    public static interface ProgressListener
    {
        public void progress(int value);
    }

    public static class SimpleProgressListener implements ProgressListener
    {
        @Override
        public void progress(int value)
        {
        }
    }

    /**
     * 设置进度条的值
     *
     * @param value
     */
    public void setProgressValue(float value)
    {
        // 进度条的值如果大于了最大值，则设置进度条的值是最大值
        mProgressValue = value > mDuration ? mDuration : value;
    }

    /**
     * 设置结束的时候的角度
     *
     * @param angle
     */
    public void setEndAngle(float angle)
    {
        mEndAngle = mStartAngle >= angle ? 360 - mSpaceAngle : angle;
    }

    /**
     * 设置进度条的结束时候的角度
     *
     * @param angle
     */
    public void setProgressEndAngle(float angle)
    {
        mProgressEndAngle = angle > mEndAngle ? mEndAngle : angle;
    }

    /**
     * 更新设置进度条的值（设置了进度会更新到最新值,并且无动画）
     *
     * @param value
     */
    public void updateProgressValue(float value)
    {
        setProgressValue(value);
        mProgressEndAngle = getAngle(mProgressValue);
        mProgressAngle = mProgressEndAngle;
        postInvalidate();
    }

    /**
     * 更新进度条的值（设置了进度会更新到最新的值，有动画）
     *
     * @param value
     */
    public void updateProgressValueForAnimation(float value)
    {
        setProgressValue(value);
        setProgressEndAngle(getAngle(mProgressValue));
        startAnimation();
    }
}
