package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.show.L;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.entiy.AdsEntity;
import chanlytech.com.laborsupervision.entiy.ServersEntity;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.SharedPreferData;

/**
 * Created by Lyy on 2015/8/31.
 */
public class HomeFragmentMoudle extends BaseModule {
    private Context mContext;
    public HomeFragmentMoudle(Context context) {
        super(context);
        this.mContext=context;
    }





    /**
     * 获取首页数据
     * */
    public void getHomeListData(){
        ServerUtil.getHomeListData(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                callback(ResultCode.GET_HOME_LIST,data);
            }
            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }
        });
    }

    /**
     * 修改用户地址
     * */
    /**
     * 用户----修改用户信息
     */
    public void modifyUserInfo(String address,String cityId) {
        Map<String,String>map=new HashMap<>();

        map.put("address",address);
        map.put("cityId",cityId);
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
     * 获取区域列表
     * */

    public void getCityList(){
        ServerUtil.getCityList(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.GET_CITY_LIST, data);
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
        ServerUtil.modifyUserInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                com.chanlytech.unicorn.imageloader.utils.L.i("推送id绑定结果", data);
            }
        });
    }
}
