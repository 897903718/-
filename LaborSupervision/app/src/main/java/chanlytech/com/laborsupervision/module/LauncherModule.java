package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.chanlytech.unicorn.imageloader.utils.L;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;
import chanlytech.com.laborsupervision.util.SharedPreferData;

/**
 * Created by Lyy on 2015/9/11.
 */
public class LauncherModule extends BaseModule {
    private Context mContext;
    public LauncherModule(Context context) {
        super(context);
        this.mContext=context;
    }

    /**
     * 获取启动图
     * */
    public  void getStartimg(){
        ServerUtil.getStartImg(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("启动图", data);
                callback(ResultCode.START_IMG, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);

            }
        });
    }
    /**
     * 检查更新
     * */
    public void update(){
        ServerUtil.updateVersion(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.UPDATE_INFO, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);

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
        map.put("baiduId", SharedPreferData.readString(mContext,"CID").toString());
        ServerUtil.modifyUserInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("推送id绑定结果", data);
            }
        });
    }

    //获取用户信息
    public void getUserInfo() {
        ServerUtil.getUserInfo(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                com.arialyy.frame.util.show.L.i("用户信息", data);
                callback(ResultCode.GET_USER_INFO, data);
            }
        });
    }
}
