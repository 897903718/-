package com.chanlytech.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自动计时的按钮（用于发送验证码等等）
 * Created by Carlton on 2014/12/3.
 */
public class AutoTimeButton extends CheckBox
{
    private static final String                   TAG                 = "AutoTimeButton";
    private              OnTimeRunningOutListener mTimeRunOutListener = new SampleTimeRunningOutListener();
    private              int                      total               = 0;
    private              int                      space               = 0;
    private              CharSequence             mText               = "";

    public AutoTimeButton(Context context)
    {
        super(context);
        init(context);
    }

    public AutoTimeButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public AutoTimeButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoTimeButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getSpace()
    {
        return space;
    }

    public void setSpace(int space)
    {
        this.space = space;
    }

    public void setTimeRunOutListener(OnTimeRunningOutListener listener)
    {
        mTimeRunOutListener = listener;
    }

    private void init(Context context)
    {
        setButtonDrawable(android.R.color.transparent);
        setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked && mTimeRunOutListener.onRunState(AutoTimeButton.this))
                {
                    start();
                }
                else
                {
                    setChecked(false);
                }
            }
        });
        mText = getText();
    }

    private int run = 0;
    private Timer timer;

    /**
     * 开始
     */
    private void start()
    {
        setEnabled(false);
        setChecked(true);
        run = 0;
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                postRun();
            }
        }, 0, space);
    }

    /**
     * 停止计时
     */
    public void stop()
    {
        if (timer != null)
        {
            timer.cancel();
        }
        setText(mText);
        setEnabled(true);
        setChecked(false);
    }

    private void postRun()
    {
        post(new Runnable()
        {
            @Override
            public void run()
            {
                run += space;
                if (run >= total)
                {
                    stop();
                    mTimeRunOutListener.onTimeOver(AutoTimeButton.this);
                }
                else
                {
                    mTimeRunOutListener.onRunning(AutoTimeButton.this, run);
                }
            }
        });
    }

    /**
     * 随着时间变化对控件的时间监听
     */
    public interface OnTimeRunningOutListener
    {
        /**
         * 进入被计时状态
         *
         * @param autoTimeButton
         */
        public boolean onRunState(AutoTimeButton autoTimeButton);

        /**
         * 正在流失的时间监听
         *
         * @param autoTimeButton
         * @param time
         */
        public void onRunning(AutoTimeButton autoTimeButton, int time);

        /**
         * 时间完成
         *
         * @param autoTimeButton
         */
        public void onTimeOver(AutoTimeButton autoTimeButton);
    }

    public static class SampleTimeRunningOutListener implements OnTimeRunningOutListener
    {

        @Override
        public boolean onRunState(AutoTimeButton autoTimeButton)
        {
            return true;
        }

        @Override
        public void onRunning(AutoTimeButton autoTimeButton, int time)
        {

        }

        @Override
        public void onTimeOver(AutoTimeButton autoTimeButton)
        {

        }
    }
}
