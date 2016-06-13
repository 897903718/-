package chanlytech.com.laborsupervision.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import chanlytech.com.laborsupervision.module.UserInfoModule;
import chanlytech.com.laborsupervision.util.ValidateUtils;

public class LoginActivity extends BaseActivity<UserInfoModule> implements View.OnClickListener {
    /**
     * 登录
     */
    @InjectView(R.id.title)
    TextView tv_title;
    @InjectView(R.id.et_phone)
    EditText editText_phone;
    @InjectView(R.id.et_pwd)
    EditText editText_pwd;
    @InjectView(R.id.btn_login)
    Button button_login;
    @InjectView(R.id.tv_forget_pwd)
    TextView textView_forget_pwd;
    @InjectView(R.id.tv_register)
    TextView textView_regis;
    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        initView();
        initLinster();
        BaseApplication.getAppLoctin().addActivity(this);
    }

    @Override
    public UserInfoModule initModule() {
        return new UserInfoModule(this);
    }

    private void initView(){
        tv_title.setText(R.string.login);
    }

    private void initLinster(){
        button_login.setOnClickListener(this);
        textView_forget_pwd.setOnClickListener(this);
        textView_regis.setOnClickListener(this);
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_login:
                if (!ValidateUtils.isPhoneNumber(editText_phone.getText().toString().trim())) {
                    T.showLong(this, "请输入正确的手机号码");
                    return;
                }
                if (editText_pwd.getText().length() == 0) {
                    T.showLong(this, "请输入密码");
                    return;
                }
                getModule().login(editText_phone.getText().toString(), editText_pwd.getText().toString());
                break;
            case R.id.tv_forget_pwd:
                Intent intent=new Intent(this,ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                Intent intent1=new Intent(this,RegisteredActivity.class);
                startActivity(intent1);
                break;
        }
    }


    @Override
    protected void dataCallback(int result, Object data) {
        super.dataCallback(result, data);
        switch (result){
            case ResultCode.USER_LOGIN://登录
                BaseEntity baseEntity = JSON.parseObject(data.toString(), BaseEntity.class);
                if (baseEntity.getStatus() == 1) {
                    UserEntity userEntity = JSON.parseObject(baseEntity.getData(), UserEntity.class);
                    AppManager.setLoginState(true);
                    AppManager.saveUser(userEntity);
                    getModule().bingCid();
                    finish();
                } else {
                    ErrorToast.showError(this, baseEntity.getErrorCode());
                }
                break;
            case ResultCode.EORROR_CODE:
                T.showLong(this, data.toString());
                break;
        }
    }
}
