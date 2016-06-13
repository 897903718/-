package com.chanlytech.ui.widget.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 一个有加载更多信息的ListView
 */
public class LoadMoreListView extends ListView implements OnScrollListener
{
    //    private static final String TAG = "XListView";

    private final static int SCROLL_BACK_HEADER = 0;
    private final static int SCROLL_BACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 250;

    // when pull up >= 50px
    private final static int PULL_LOAD_MORE_DELTA = 50;

    // support iOS like pull
    private final static float OFFSET_RADIO = 1.8f;

    private float mLastY = -1;

    // used for scroll back
    private Scroller         mScroller;
    // user's scroll listener
    private OnScrollListener mScrollListener;
    // for mScroller, scroll back from header or footer.
    private int              mScrollBack;

    // the interface to trigger refresh and load more.
    private ILoadMoreListener mListener;

    private LinearLayout  mFooterLayout;
    private UinFooterView mFooterView;
    private boolean mIsFooterReady = false;

    private boolean mEnablePullLoad = true;
    private boolean mEnableAutoLoad = true;
    private boolean mPullLoading    = false;

    // total list items, used to detect is at the bottom of ListView
    private int mTotalItemCount;
    /**
     * 每一页多少条数据
     */
    private int pageCount = 12;

    public LoadMoreListView(Context context)
    {
        super(context);
        initWithContext(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initWithContext(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context)
    {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);
        // init footer view
        mFooterView = new UinFooterView(context);
        mFooterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mFooterLayout.addView(mFooterView, params);
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        addFooterView();
        super.setAdapter(adapter);
    }

    /**
     * 添加FooterView
     */
    private void addFooterView()
    {
        if (!mIsFooterReady)
        {
            mIsFooterReady = true;
//            mFooterLayout.setOnClickListener(new OnClickListener()
//            {
//                @Override
//                public void onStateChanged(View v)
//                {
//                    loadMore();
//                }
//            });
            if (getAdapter() == null || getAdapter().getCount() < pageCount)
            {
                mFooterView.setState(UinFooterView.STATE_NO_DATA);
            }
            addFooterView(mFooterLayout);
            invalidate();
            requestLayout();
        }
    }

    /**
     * 设置加载更多的状态
     *
     * @param state
     */
    public void setLoadMoreState(int state)
    {
        mFooterView.setState(state);
    }

    /**
     * 设置FooterView的显示状态
     *
     * @param state
     */
    public void setFooterViewState(int state)
    {
        mFooterView.setVisibility(state);
    }

    /**
     * Enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable)
    {
        mEnablePullLoad = enable;

        if (!mEnablePullLoad)
        {
            mFooterView.setBottomMargin(0);
            mFooterView.hide();
            mFooterView.setPadding(0, 0, 0, mFooterView.getHeight() * (-1));
            mFooterView.setOnClickListener(null);

        }
        else
        {
            mPullLoading = false;
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.show();
            mFooterView.setState(UinFooterView.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startLoadMore();
                }
            });
        }
    }

    @Override
    public void setEmptyView(View emptyView)
    {
        super.setEmptyView(emptyView);
        ListAdapter adapter = getAdapter();
        final boolean empty = ((adapter == null) || adapter.isEmpty());
        if (empty)
        {
            setFooterViewState(GONE);
        }
    }

    /**
     * Enable or disable auto load more feature when scroll to bottom.
     *
     * @param enable
     */
    public void setAutoLoadEnable(boolean enable)
    {
        mEnableAutoLoad = enable;
    }

    /**
     * Stop load more, reset footer view.
     */
    public void stopLoadMore()
    {
        if (mPullLoading)
        {
            mPullLoading = false;
            mFooterView.setState(UinFooterView.STATE_NORMAL);
        }
    }

    public void stopLoadMoreForEmpty()
    {
        if (mPullLoading)
        {
            mPullLoading = false;
            mFooterView.setState(UinFooterView.STATE_NO_DATA);
        }
    }

    /**
     * 开始加载更多数据
     */
    private void startLoadMore()
    {
        mPullLoading = true;
        mFooterView.setState(UinFooterView.STATE_LOADING);
        loadMore();
    }

    /**
     * Set listener.
     *
     * @param listener
     */
    public void setLoadMoreListener(ILoadMoreListener listener)
    {
        mListener = listener;
    }

    private void invokeOnScrolling()
    {
        if (mScrollListener instanceof ScrollListener)
        {
            ScrollListener listener = (ScrollListener) mScrollListener;
            listener.onScrolling(this);
        }
    }

    private void updateFooterHeight(float delta)
    {
        int height = mFooterView.getBottomMargin() + (int) delta;

        if (mEnablePullLoad && !mPullLoading)
        {
            if (height > PULL_LOAD_MORE_DELTA)
            {
                // height enough to invoke load more.
                mFooterView.setState(UinFooterView.STATE_READY);
            }
            else
            {
                mFooterView.setState(UinFooterView.STATE_NORMAL);
            }
        }

        mFooterView.setBottomMargin(height);

        // scroll to bottom
        // setSelection(mTotalItemCount - 1);
    }

    private void resetFooterHeight()
    {
        int bottomMargin = mFooterView.getBottomMargin();

        if (bottomMargin > 0)
        {
            mScrollBack = SCROLL_BACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    /**
     * 总的数量是否超过了一页的数据
     *
     * @return
     */
    private boolean isMoreThanPageCount()
    {
        return getCount() >= getPageCount();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (mLastY == -1)
        {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();

                if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0) && isMoreThanPageCount())
                {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;

            default:
                // reset
                mLastY = -1;
                if (getLastVisiblePosition() == mTotalItemCount - 1)
                {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA)
                    {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            if (mScrollBack != SCROLL_BACK_HEADER)
            {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }

        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l)
    {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        if (mScrollListener != null)
        {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }

        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
        {
            if (getLastVisiblePosition() == getCount() - 1)
            {
                setFooterViewState(VISIBLE);
            }
            if (mEnableAutoLoad && getLastVisiblePosition() == getCount() - 1 && isMoreThanPageCount())
            {
                startLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null)
        {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    private void loadMore()
    {
        if (mEnablePullLoad && null != mListener)
        {
            mListener.onLoadMore();
        }
    }

    public int getPageCount()
    {
        return pageCount - 2;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount + 2;
    }

    /**
     * You can listen ListView.OnScrollListener or this one. it will invoke
     * onScrolling when header/footer scroll back.
     */
    public interface ScrollListener extends OnScrollListener
    {
        public void onScrolling(View view);
    }
}
