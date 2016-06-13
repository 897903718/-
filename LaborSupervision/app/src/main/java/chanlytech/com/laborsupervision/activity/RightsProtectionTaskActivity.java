package chanlytech.com.laborsupervision.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.RightsProtectionTaskAdapter;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.RightsEntity;
import chanlytech.com.laborsupervision.module.RightsProssModule;
import chanlytech.com.laborsupervision.widget.XListView;

public class RightsProtectionTaskActivity extends BaseActivity<RightsProssModule> implements XListView.IXListViewListener {
    /**
     * 维权任务
     */
    @InjectView(R.id.task_list)
    XListView mXListView;
    @InjectView(R.id.title)
    TextView textView;
    private List<RightsEntity>mRightsEntities=new ArrayList<>();
    private int page=0;
    private RightsProtectionTaskAdapter mRightsProtectionTaskAdapter;
    @Override
    public int setContentView() {
        return R.layout.activity_rights_protection_task;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        getModule().getTaskList(page);
    }

    @Override
    public RightsProssModule initModule() {
        return new RightsProssModule(this);
    }

    private void initView() {
        textView.setText("维权任务");
        mRightsProtectionTaskAdapter=new RightsProtectionTaskAdapter(this,mRightsEntities);
        mXListView.setAdapter(mRightsProtectionTaskAdapter);
        mXListView.setXListViewListener(this);
        mXListView.setPullLoadEnable(true);
    }



    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (NetUtils.isConnected(RightsProtectionTaskActivity.this)) {
                    page = 0;
                    mXListView.setPullLoadEnable(true);
                    mRightsEntities.clear();
                    getModule().getTaskList(page);

                } else {
                    T.showLong(RightsProtectionTaskActivity.this, "当前网络不可用");
                    mRightsProtectionTaskAdapter.notifyDataSetChanged();
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
                if (NetUtils.isConnected(RightsProtectionTaskActivity.this)) {
                    page++;
                    getModule().getTaskList(page);
                } else {
                    T.showLong(RightsProtectionTaskActivity.this, "当前网络不可用");
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
            case ResultCode.GET_TASK_LIST:
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    if(baseEntity.getData().length()>0){
                        List<RightsEntity> rightsEntities = JSON.parseArray(baseEntity.getData(), RightsEntity.class);
                        if (rightsEntities.size() > 0) {
                            if (page == 0) {
                                mRightsEntities.clear();
                            }
                            mXListView.stopLoadMore();
                            mXListView.stopRefresh();
                            mRightsEntities.addAll(rightsEntities);
                            if (rightsEntities.size() <= 9) {
                                mXListView.setPullLoadEnable(false);
                            } else {
                                mXListView.setPullLoadEnable(true);
                            }

                            mRightsProtectionTaskAdapter.notifyDataSetChanged();
                        } else {
                            mXListView.stopLoadMore();
                            mXListView.stopRefresh();
                            mXListView.setPullLoadEnable(false);
                        }
                    }else {
                        if(page==0){
                            mXListView.setPullLoadEnable(false);
                            T.showLong(this, "您暂时没有任务");
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
