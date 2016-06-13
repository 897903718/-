package chanlytech.com.laborsupervision.activity;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.arialyy.frame.util.show.L;
import com.arialyy.frame.util.show.T;
import com.igexin.sdk.PushManager;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.adapter.FragmentTabAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.entiy.MessageEntity;
import chanlytech.com.laborsupervision.fragment.HomeFragment;
import chanlytech.com.laborsupervision.fragment.InformationFragment;
import chanlytech.com.laborsupervision.fragment.LegalProvisionsFragment;
import chanlytech.com.laborsupervision.fragment.NewsFragment;
import chanlytech.com.laborsupervision.fragment.RightsFragment;
import chanlytech.com.laborsupervision.fragment.UserInfoFragment;
import chanlytech.com.laborsupervision.getui.PushDemoReceiver;
import chanlytech.com.laborsupervision.sqlite.MessageDao;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.PublicTool;
import chanlytech.com.laborsupervision.util.SharedPreferData;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.bottom_bar)
    RadioGroup mGroup;
    //    @InjectView(R.id.iv_tishi)
    static ImageView mImageView_tishi;
//    @InjectView(R.id.back)
//    ImageView mImageView_back;

    static RadioButton mRadioButton, mRadioButton_home;
    private FragmentManager mFm;
    private List<Fragment> fragments;
    private FragmentTabAdapter mFragmentTabAdapter;
    private long mFirstClickTime = 0;
    private static Context mContext;
    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BaseApplication.getAppLoctin().addActivity(this);
        /** 设置是否对日志信息进行加密, 默认false(不加密). */
//        AnalyticsConfig.enableEncrypt(true);
        mContext=MainActivity.this;
        initView();
        initLinster();
//        listenrKillActivity();
        //绑定百度推送id

    }


    private void initView() {
        mImageView_tishi = (ImageView) findViewById(R.id.iv_tishi);
//        showIco(true,mImageView_tishi);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int deviceWidth = PublicTool.getDeviceWidth();
        int deviceHeight = PublicTool.getDeviceHeight();
        params.setMargins(deviceWidth * 5 / 6, deviceHeight - 170, 0, 0);
        mImageView_tishi.setLayoutParams(params);
        mRadioButton = (RadioButton) findViewById(R.id.rbt_legal);
        mRadioButton_home = (RadioButton) findViewById(R.id.rbt_home);
//        title.setText(getResources().getText(R.string.home));
        mFm = getSupportFragmentManager();
        fragments = new ArrayList<Fragment>();
        fragments.add(NewsFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(RightsFragment.newInstance());
        fragments.add(UserInfoFragment.newInstance());
        mFragmentTabAdapter = new FragmentTabAdapter(this, fragments, R.id.content, mGroup);
        mFragmentTabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                super.OnRgsExtraCheckedChanged(radioGroup, checkedId, index);

//                if (index == 2) {
//                    RightsFragment.mWebView.loadUrl("http://ldsbcd.chanlytech.com//laodongjianguan//index.php//task//task");
//                }
                if (index == 3) {
                    showIco(false, mImageView_tishi);
                }
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                    if (rb.isChecked()) {
                        rb.setTextColor(getResources().getColor(R.color.main_red));
//                        title.setText(rb.getText());
//                        chooseFragment(rb.getTag() + "");
                    } else {
                        rb.setTextColor(getResources().getColor(R.color.main_test_color));
                    }
                }

            }
        });
        if(PushDemoReceiver.payloadData!=null){//判断是否有离线消息
            if(PushDemoReceiver.payloadData.length()>0){
                showIco(true,mImageView_tishi);
            }

        }

    }

    // 切换到咨询栏目
    public static void changeColumn() {
        Constants.state = true;
        mRadioButton.setChecked(true);
        mRadioButton.setTextColor(0xFFDD7075);
        mRadioButton_home.setTextColor(0xFFA5A5A5);
    }

    private void initLinster() {
//        tv_location.setOnClickListener(this);
//        tv_set.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > 2000) {
            mFirstClickTime = currentTimeMillis;
            T.showShort(this, "再次点击退出程序");
        } else {
            LegalProvisionsFragment.catid=null;
            LegalProvisionsFragment.catname=null;
            LegalProvisionsFragment.bookname=null;
            LegalProvisionsFragment.state=0;
            LegalProvisionsFragment.isEnter=true;
            BaseApplication.getAppLoctin().exit();
        }
    }



    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    protected void onDestroy() {
        PushDemoReceiver.payloadData.delete(0,PushDemoReceiver.payloadData.length());
        super.onDestroy();
    }

    //监听是否开启不保留活动
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void listenrKillActivity() {
        int alwaysFinish = Settings.Global.getInt(getContentResolver(), Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0);
        if (alwaysFinish == 1) {
            Dialog dialog = null;
            dialog = new AlertDialog.Builder(this)
                    .setMessage("由于您已开启 '不保留活动', 可能导致蜂巢无法正常使用.我们建议您点击左下方 '设置' 按钮, 在'开发者选项' 中关闭 '不保留活动' 功能")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                            startActivity(intent);
                        }
                    }).create();
            dialog.show();
        }
    }

    public static void showIco(boolean state,ImageView imageView) {
        if(mImageView_tishi!=null){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int deviceWidth = PublicTool.getDeviceWidth();
            int deviceHeight = PublicTool.getDeviceHeight();
            params.setMargins(deviceWidth * 5 / 6, deviceHeight - 170, 0, 0);
            mImageView_tishi.setBackgroundResource(R.mipmap.pic_dot);
            mImageView_tishi.setLayoutParams(params);
            if (state) {
                mImageView_tishi.setVisibility(View.VISIBLE);
            } else {
                mImageView_tishi.setVisibility(View.GONE);
            }
        }else {
            mImageView_tishi=imageView;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int deviceWidth = PublicTool.getDeviceWidth();
            int deviceHeight = PublicTool.getDeviceHeight();
            params.setMargins(deviceWidth * 5 / 6, deviceHeight - 170, 0, 0);
            mImageView_tishi.setBackgroundResource(R.mipmap.pic_dot);
            mImageView_tishi.setLayoutParams(params);
            if (state) {
                mImageView_tishi.setVisibility(View.VISIBLE);
            } else {
                mImageView_tishi.setVisibility(View.GONE);
            }
        }


    }
}
