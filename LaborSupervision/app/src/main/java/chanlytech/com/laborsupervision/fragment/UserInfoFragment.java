package chanlytech.com.laborsupervision.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.annotation.Inject;
import com.arialyy.frame.util.KeyBoardUtils;
import com.arialyy.frame.util.NetUtils;
import com.arialyy.frame.util.show.T;
import com.chanlytech.ui.widget.NotScrollListView;
import com.chanlytech.unicorn.imageloader.utils.L;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.activity.CaseAnalysisActivity;
import chanlytech.com.laborsupervision.activity.ForgetPwdActivity;
import chanlytech.com.laborsupervision.activity.MainActivity;
import chanlytech.com.laborsupervision.activity.MyMessageActivity;
import chanlytech.com.laborsupervision.activity.MyReservationActivity;
import chanlytech.com.laborsupervision.activity.MyTestActivity;
import chanlytech.com.laborsupervision.activity.RegisteredActivity;
import chanlytech.com.laborsupervision.activity.RightsProssActivity;
import chanlytech.com.laborsupervision.activity.RightsProtectionTaskActivity;
import chanlytech.com.laborsupervision.activity.SetActivity;
import chanlytech.com.laborsupervision.activity.UserInfoActivity;
import chanlytech.com.laborsupervision.adapter.MenuAdapter;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.base.BaseFragment;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.MenueEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.UserInfoModule;
import chanlytech.com.laborsupervision.util.Constants;
import chanlytech.com.laborsupervision.util.Imageload;
import chanlytech.com.laborsupervision.util.SharedPreferData;
import chanlytech.com.laborsupervision.util.ValidateUtils;
import chanlytech.com.laborsupervision.widget.MyView;
import chanlytech.com.laborsupervision.widget.PagerSlidingTabStrip;

/**
 * Created by Lyy on 2015/8/31.
 * 个人中心
 */
public class UserInfoFragment extends BaseFragment<UserInfoModule> implements View.OnClickListener {
    @InjectView(R.id.ll_login)
    LinearLayout mLayout_login;
    @InjectView(R.id.ll_no_login)
    LinearLayout mLayout_no_login;
    @InjectView(R.id.tv_register)
    TextView tv_register;
    @InjectView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @InjectView(R.id.et_phone)
    EditText mEditText_phone;
    @InjectView(R.id.et_pwd)
    EditText mEditText_pwd;
    @InjectView(R.id.btn_login)
    Button mButton_login;
    @InjectView(R.id.iv_user)
    ImageView iv_user;
    @InjectView(R.id.tv_username)
    TextView mTextView_username;
    @InjectView(R.id.tv_user_type)
    TextView mTextView_usertype;
    @InjectView(R.id.rl_user_info)
    RelativeLayout mRelativeLayout;
    @InjectView(R.id.ll_add_menu)
    LinearLayout mLayout_menue;
    @InjectView(R.id.iv_back)
    ImageView back;
    @InjectView(R.id.tv_title)
    TextView title;
    @InjectView(R.id.tv_right)
    TextView tv_right;
    @InjectView(R.id.list)
    NotScrollListView notScrollListView;
    private MenuAdapter menuAdapter;
    Intent mIntent;
    private List<MenueEntity> mEnueEntities = new ArrayList<>();
//    String url = "http://m.dianhua.cn/detail/31ccb426119d3c9eaa794df686c58636121d38bc?apikey=jFaWGVHdFVhekZYWTBWV1ZHSkZOVlJWY&app=com.yulore.yellowsdk_ios&uid=355136051337627";

    public UserInfoFragment() {
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public int setContentView() {
        return R.layout.fragment_userinfo;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        back.setVisibility(View.GONE);
        title.setText("个人中心");
        tv_right.setText("设置");
        initLinster();



    }

    @Override
    public UserInfoModule initModule() {
        return new UserInfoModule(getActivity());
    }

    private void initView() {
        if (AppManager.isLogin()) {
            //显示登录后的界面
            if (SharedPreferData.readString(getActivity(), "lasttime").length() == 0) {
                getModule().getUserNewInfor("0");
            } else {
                getModule().getUserNewInfor(SharedPreferData.readString(getActivity(), "lasttime").toString());
            }
            mLayout_login.setVisibility(View.VISIBLE);
            mLayout_no_login.setVisibility(View.GONE);
            Imageload.loadImageRound(getActivity(), 100, AppManager.getUser().getAvatar(), iv_user);
            String path = AppManager.getUser().getAvatar();
            L.i("用户头像路径", path);
            mTextView_username.setText(AppManager.getUser().getNickName());
            if (AppManager.getUser().getUserType().equals("1")) {
                mTextView_usertype.setText("普通用户");
            } else if (AppManager.getUser().getUserType().equals("2")) {
                mTextView_usertype.setText("企业用户");
            } else if (AppManager.getUser().getUserType().equals("3")) {
                mTextView_usertype.setText("内部人员");
            }
        } else {
            //显示未登陆的界面
            mLayout_no_login.setVisibility(View.VISIBLE);
            mLayout_login.setVisibility(View.GONE);
        }


    }

    private void initLinster() {
        tv_register.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        mButton_login.setOnClickListener(this);
        mRelativeLayout.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        mLayout_login.setOnClickListener(this);
        mLayout_no_login.setOnClickListener(this);
        notScrollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView=new ImageView(getActivity());
                MainActivity.showIco(false,imageView);
                if(!Constants.isFastClick()){
                    if (mEnueEntities.get(position).getType() == 3) {
                        mIntent = new Intent(getActivity(), MyMessageActivity.class);
                        getActivity().startActivity(mIntent);
                    }else if (mEnueEntities.get(position).getType()==4){
                        //4维权进度
                        mIntent = new Intent(getActivity(), RightsProssActivity.class);
                        getActivity().startActivity(mIntent);
                    }else if(mEnueEntities.get(position).getType()==5){
                        //5维权任务
                        mIntent = new Intent(getActivity(), RightsProtectionTaskActivity.class);
                        getActivity().startActivity(mIntent);
                    }else {
                        mIntent = new Intent(getActivity(), MyTestActivity.class);
                        mIntent.putExtra("url", mEnueEntities.get(position).getUrl());
                        mIntent.putExtra("title", mEnueEntities.get(position).getName());
                        getActivity().startActivity(mIntent);
                    }
                }

            }
        });
    }

    public void onResume() {
        super.onResume();
        if(AppManager.isLogin()){
            getModule().getUserInfo();
        }
        initView();
        MobclickAgent.onPageStart("MainScreen"); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register://注册
                mIntent = new Intent(getActivity(), RegisteredActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_forget_pwd://忘记密码
                mIntent = new Intent(getActivity(), ForgetPwdActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_login://登录
                if (!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString().trim())) {
                    T.showLong(getActivity(), "请输入正确的手机号码");
                    return;
                }
                if (mEditText_pwd.getText().length() == 0) {
                    T.showLong(getActivity(), "请输入密码");
                    return;
                }
                getModule().login(mEditText_phone.getText().toString(), mEditText_pwd.getText().toString());
                //关闭软键盘
                KeyBoardUtils.closeKeybord(mEditText_pwd, getActivity());
                break;
            case R.id.rl_user_info:
                if(!Constants.isFastClick()){
                    mIntent = new Intent(getActivity(), UserInfoActivity.class);
                    mIntent.putExtra("userEntity", AppManager.getUser());
                    mIntent.putExtra("flag", "1");
                    startActivity(mIntent);
                }

                break;
            case R.id.tv_right://设置

                Intent intent1 = new Intent(getActivity(), SetActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.USER_LOGIN://登录
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    UserEntity userEntity = JSON.parseObject(baseEntity.getData(), UserEntity.class);
                    AppManager.setLoginState(true);
                    AppManager.saveUser(userEntity);
                    getModule().bingCid();
                    initView();
                } else {
                    ErrorToast.showError(getActivity(), baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(getActivity(), data.toString());
                break;
            case ResultCode.GET_USER_NEWS_INFO:
                //本地存储上次访问的时间
                SharedPreferData.writeStringdata(getActivity(), "lasttime", System.currentTimeMillis() / 1000 + "");
                BaseEntity mBaseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (mBaseEntity.getStatus() == 1) {
                    mEnueEntities = JSON.parseArray(mBaseEntity.getData(), MenueEntity.class);
                    menuAdapter = new MenuAdapter(getActivity(), mEnueEntities);
                    notScrollListView.setAdapter(menuAdapter);
                    menuAdapter.notifyDataSetChanged();
                }
                break;
            case ResultCode.GET_USER_INFO:
                BaseEntity baseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(baseEntity1.getStatus()==1){
                    //保存用户信息
                    UserEntity userEntity = JSON.parseObject(baseEntity1.getData(), UserEntity.class);
                    AppManager.saveUser(userEntity);
                }
                break;
        }
    }



}
