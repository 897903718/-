package chanlytech.com.laborsupervision.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollGridView;
import com.chanlytech.ui.widget.NotScrollListView;
import com.chanlytech.ui.widget.pullrefresh.IDataLoadListener;
import com.chanlytech.ui.widget.pullrefresh.PullToRefreshBase;
import com.chanlytech.ui.widget.pullrefresh.PullToRefreshScrollView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.CaseAnalysisActivity;
import chanlytech.com.laborsupervision.activity.ChoiceCityActivity;
import chanlytech.com.laborsupervision.activity.IWantToJoinActivity;
import chanlytech.com.laborsupervision.activity.LawyersToJoin;
import chanlytech.com.laborsupervision.activity.LegalProvisionsActivity;
import chanlytech.com.laborsupervision.activity.MainActivity;
import chanlytech.com.laborsupervision.activity.RightsActivity;
import chanlytech.com.laborsupervision.adapter.AdAdapter;
import chanlytech.com.laborsupervision.adapter.AddressAdapter;
import chanlytech.com.laborsupervision.adapter.HomeCaseAdapter;
import chanlytech.com.laborsupervision.adapter.HomeServerAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.config.ServerConfig;
import chanlytech.com.laborsupervision.entiy.AdsEntity;
import chanlytech.com.laborsupervision.entiy.BaseDataEntity;
import chanlytech.com.laborsupervision.entiy.CityEntity;
import chanlytech.com.laborsupervision.entiy.NewsEntity;
import chanlytech.com.laborsupervision.entiy.ServersEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.entiy.WebEntity;
import chanlytech.com.laborsupervision.module.HomeFragmentMoudle;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.SharedPreferData;
import chanlytech.com.laborsupervision.web.ServerWebActivity;

/**
 * Created by Lyy on 2015/8/31.
 * 首页
 */
public class HomeFragment extends BaseFragment<HomeFragmentMoudle> implements IDataLoadListener, View.OnClickListener {
    @InjectView(R.id.ad_vp)
    ViewPager mViewPager;
    @InjectView(R.id.ll_add_four)
    LinearLayout mLayout_four;
    @InjectView(R.id.grid)
    NotScrollGridView mGridView;
    @InjectView(R.id.list)
    NotScrollListView mListView;
    @InjectView(R.id.refresh_root)
    PullToRefreshScrollView mPullToRefreshScrollView;
//    @InjectView(R.id.rl_ad)
//    RelativeLayout mRelativeLayout_ad;
    @InjectView(R.id.rl_case)
    RelativeLayout relativeLayout;
    @InjectView(R.id.tv_more)
    TextView tv_more;
    @InjectView(R.id.grid1)
    NotScrollGridView mGridView1;
    @InjectView(R.id.tv_address)
    TextView mTextView_address;
    @InjectView(R.id.tv_catname)
    TextView mTextView_name;
    @InjectView(R.id.logo_ldsb)
    ImageView imageView_logo;
    @InjectView(R.id.iv_loc)
    ImageView iv_loc;
    private int number;
    // ViewPager-item临时视图
    private View view1;
    // ViewPager-item视图集合的保存
    private ArrayList<View> views1 = new ArrayList<View>();
    private List<ImageView> mImageViews = new ArrayList<ImageView>();//装小圆点的集合
    private List<AdsEntity> adsEntities = new ArrayList<>();
    private int currentItem = 1;
    TaggleHandler taggletHandler = new TaggleHandler();
    private List<ServersEntity> mServersEntities = new ArrayList<>();
    private List<ServersEntity> mServersEntities1 = new ArrayList<>();
    private List<NewsEntity> newsEntities;
    private Intent mIntent;
    private AdAdapter adAdapter;
    private boolean state;
    private PopupWindow mPopupWindow, mPopupWindow1;
    private AddressAdapter mCityAdapter;
    private List<CityEntity> cityEntities = new ArrayList<>();
    private ImageView tempImag;
    private CityEntity entity;
    private boolean item_check;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

//       按手机分辨率设置bannder的大小
//        int weight=PublicTool.getDeviceWidth();
//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(weight,weight*2/3);
//        mRelativeLayout_ad.setLayoutParams(layoutParams);

        if (AppManager.isLogin()) {
            String address = AppManager.getUser().getAddress();
            mTextView_address.setText(address);
        } else {
            mTextView_address.setText("高新区");
        }

        if (SharedPreferData.readString(getActivity(), "disclaimer").equals("1")) {

        } else {
            mTextView_address.setText(""); //不设置地区
            disclaimer();
            getModule().getCityList();
        }
        getModule().getHomeListData();
        if (AppManager.isLogin() && SharedPreferData.readString(getActivity(), "CID").length() > 0) {
            getModule().bingCid();
        }
        mViewPager.setFocusable(true);
        mViewPager.setFocusableInTouchMode(true);
        mViewPager.requestFocus();
        taggletHandler.sleep(5000);
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                HomeFragment.this.onRefresh(refreshView);
            }
        });
        initLinster();

    }

    private void initLinster() {
        tv_more.setOnClickListener(this);
        mTextView_address.setOnClickListener(this);
        imageView_logo.setOnClickListener(this);
        iv_loc.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Constants.isFastClick()) {
                    switch (mServersEntities.get(position).getType()){
                        case 1://h5
                            WebEntity webEntity = new WebEntity();
                            webEntity.setTitle(mServersEntities.get(position).getName());
                            webEntity.setUrl(mServersEntities.get(position).getUrl());
                            mIntent = new Intent(getActivity(), ServerWebActivity.class);
                            mIntent.putExtra("webEntity", webEntity);
                            startActivity(mIntent);
                            break;
                        case 8://维权
                            mIntent = new Intent(getActivity(), RightsActivity.class);
                            startActivity(mIntent);
                            break;
                        case 9://发条
                            mIntent = new Intent(getActivity(), LegalProvisionsActivity.class);
//                            mIntent.putExtra("id",mServersEntities.get(position).getAppId());
                            startActivity(mIntent);
                            break;
                    }

                }

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到案列分析
                if (!Constants.isFastClick()) {
//                    mIntent = new Intent(getActivity(), ServerWebActivity.class);
//                    WebEntity webEntity = new WebEntity();
//                    webEntity.setTitle(newsEntities.get(position).getTitle());
//                    webEntity.setUrl(newsEntities.get(position).getUrl());
//                    mIntent = new Intent(getActivity(), ServerWebActivity.class);
//                    mIntent.putExtra("webEntity", webEntity);

                    mIntent=new Intent(getActivity(), CaseAnalysisActivity.class);
                    mIntent.putExtra("title", newsEntities.get(position).getTitle());
                    mIntent.putExtra("url", newsEntities.get(position).getUrl());
                    mIntent.putExtra("id", newsEntities.get(position).getId());
                    startActivity(mIntent);
                }


            }
        });
    }


    @Override
    public HomeFragmentMoudle initModule() {
        return new HomeFragmentMoudle(getActivity());
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.GET_HOME_LIST:
                mPullToRefreshScrollView.onRefreshComplete();
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    BaseDataEntity baseDataEntity = JSON.parseObject(baseEntity.getData(), BaseDataEntity.class);
                    adsEntities = baseDataEntity.getBanner();
                    views1.clear();
                    if (adsEntities.size() > 0) {
                        bingAds(adsEntities);
                    } else {
                        bingAds(defaultTabData());
                    }
                    mServersEntities = baseDataEntity.getApps();
                    mServersEntities1 = baseDataEntity.getApps2();
                    mGridView.setAdapter(new HomeServerAdapter(getActivity(), mServersEntities, 1));
                    mGridView1.setAdapter(new HomeServerAdapter(getActivity(), baseDataEntity.getApps2(), 2));
                    if (baseDataEntity.getNews() == null) {
                        relativeLayout.setVisibility(View.GONE);
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        newsEntities = new ArrayList<>();
                        newsEntities = baseDataEntity.getNews();
                        mListView.setAdapter(new HomeCaseAdapter(getActivity(), newsEntities));
                        mTextView_name.setText(newsEntities.get(0).getCatname());
                    }
                    mPullToRefreshScrollView.onRefreshComplete();
                } else {
                    ErrorToast.showError(getActivity(), baseEntity.getErrorCode());
                    mPullToRefreshScrollView.onRefreshComplete();
                }
                break;
            case ResultCode.EORROR_CODE:
                mPullToRefreshScrollView.onRefreshComplete();
                T.showLong(getActivity(), data.toString());
                break;
            case ResultCode.GET_CITY_LIST:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity1.getStatus() == 1) {
                    cityEntities = JSON.parseArray(baseEntity1.getData(), CityEntity.class);
                    mCityAdapter = new AddressAdapter(getActivity(), cityEntities);
//                    showChoiceCityPop();
                } else {
                    T.showLong(getActivity(), baseEntity1.getErrorMsg());
                }
                break;
        }
    }

    /**
     * 绑定广告数据
     */
    private void bingAds(List<AdsEntity> adsEntities) {
        mImageViews.clear();
        mLayout_four.removeAllViews();
        int length = adsEntities.size() + 2;
        for (int i = 0; i < length; i++) {
            view1 = View.inflate(getActivity(), R.layout.linshiview, null);
            views1.add(view1);
        }
        adAdapter = new AdAdapter(getActivity(), adsEntities, views1);
        mViewPager.setAdapter(adAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        initDot(mLayout_four, views1);
    }

    // 由于前后各多添加了一个view,所以前后的点去掉
    private void initDot(LinearLayout mLayout, ArrayList<View> views1) {
        //		dot = (LinearLayout) this.findViewById(R.id.dot);
        mImageViews = new ArrayList<ImageView>();
        for (int i = 0; i < views1.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 0, 12, 0);
            ImageView m = new ImageView(getActivity());
            if (i == 0) { // 由于前后各添加了一个view,所以前后的点隐藏掉
                m.setVisibility(View.GONE);
            } else if (i == 1) {// 默认索引1也就是a为选中状态
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.mipmap.ic_dot_solid);

            } else if (i > 1 && i < views1.size() - 1) {
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.mipmap.ic_dot_hollow);
            } else { // 由于前后各多添加了一个view,所以前后的点隐藏掉
                m.setVisibility(View.GONE);
            }
            m.setLayoutParams(params);
            mLayout.addView(m);
            mImageViews.add(m);
        }
    }


    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            int pageIndex = arg0;
            // c' a b c a'
            // 向右滑动到a'时,将a'页设置为a的位置，则可以继续向右滑动。同理当向左滑动到 c'时，将c'页设置为c。
            if (arg0 == 0) {
                // 当视图在第一个时，将页面号设置为图片的最后一张。
                pageIndex = adsEntities.size(); // 2'>2
            } else if (arg0 == adsEntities.size() + 1) {
                // 当视图在最后一个是,将页面号设置为图片的第一张。
                pageIndex = 1; // 0'>0
            }
            if (arg0 != pageIndex) {
                // mPager.setCurrentItem(pageIndex, false);
                currentItem = pageIndex;
            } else {
                currentItem = arg0;
            }
            mViewPager.setCurrentItem(currentItem, false);
//            L.i("currentItem=====",currentItem+"");
            for (int i = 0; i < mImageViews.size(); i++) {
                if (i == currentItem) {
                    mImageViews.get(i).setBackgroundResource(R.mipmap.ic_dot_solid);

                } else {
                    mImageViews.get(i).setBackgroundResource(R.mipmap.ic_dot_hollow);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getModule().getHomeListData();
    }

    @Override
    public void onLoadMore(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more://更多跳转案列分析
//                WebEntity webEntity = new WebEntity();
//                webEntity.setTitle("大讲堂");
//                webEntity.setUrl(ServerConfig.BASE_URL+"Legalforum/Legalforum");
//                mIntent = new Intent(getActivity(), ServerWebActivity.class);
//                mIntent.putExtra("webEntity", webEntity);
//                startActivity(mIntent);
                MainActivity.changeColumn();
                break;
            case R.id.tv_address://地区切换
                Intent intent = new Intent(getActivity(), ChoiceCityActivity.class);
                intent.putExtra("flag", "0");
                startActivity(intent);
                break;
            case R.id.logo_ldsb:
                number++;
                if (number == 1) {
                    T.showShort(getActivity(), "求别点，我只是一张图片");
                } else if (number == 2) {
                    T.showShort(getActivity(), "我真的只是一张图片");
                } else if (number == 3) {
                    T.showShort(getActivity(), "我真的只是一张图片啊！");
                } else {
                    T.showShort(getActivity(), "职场护航祝您天天开心！");
                }

                break;
            case R.id.iv_loc:
                Intent intent1 = new Intent(getActivity(), ChoiceCityActivity.class);
                intent1.putExtra("flag", "0");
                startActivity(intent1);
                break;
        }
    }

    class TaggleHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(currentItem);
            }
            taggletHandler.sleep(5000);
            if (currentItem >= views1.size()) {
                currentItem = 1;
            } else {
                currentItem++;
            }
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            this.sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
        if (Constants.state_address) {
            UserEntity user = AppManager.getUser();
            mTextView_address.setText(user.getAddress());
            Constants.state_address = false;
            getModule().getHomeListData();
            if (AppManager.isLogin()) {
                getModule().modifyUserInfo(user.getAddress(), user.getCityId());
            }
        } else {
            if (!state) {
                if (AppManager.isLogin()) {
                    state = true;
                    UserEntity user = AppManager.getUser();
                    mTextView_address.setText(user.getAddress());
                    getModule().getHomeListData();
                }
            }

        }


    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    //为banner配置默认数据
    private List<AdsEntity> defaultTabData() {
        List<AdsEntity> adsEntities = new ArrayList<>();
        adsEntities.add(new AdsEntity("http://182.140.145.103:8088/dianfawang/public/banner/thumb/555c4b5f9aec4.jpg"));
        return adsEntities;
    }


    //首次地区选择
    private void showChoiceCityPop() {
        imageView_logo.post(new Runnable() {
            @Override
            public void run() {
                View view = View.inflate(getActivity(), R.layout.chice_city, null);
                mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setFocusable(true);
                mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item_check) {
                            UserEntity user = AppManager.getUser();
                            mTextView_address.setText(user.getAddress());
                            getModule().getHomeListData();
                            mPopupWindow.dismiss();
                            item_check = false;
                            SharedPreferData.writeStringdata(getActivity(), "disclaimer", "1");
                        } else {
                            T.showLong(getActivity(), "请选择地区");
                        }


                    }
                });
                ListView mListView = (ListView) view.findViewById(R.id.list);
                mListView.setAdapter(mCityAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CityEntity cityEntity = cityEntities.get(position);
                        ImageView imageView = (ImageView) view.findViewById(R.id.iv_check);
                        if (tempImag != null) {
                            tempImag.setVisibility(View.GONE);
                        }
                        imageView.setVisibility(View.VISIBLE);
                        tempImag = imageView;
                        if (entity != null) {
                            entity.setIsCheck(1);
                        }
                        cityEntity.setIsCheck(0);
                        entity = cityEntity;
                        mCityAdapter.notifyDataSetChanged();
                        UserEntity user = AppManager.getUser();
                        user.setCityId(cityEntity.getCityId());
                        user.setAddress(cityEntity.getName());
                        AppManager.saveUser(user);
                        item_check = true;
                    }
                });
            }
        });

    }


//免责声明弹窗
    private void disclaimer(){
        imageView_logo.post(new Runnable() {
            @Override
            public void run() {
                View view = View.inflate(getActivity(), R.layout.disclaimer_view, null);
                mPopupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                mPopupWindow1.setOutsideTouchable(true);
                mPopupWindow1.setFocusable(true);
                mPopupWindow1.showAtLocation(view, Gravity.CENTER, 0, 0);
                Button mbtn = (Button) view.findViewById(R.id.btn_ok);
                mbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupWindow1.dismiss();
                        showChoiceCityPop();
                    }
                });
            }
        });

    }
}
