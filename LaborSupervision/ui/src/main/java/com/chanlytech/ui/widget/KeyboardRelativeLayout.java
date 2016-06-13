package com.chanlytech.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.chanlytech.ui.widget.inf.InputListener;

/**
 * 键盘监听
 */
public class KeyboardRelativeLayout extends RelativeLayout
{
    private static final String TAG = "KeyboardRelativeLayout";
    private boolean       mHasInit;
    private boolean       mHasKeybord;
    private int           mHeight;
    private InputListener mListener;


    public KeyboardRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public KeyboardRelativeLayout(Context context)
    {
        super(context);
    }

    /**
     * set keyboard state listener
     */
    public void setOnInputListener(InputListener listener)
    {
        mListener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit)
        {
            mHasInit = true;
            mHeight = b;
            if (mListener != null)
            {
                mListener.onKeyboardStateChange(InputListener.KEYBOARD_STATE_INIT);
            }
        }
        else
        {
            mHeight = mHeight < b ? b : mHeight;
        }
        if (mHasInit && mHeight > b)
        {
            mHasKeybord = true;
            if (mListener != null)
            {
                mListener.onKeyboardStateChange(InputListener.KEYBOARD_STATE_SHOW);
            }
        }
        if (mHasInit && mHasKeybord && mHeight == b)
        {
            mHasKeybord = false;
            if (mListener != null)
            {
                mListener.onKeyboardStateChange(InputListener.KEYBOARD_STATE_HIDE);
            }
        }
    }
}
