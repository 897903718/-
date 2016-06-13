package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/7/23.
 */
public class ChoiceCityModule extends BaseModule {
    public ChoiceCityModule(Context context) {
        super(context);
    }

    /**
     * 获取城市列表
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

}
