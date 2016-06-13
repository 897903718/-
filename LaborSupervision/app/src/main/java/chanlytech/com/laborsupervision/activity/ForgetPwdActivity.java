package chanlytech.com.laborsupervision.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;


import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.base.BaseEntity;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.ForgetPwdModule;
import chanlytech.com.laborsupervision.util.ValidateUtils;

public class ForgetPwdActivity extends BaseActivity<ForgetPwdModule> implements View.OnClickListener {
    /**
     * 忘记密码
     */
    @InjectView(R.id.et_phone)
    EditText mEditText_phone;
    @InjectView(R.id.et_code)
    EditText mEditText_code;
    @InjectView(R.id.et_pwd)
    EditText mEditText_pwd;
    @InjectView(R.id.btn_getcode)
    Button mButton_code;
    @InjectView(R.id.btn_registered)
    Button mButton_save;
    @InjectView(R.id.title)
    TextView mTextView_title;
    private  MyTimer myTimer;

    @Override
    public int setContentView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @Override
    public ForgetPwdModule initModule() {
        return new ForgetPwdModule(this);
    }

    private void initView() {
        mTextView_title.setText("忘记密码");
    }


    private void initLinster() {
        mButton_code.setOnClickListener(this);
        mButton_save.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_getcode:
                if(!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString().trim())){
                    T.showLong(this,"请输入正确的手机号码");
                    return;
                }else {
                    myTimer=new MyTimer(60000,1000);
                    myTimer.start();
                    getModule().getSmsCode(mEditText_phone.getText().toString().trim());
                }
                break;
            case R.id.btn_registered://保存
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
                getModule().changePwd(mEditText_phone.getText().toString().trim(),mEditText_code.getText().toString().trim(),mEditText_pwd.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.EORROR_CODE:
                T.showLong(this,data.toString());
                break;
            case ResultCode.GET_SMS_CODE:
                BaseEntity mBaseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (mBaseEntity.getStatus() == 1) {
                    T.showLong(this, "验证码已发送至您的手机，请注意查收");
                } else {
                    ErrorToast.showError(this, mBaseEntity.getErrorCode());
                }
                break;
            case ResultCode.CHANGE_PWD:
                BaseEntity mBaseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if (mBaseEntity1.getStatus()==1){
                    UserEntity mUserEntity = JSON.parseObject(mBaseEntity1.getData(), UserEntity.class);
                    T.showLong(this,"密码重置成功");
                    AppManager.setLoginState(true);//保存用户登录状态
//                    AppManager.setLoginState(true);//保存用户登录状态
//                    new SharePreferenceUtil(RegisteredActivity.this).setUserName(mBaseEntity1.getData());
                    AppManager.saveUser(mUserEntity);
                    finish();
                }else {
                    ErrorToast.showError(this, mBaseEntity1.getErrorCode());
                }
                break;
        }
    }

    class MyTimer extends CountDownTimer {

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
            mButton_code.setEnabled(false);
            mButton_code.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            mButton_code.setEnabled(true);
            mButton_code.setText("获取验证码");
        }
    }
}
