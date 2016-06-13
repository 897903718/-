package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2016/1/27.
 */
public class RightsMoudle extends BaseModule {
    public RightsMoudle(Context context) {
        super(context);
    }

    /**
     * 获取维权问题
     * */

    public void WeiQuan(){
        ServerUtil.weiQuan(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.WEIQUAN, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
            }
        });
    }

    /**
     * 维权信息提交
     * */

    public void tijiao(String optionid,String enterprisename,String address,String phone){
        Map<String,String>map=new HashMap<>();
        map.put("optionid",optionid);
        map.put("enterprisename",enterprisename);
        map.put("address",address);
        map.put("phone",phone);
        ServerUtil.tijiao(map,new HttpUtil.Response(){
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.TIJIAO,data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }
        });
    }
}
