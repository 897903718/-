package com.chanlytech.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.chanlytech.ui.R;

import java.util.Collection;

/**
 * 数据加载视图
 */
public class UinLoadDataView extends FrameLayout
{
    /**
     * 正在加载
     */
    public static final int LOADING      = 0;
    /**
     * 加载失败
     */
    public static final int LOADING_FAIL = 1;
    /**
     * 无效
     */
    public static final int INVALID      = 2;
    /**
     * 有限
     */
    public static final int AVAILABLE    = 3;
    /**
     * Context
     */
    private Context         mContext;
    /**
     * 正在加载的布局
     */
    private LinearLayout    mLoadingLayout;
    /**
     * 加载失败的布局
     */
    private LinearLayout    mLoadFailLayout;
    /**
     * 重新加载按钮
     */
    private Button          mReLoad;
    /**
     * 重新加载点击事件监听
     */
    private OnClickListener mListener;
    /**
     * 内容视图
     */
    private View            mContentView;
    /**
     * 根视图
     */
    private View            mRootView;
    /**
     * 是否显示
     */
    private boolean         mIsShow;

    public UinLoadDataView(Context context)
    {
        super(context);
        init(context);
    }

    public UinLoadDataView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public UinLoadDataView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context)
    {
        mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.uin_layout_load_data, null);
        mLoadingLayout = (LinearLayout) mRootView.findViewById(R.id.uin_loading);
        mLoadFailLayout = (LinearLayout) mRootView.findViewById(R.id.uin_load_fail);
        mReLoad = (Button) mRootView.findViewById(R.id.uin_reload);

        initRotateImg();

        addView(mRootView);
    }

    /**
     * 初始化旋转的图片
     */
    private void initRotateImg()
    {
        ImageView imgView = (ImageView) mRootView.findViewById(R.id.load_img);
        imgView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.uin_rotate_center));
    }

    /**
     * 设置重新加载的点击事件
     *
     * @param listener
     */
    public void setReLoadListener(OnClickListener listener)
    {
        mListener = listener;
        if (mListener != null)
        {
            mReLoad.setOnClickListener(mListener);
        }
    }

    /**
     * 设置其他的视图
     *
     * @param view
     */
    public void setContentView(View view)
    {
        mContentView = view;
    }

    public View getContentView()
    {
        return mContentView;
    }

    /**
     * 设置状态
     */
    public void setState(int state)
    {
        switch (state)
        {
            case LOADING:
                loading();
                break;
            case LOADING_FAIL:
                loadingFail();
                break;
            case INVALID:
                invalid();
                break;
            case AVAILABLE:

                break;
            default:
                break;
        }
    }

    /**
     * 完成，如果数据是空，那么说明加载失败，否则加载完成
     *
     * @param list
     */
    public void onFinish(Collection list)
    {
        if (list == null)
        {
            onLoadFail();
        }
        else
        {
            onComplete();
        }
    }

    /**
     * 加载完成
     */
    public void onComplete()
    {
        setState(INVALID);
    }

    /**
     * 正在加载
     */
    public void onLoading()
    {
        setState(LOADING);
    }

    /**
     * 加载失败
     */
    public void onLoadFail()
    {
        setState(LOADING_FAIL);
    }

    /**
     * 加载中
     */
    protected void loading()
    {
        initRotateImg();
        setVisibility(VISIBLE);
        mContentView.setVisibility(GONE);
        mLoadingLayout.setVisibility(VISIBLE);
        mLoadFailLayout.setVisibility(GONE);
    }

    /**
     * 加载失败
     */
    protected void loadingFail()
    {
        setVisibility(VISIBLE);
        mContentView.setVisibility(GONE);
        mLoadingLayout.setVisibility(GONE);
        mLoadFailLayout.setVisibility(VISIBLE);
    }

    /**
     * 无效
     */
    protected void invalid()
    {
        setVisibility(GONE);
        mContentView.setVisibility(VISIBLE);
    }

    /**
     * 是否显示
     *
     * @return
     */
    public boolean isShow()
    {
        return getVisibility() == VISIBLE;
    }
}
