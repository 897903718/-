package chanlytech.com.laborsupervision.module;

import android.content.Context;
import android.util.Log;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.show.L;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.SharedPreferData;

/**
 * Created by Lyy on 2015/7/13.
 */
public class RegisteredModule extends BaseModule {
    private Context mContext;
    public RegisteredModule(Context context) {
        super(context);
        this.mContext=context;
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

    //注册账号
    public void userRegister(String phone, String password, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("telphone", phone);
        map.put("password", password);
        map.put("code", code);
//        map.put("baiduId",SharedPreferData.readString(mContext, "CID").toString());
//        map.put("baiduId",SharedPreferData.readString(mContext, "CID").toString());
//        map.put("YHcode",YHcode);
        ServerUtil.userRegistered(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.d("用户注册返回", data);
                callback(ResultCode.REGISTER, data);
            }
        });

    }


    /**
     * 用户----修改用户信息
     */
    public void modifyUserInfo(String address,String cityId) {
        Map<String,String>map=new HashMap<>();
        map.put("address",address);
        map.put("cityId",cityId);
        map.put("baiduId", SharedPreferData.readString(mContext, "CID").toString());
        ServerUtil.modifyUserInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("修改用户信息返回", data);
                callback(ResultCode.MODIFY_USER_INFO, data);
            }
        });
    }
    /**
     * 绑定推送id
     *
     *
     * */
    public void bingCid(){
        Map<String,String>map=new HashMap<>();
        map.put("baiduId", SharedPreferData.readString(mContext, "CID").toString());
        ServerUtil.modifyUserInfo(map,new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                com.chanlytech.unicorn.imageloader.utils.L.i("推送id绑定结果", data);
            }
        });
    }
}
