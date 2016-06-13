/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.chanlytech.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chanlytech.ui.R;


/**
 * 横向的指示器
 * Create by YangQiang on 13-9-8.
 */
public class HorizontalIndicator extends LinearLayout
{
    private final   int DEFAULT_COUNT = 3;
    /**
     * 下一个
     */
    protected final int NEXT          = 1;
    /**
     * 上一个
     */
    protected final int PREVIOUS      = -1;
    /**
     * 无动作
     */
    protected final int NONE          = 0;
    /**
     * 指示器个数
     */
    protected       int mCount        = 0;
    /**
     * 指示器当前选中的位置
     */
    protected       int mCurrentIndex = 0;
    /**
     * 选中后的图片
     */
    protected Drawable mSelectedDrawable;
    /**
     * 普通的图片
     */
    protected Drawable mNormalDrawable;
    /**
     * 指示器图片的左边距
     */
    protected int      paddingLeft;
    /**
     * 指示器图片的上边距
     */
    protected int      paddingTop;
    /**
     * 指示器图片的右边距
     */
    protected int      paddingRight;
    /**
     * 指示器图片的下边距
     */
    protected int      paddingBottom;

    public HorizontalIndicator(Context context)
    {
        super(context);
    }

    public HorizontalIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalIndicator(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalIndicator);
        if (typedArray != null)
        {
            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; ++i)
            {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.HorizontalIndicator_normal_image)
                {
                    mNormalDrawable = typedArray.getDrawable(attr);
                }
                else if (attr == R.styleable.HorizontalIndicator_select_image)
                {
                    mSelectedDrawable = typedArray.getDrawable(attr);
                }
                else if (attr == R.styleable.HorizontalIndicator_padding_left)
                {
                    paddingLeft = (int) typedArray.getDimension(attr, 0);
                }
                else if (attr == R.styleable.HorizontalIndicator_padding_top)
                {
                    paddingTop = (int) typedArray.getDimension(attr, 0);
                }
                else if (attr == R.styleable.HorizontalIndicator_padding_right)
                {
                    paddingRight = (int) typedArray.getDimension(attr, 0);
                }
                else if (attr == R.styleable.HorizontalIndicator_padding_bottom)
                {
                    paddingBottom = (int) typedArray.getDimension(attr, 0);
                }
            }
        }
        setCount(DEFAULT_COUNT);
    }

    /**
     * 设置指示器的数量，必须先调用此方法才能显示指示器
     *
     * @param count
     */
    public void setCount(int count)
    {
        mCount = count;
        initIndicator();
    }

    /**
     * 初始化指示器
     */
    public void initIndicator()
    {
        removeAllViews();
        if (mCount < 1)
        {
            return;
        }
        for (int index = 0; index < mCount; ++index)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(mNormalDrawable);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            imageView.setLayoutParams(params);
            addView(imageView);
        }
        changeIndicator(0, NONE);
    }

    /**
     * 设置指示器的图片
     *
     * @param selectImage
     * @param currentImage
     */
    public void setSelectImage(Drawable selectImage, Drawable currentImage)
    {
        mSelectedDrawable = selectImage;
        mNormalDrawable = currentImage;
        initIndicator();
    }

    /**
     * 指示器向下移动
     */
    public boolean next()
    {
        ++mCurrentIndex;
        if (mCurrentIndex > mCount)
        {
            mCurrentIndex = mCount;
            return false;
        }
        return changeIndicator(mCurrentIndex, NEXT);
    }

    /**
     * 指示器向上移动
     *
     * @return
     */
    public boolean previous()
    {
        --mCurrentIndex;
        if (mCurrentIndex < 0)
        {
            mCurrentIndex = 0;
            return false;
        }
        return changeIndicator(mCurrentIndex, PREVIOUS);
    }

    /**
     * 改变指示器的状态
     *
     * @param index
     * @param action
     */
    public boolean changeIndicator(int index, int action)
    {
        int preIndex = mCurrentIndex + action; // 上一个指示器位置
        ImageView selected = (ImageView) getChildAt(index);
        if (null == selected)
        {
            return false;
        }
        selected.setImageDrawable(mSelectedDrawable);
        if (index == preIndex)
        {// 上一个指示位置和当前指示位置相同
            return true;
        }
        ImageView previous = (ImageView) getChildAt(preIndex);
        if (null == previous)
        {
            return false;
        }
        previous.setImageDrawable(mNormalDrawable);
        return true;
    }

    /**
     * 修改指示器，把Index位置上的指示器图片设置成选中状态，把index位置的上一个或下一个位置的图片设置成普通状态
     *
     * @param index
     *         从0开始
     *
     * @return
     */
    public boolean changeIndicator(int index)
    {
        int preIndex = index - 1;
        if (preIndex < 0)
        {
            preIndex = mCount - 1;
        }
        int nextIndex = index + 1;
        if (nextIndex >= mCount)
        {
            nextIndex = 0;
        }
        // 设置左边的图片为普通图片
        if (preIndex >= 0)
        {
            ImageView pre = (ImageView) getChildAt(preIndex);
            if (null == pre)
            {
                return false;
            }
            pre.setImageDrawable(mNormalDrawable);
        }
        // 设置右边的图片为普通图片
        if (nextIndex < mCount)
        {
            ImageView next = (ImageView) getChildAt(nextIndex);
            if (next == null)
            {
                return false;
            }
            next.setImageDrawable(mNormalDrawable);
        }
        // 设置当前图片为选中
        ImageView select = (ImageView) getChildAt(index);
        if (null == select)
        {
            return false;
        }
        select.setImageDrawable(mSelectedDrawable);
        return true;
    }
}
