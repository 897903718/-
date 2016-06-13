package chanlytech.com.laborsupervision.activity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.arialyy.frame.util.show.T;

import com.umeng.analytics.MobclickAgent;

import butterknife.InjectView;
import chanlytech.com.laborsupervision.R;
import chanlytech.com.laborsupervision.base.AppManager;
import chanlytech.com.laborsupervision.base.BaseActivity;
import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.config.ErrorToast;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.BaseEntity;
import chanlytech.com.laborsupervision.entiy.UserEntity;
import chanlytech.com.laborsupervision.module.ChangePwdModule;
import chanlytech.com.laborsupervision.util.ValidateUtils;

/**
 * 修改密码
 * */
public class ChangePwdActivity extends BaseActivity<ChangePwdModule> implements View.OnClickListener {
    @InjectView(R.id.et_phone_number)
    EditText mEditText_phone;
    @InjectView(R.id.inpu_code)
    EditText mEditText_code;
    @InjectView(R.id.btn_senphone)
    Button mButton;
    @InjectView(R.id.btn_ok)
    Button mButton_ok;
    @InjectView(R.id.et_pwd)
    EditText mEditText_pwd;
    @InjectView(R.id.title)
    TextView title;
    private MyTimer myTimer;
    @Override
    public int setContentView() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @Override
    public ChangePwdModule initModule() {
        return new ChangePwdModule(this);
    }

    private void initView() {
        title.setText("修改密码");
    }

    private void initLinster(){
        mButton_ok.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_senphone:
                if (!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString())) {
                    T.showShort(this, "对不起！您填写的手机号码格式有误，请重新填写");
                    return;
                } else {
                    myTimer=new MyTimer(60000,1000);
                    myTimer.start();
                    getModule().getSmsCode(mEditText_phone.getText().toString().trim());
                }
                break;
            case R.id.btn_ok:
                if(!ValidateUtils.isPhoneNumber(mEditText_phone.getText().toString())){
                    T.showShort(this, "对不起！您填写的手机号码格式有误，请重新填写");
                    return;
                }else if (mEditText_pwd.getText().length()<6){
                    T.showShort(this, "对不起！密码至少六位");
                    return;
                }
                getModule().changePwd(mEditText_phone.getText().toString().trim(),mEditText_code.getText().toString(),mEditText_pwd.getText().toString());
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
            mButton.setEnabled(false);
            mButton.setText(millisUntilFinished/1000+"秒");
        }

        @Override
        public void onFinish() {
            mButton.setEnabled(true);
            mButton.setText("获取验证码");
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
            case ResultCode.CHANGE_PWD:
                BaseEntity mBaseEntity1 = JSON.parseObject(data.toString(), BaseEntity.class);
                if(mBaseEntity1.getStatus()==1){
                    UserEntity userEntity= JSON.parseObject(mBaseEntity1.getData(), UserEntity.class);
                    AppManager.saveUser(userEntity);
                    AppManager.setLoginState(true);
                    T.showLong(this, "密码修改成功");
                    finish();
                }else {
                    ErrorToast.showError(this, mBaseEntity1.getErrorCode());
                }
                break;
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
