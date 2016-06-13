package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.show.L;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/9/25.
 */
public class ForgetPwdModule extends BaseModule {
    public ForgetPwdModule(Context context) {
        super(context);
    }


    //获取短信验证码
    public void getSmsCode(String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("telphone", phone);
        ServerUtil.getSmsCode(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                L.d("短信验证", data);
                super.onResponse(data);
                callback(ResultCode.GET_SMS_CODE, data);
            }
        });
    }


    //忘记密码
    public void changePwd(String phone,String  code,String password){
        Map<String ,String>map=new HashMap<>();
        map.put("telphone",phone);
        map.put("code",code);
        map.put("password",password);
        ServerUtil.changPwd(map,new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.CHANGE_PWD,data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }
        });

    }
}
