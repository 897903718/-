package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.chanlytech.unicorn.utils.AndroidUtils;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/7/23.
 */
public class FreeBackModule extends BaseModule {
    public FreeBackModule(Context context) {
        super(context);
    }

    /**
     * 意见反馈
     * */
    public void submitFeedback(String content){
        Map<String,String>map=new HashMap<>();
        map.put("phoneType",AndroidUtils.getDeviceModel());
        map.put("system", AndroidUtils.getPackageInfo(getContext()).versionName);//
        map.put("content",content);
        ServerUtil.submitFeedback(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.SUBMIT_FEEBACK, data);
            }
        });
    }
}
