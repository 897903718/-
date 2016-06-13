package com.chanlytech.ui.widget.pullrefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chanlytech.ui.R;
import com.chanlytech.ui.widget.pullrefresh.PullToRefreshBase;

/**
 * Created by Lyy on 2015/5/25.
 * 带猫头鹰的下拉动画
 */
public class BridLoadingLayout extends LoadingLayout {
    private AnimationDrawable mFrameAnim;

    public BridLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mFrameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_brid_refresh);
        mHeaderImage.setScaleType(ImageView.ScaleType.MATRIX);
//        mHeaderImage.setImageMatrix(mHeaderImageMatrix);
        mHeaderImage.setBackgroundDrawable(mFrameAnim);
//        mHeaderTextViewLL.setVisibility(GONE);
    }

    //设置默认图片
    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.back_transparent;
    }

    /**
     * 初始化一些操作，比如获取图片的中心点
     *
     * @param imageDrawable 设置的加载图片
     */
    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    /**
     * 正在下拉
     *
     * @param scaleOfLayout 下拉的偏移量
     */
    @Override
    protected void onPullImpl(float scaleOfLayout) {

    }

    /**
     * 开始刷新
     */
    @Override
    protected void pullToRefreshImpl() {
//        mFrameAnim.start();
    }

    /**
     * 正在刷新
     */
    @Override
    protected void refreshingImpl() {
        mFrameAnim.start();
    }

    /**
     * 释放刷新（停止刷新）
     */
    @Override
    protected void releaseToRefreshImpl() {
        mFrameAnim.stop();
    }

    /**
     * 重新设置
     */
    @Override
    protected void resetImpl() {
        if (mFrameAnim != null)
            mFrameAnim.stop();
    }
}
