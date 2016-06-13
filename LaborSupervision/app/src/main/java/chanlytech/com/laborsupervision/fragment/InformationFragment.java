package chanlytech.com.laborsupervision.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.CodeSelectionActivity;
import chanlytech.com.laborsupervision.activity.SearchActivity;
import chanlytech.com.laborsupervision.adapter.LegalProvisionsAdapter;
import chanlytech.com.laborsupervision.adapter.NewsAdapter;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.BookEntity;
import chanlytech.com.laborsupervision.entiy.ColumnEntity;
import chanlytech.com.laborsupervision.entiy.DataEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.module.InformationModule;
import chanlytech.com.laborsupervision.widget.XListView;

/**
 * Created by Lyy on 2015/8/31.
 */
public class InformationFragment extends BaseFragment<InformationModule> implements XListView.IXListViewListener, View.OnClickListener {
    @InjectView(R.id.new_list)
    XListView mNewsListView;
    //    @InjectView(R.id.tv_search)
//    TextView mTextView_select;
//    @InjectView(R.id.ll_search)
//    LinearLayout mLayout_search;
//    @InjectView(R.id.ll_screen)
//    LinearLayout mLayout_screen;
//    @InjectView(R.id.rl_fa)
//    RelativeLayout mLayout_fa;
//    @InjectView(R.id.tv_fa)
//    TextView mTextView_fa;
//    @InjectView(R.id.rl_zhang)
//    RelativeLayout mLayout_zhang;
//    @InjectView(R.id.tv_zhang)
//    TextView mTextView_zhang;
    private NewsAdapter newsAdapter;
    private List<NewsEntity> newsEntities = new ArrayList<>();
    private int pagernumber = 0;
    private ColumnEntity columnEntity;
    private String savedata, bookdata = null;

    public InformationFragment() {
    }

    public static InformationFragment newInstance(ColumnEntity ColumnEntity) {
        InformationFragment informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ColumnEntity", ColumnEntity);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        columnEntity = getArguments().getParcelable("ColumnEntity");
        getModule().getPosterAndNews(columnEntity.getId(), pagernumber, "", "");//第一次加载
        initView();
        initLinster();


    }

    private void initView() {
        newsAdapter=new NewsAdapter(getActivity(),newsEntities);
        mNewsListView.setAdapter(newsAdapter);
        mNewsListView.setXListViewListener(this);
        mNewsListView.setPullLoadEnable(true);
    }

    private void initLinster() {
//        mTextView_select.setOnClickListener(this);
//        mLayout_fa.setOnClickListener(this);
//        mLayout_zhang.setOnClickListener(this);
    }

    @Override
    public InformationModule initModule() {
        return new InformationModule(getActivity());
    }

    public void onResume() {
        super.onResume();

//        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("MainScreen");
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.GET_POSTER_NEWS:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    savedata = baseEntity.getData();
                    DataEntity dataEntity = JSON.parseObject(baseEntity.getData(), DataEntity.class);
                    if (dataEntity.getNews().size() > 0) {
                        if (pagernumber == 0) {
                            newsEntities.clear();
                        }
                        newsEntities.addAll(dataEntity.getNews());
                        mNewsListView.stopLoadMore();
                        mNewsListView.stopRefresh();
                        if (newsEntities.size() <= 9) {
                            mNewsListView.setPullLoadEnable(false);
                        } else {
                            mNewsListView.setPullLoadEnable(true);
                        }
                        newsAdapter.notifyDataSetChanged();
                    } else {
                        mNewsListView.stopLoadMore();
                        mNewsListView.stopRefresh();
                        mNewsListView.setPullLoadEnable(false);
                    }

                } else {
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(getActivity(), data.toString());
                break;

        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(getActivity())) {
                    pagernumber = 0;
                    mNewsListView.setPullLoadEnable(true);
                    newsEntities.clear();
                    getModule().getPosterAndNews(columnEntity.getId(), pagernumber, "", "");

                } else {
                    T.showLong(getActivity(), "当前网络不可用");
                    newsAdapter.notifyDataSetChanged();
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(getActivity())) {
                    pagernumber++;
                    getModule().getPosterAndNews(columnEntity.getId(), pagernumber, "", "");
                } else {
                    T.showLong(getActivity(), "当前网络不可用");
                    mNewsListView.stopLoadMore();
                    mNewsListView.stopRefresh();
                }
            }
        }, 2000);
    }


    @Override
    public void onClick(View v) {

    }


}
