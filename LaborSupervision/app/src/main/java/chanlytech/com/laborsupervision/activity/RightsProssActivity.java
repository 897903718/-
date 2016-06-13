package chanlytech.com.laborsupervision.activity;


import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.ProgressAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.RightsEntity;
import chanlytech.com.laborsupervision.module.RightsProssModule;
import chanlytech.com.laborsupervision.widget.XListView;

public class RightsProssActivity extends BaseActivity<RightsProssModule> implements XListView.IXListViewListener {
    /**
     * 维权进度
     */
    @InjectView(R.id.list)
    XListView mXListView;
    @InjectView(R.id.title)
    TextView title;
    private int page = 0;
    private List<RightsEntity> mRightsEntities = new ArrayList<>();
    private ProgressAdapter mProgressAdapter;

    @Override
    public int setContentView() {
        return R.layout.activity_rights_pross;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        getModule().getProssList(page);
    }

    @Override
    public RightsProssModule initModule() {
        return new RightsProssModule(this);
    }

    private void initView() {
        title.setText("维权进度");
        mProgressAdapter = new ProgressAdapter(this, mRightsEntities);
        mXListView.setAdapter(mProgressAdapter);
        mXListView.setXListViewListener(this);
        mXListView.setPullLoadEnable(true);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(RightsProssActivity.this)) {
                    page = 0;
                    mXListView.setPullLoadEnable(true);
                    mRightsEntities.clear();
                    getModule().getProssList(page);

                } else {
                    T.showLong(RightsProssActivity.this, "当前网络不可用");
                    mProgressAdapter.notifyDataSetChanged();
                    mXListView.stopLoadMore();
                    mXListView.stopRefresh();
                }
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(RightsProssActivity.this)) {
                    page++;
                    getModule().getProssList(page);
                } else {
                    T.showLong(RightsProssActivity.this, "当前网络不可用");
                    mXListView.stopLoadMore();
                    mXListView.stopRefresh();
                }
            }
        }, 2000);
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.PRO_LIST:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    if(baseEntity.getData().length()>0){
                        List<RightsEntity> rightsEntities = JSON.parseArray(baseEntity.getData(), RightsEntity.class);
                        if (rightsEntities.size() > 0) {
                            if (page ==0) {
                                mRightsEntities.clear();
                            }
                            mRightsEntities.addAll(rightsEntities);
                            mXListView.stopLoadMore();
                            mXListView.stopRefresh();
                            if (rightsEntities.size() <= 1) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }
                            mProgressAdapter.notifyDataSetChanged();
                        } else {
                            mXListView.stopLoadMore();
                            mXListView.stopRefresh();
                            mXListView.setPullLoadEnable(false);
                        }
                    }else {
                        if(page==0){
                            mXListView.setPullLoadEnable(false);
                            T.showLong(this, "您暂时没有维权数据");
                        }
                        mXListView.stopLoadMore();
                        mXListView.stopRefresh();
                    }



                } else {
                    mXListView.stopLoadMore();
                    mXListView.stopRefresh();
                }
                break;
        }
    }
}
