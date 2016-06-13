package chanlytech.com.laborsupervision.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.BaseFragmentStatePagerAdapter;
import chanlytech.com.laborsupervision.adapter.MyPageAdapter;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.ColumnEntity;
import chanlytech.com.laborsupervision.module.NewsFragmentModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.PublicTool;
import chanlytech.com.laborsupervision.widget.MyViewPager;
import chanlytech.com.laborsupervision.widget.PagerSlidingTabStrip;

/**
 * Created by Lyy on 2015/9/11.
 * 资讯界面
 */
public class NewsFragment extends BaseFragment<NewsFragmentModule> {
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabStrip;
    @InjectView(R.id.view_pager)
    MyViewPager mPager;
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title;
    private BaseFragmentStatePagerAdapter mFragmentStatePagerAdapter;
    private MyPageAdapter myPageAdapter;
    private int lenth=2;
    private List<Fragment> fragments = new ArrayList<>();
    public NewsFragment(){

    }
    public static NewsFragment newInstance() {
        return new NewsFragment();
    }
    @Override
    public int setContentView() {
        return R.layout.fragment_news;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        back.setVisibility(View.GONE);
        title.setText("资讯");
        getModule().getCategoryList();



    }

    @Override
    public NewsFragmentModule initModule() {
        return new NewsFragmentModule(getActivity());
    }

    /**
     * 设置viewpager数据
     */
    private void setFragmentData(List<ColumnEntity> categoryList) {
        List<Fragment> fragments = new ArrayList<>();
        if (categoryList != null && categoryList.size() > 0) {
            for (ColumnEntity entity : categoryList) {
//                if(entity.getId().equals("4")){
//                    fragments.add(LegalProvisionsFragment.newInstance(entity));
//                }else {
//                    fragments.add(InformationFragment.newInstance(entity));
//                }
                fragments.add(InformationFragment.newInstance(entity));
            }
        } else {// 如果没有数据,则设置默认值
            List<ColumnEntity> defaultTabData = defaultTabData();
            for (ColumnEntity entity : defaultTabData) {
                fragments.add(InformationFragment.newInstance(entity));
            }
        }
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
//        mFragmentStatePagerAdapter = new BaseFragmentStatePagerAdapter(supportFragmentManager, categoryList, fragments);
        myPageAdapter=new MyPageAdapter(fragments,categoryList,supportFragmentManager);
//        mAdapter = new TabPagerAdapter(getChildFragmentManager(), fragments,categoryList);
        tabStrip.setUnderlineHeight(4);
//        tabStrip.setTextSize(20);//
        if (PublicTool.getDeviceWidth() <= 480) {
            tabStrip.setTextSize(20);//
        } else if (PublicTool.getDeviceWidth() > 480 && PublicTool.getDeviceWidth() < 1080) {
            tabStrip.setTextSize(30);
        } else if ((PublicTool.getDeviceWidth() >= 1080)) {
            tabStrip.setTextSize(40);
        }
        tabStrip.setIndicatorHeight(4);
        mPager.setPagerEnabled(true);
        mPager.setAdapter(myPageAdapter);
        mPager.setOffscreenPageLimit(fragments.size());
        tabStrip.setViewPager(mPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constants.state==true){
            if(lenth>0){
                mPager.setCurrentItem(lenth-1);
            }else {
                mPager.setCurrentItem(0);
            }
            Constants.state=false;
        }

    }

    /**
     * 默认的Tab数据
     *
     * @return
     */
    private List<ColumnEntity> defaultTabData() {
        ArrayList<ColumnEntity> list = new ArrayList<>();
        list.add(new ColumnEntity("130", "法律时评"));
        list.add(new ColumnEntity("131", "案例点评"));
        list.add(new ColumnEntity("132", "法条分析"));
        return list;
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.GET_Column_LIST:
                if(data.toString().length()>0){
                    BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                    if (baseEntity.getStatus() == 1) {
                        List<ColumnEntity> columnEntities = JSON.parseArray(baseEntity.getData(), ColumnEntity.class);
                       if(columnEntities.size()>0){
                           lenth=columnEntities.size();
                           setFragmentData(columnEntities);
                       }else {
                           //设置空数据
//                           defaultTabData();
                           setFragmentData( defaultTabData());
                       }

                    }
                }else {
                    //设置空数据
                    List<ColumnEntity> columnEntities=new ArrayList<>();
                    setFragmentData(columnEntities);
                }

                break;
        }
    }


}
