package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.chanlytech.unicorn.imageloader.utils.L;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/9/11.
 */
public class WebModule extends BaseModule {
    public WebModule(Context context) {
        super(context);
    }
    /**
     * 获取分享信息
     * */
    public void getShareInfo(String objId,String type){
        Map<String,String> map=new HashMap<>();
        map.put("objId",objId);
        map.put("type",type);
        ServerUtil.getShareInfo(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("分享信息", data);
                callback(ResultCode.SHARE_CODE, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });

    }
}
