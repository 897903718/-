package chanlytech.com.laborsupervision.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;
import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.RegisteredModule;
import chanlytech.com.laborsupervision.util.ValidateUtils;

/**
 * 注册界面2015-9-9
 */
public class RegisteredActivity extends BaseActivity<RegisteredModule> implements View.OnClickListener {
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.et_phone)
    EditText mEditText_phone;
    @InjectView(R.id.et_code)
    EditText mEditText_code;
    @InjectView(R.id.et_pwd)
    EditText mEditText_pwd;
    @InjectView(R.id.btn_getcode)
    Button mButton_getcode;
    @InjectView(R.id.radio_pwd_show)
    ImageView Radio_pwd_show;
    @InjectView(R.id.btn_registered)
    Button mButton_registered;
    private MyTimer myTimer;
    private boolean isChecked;

    @Override
    public int setContentView() {
        return R.layout.activity_registered;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @Override
    public RegisteredModule initModule() {
        return new RegisteredModule(this);
    }

    private void initView() {
        tv_title.setText("注册");
//        mEditText_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }


    private void initLinster() {
        mButton_getcode.setOnClickListener(this);
        mButton_registered.setOnClickListener(this);
        Radio_pwd_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_getcode://获取验证码
                if (!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString().trim())) {
                    T.showLong(this, "请输入正确的手机号码");
                    return;
                } else {
                    getModule().getSmsCode(mEditText_phone.getText().toString());
                    myTimer = new MyTimer(60000, 1000);
                    myTimer.start();

                }
                break;
            case R.id.btn_registered://注册
                if(!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString().trim())){
                    T.showLong(this, "请输入正确的手机号码");
                    return;
                }
                if(mEditText_code.getText().length()==0){
                    T.showLong(this, "请输入验证码");
                    return;
                }
                if (mEditText_pwd.getText().length()<6){
                    T.showLong(this,"密码最少六位");
                    return;
                }
                getModule().userRegister(mEditText_phone.getText().toString(),mEditText_pwd.getText().toString(),mEditText_code.getText().toString());
                break;
            case R.id.radio_pwd_show://显示密码
                if (!isChecked){
                    //明文显示
                    Radio_pwd_show.setBackgroundResource(R.mipmap.pwd_show);
                    mEditText_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked=true;
                }else {
                    //设置为秘文显示
                    Radio_pwd_show.setBackgroundResource(R.mipmap.ic_view);
                    mEditText_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked=false;
                }
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result) {
            case ResultCode.GET_SMS_CODE:
                BaseEntity mBaseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (mBaseEntity.getStatus() == 1) {
                    T.showLong(this, "验证码已发送至您的手机，请注意查收");
                } else {
                    ErrorToast.showError(this, mBaseEntity.getErrorCode());
                }
                break;
            case ResultCode.REGISTER:
                BaseEntity mBaseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (mBaseEntity1.getStatus()==1){
                    UserEntity mUserEntity = JSON.parseObject(mBaseEntity1.getData(), UserEntity.class);
                    AppManager.setLoginState(true);//保存用户登录状态
//                    AppManager.setLoginState(true);//保存用户登录状态
//                    new SharePreferenceUtil(RegisteredActivity.this).setUserName(mBaseEntity1.getData());
                    mUserEntity.setCityId("3145");//新注册用户默认
                    mUserEntity.setAddress("高新区");
                    AppManager.saveUser(mUserEntity);
                    //注册成功修改用户信息
                    getModule().modifyUserInfo("高新区", "3145");

                }else {
                    ErrorToast.showError(this, mBaseEntity1.getErrorCode());
                }
                break;
            case ResultCode.MODIFY_USER_INFO:
                BaseEntity BaseEntity=JSON.parseObject(data.toString(),BaseEntity.class);
                if(BaseEntity.getStatus()==1){
                    T.showLong(this, "注册成功");
                    UserEntity userEntity = JSON.parseObject(BaseEntity.getData(), UserEntity.class);
                    AppManager.saveUser(userEntity);
                    finish();
                }else {
                    ErrorToast.showError(this, BaseEntity.getErrorCode());
                }

                break;
        }
    }

    private class MyTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mButton_getcode.setEnabled(false);
            mButton_getcode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            mButton_getcode.setText("获得验证码");
            mButton_getcode.setEnabled(true);
        }
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
}
