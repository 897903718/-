package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;
import com.arialyy.frame.util.show.L;

import java.util.HashMap;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/9/11.
 */
public class NewsFragmentModule extends BaseModule {
    public NewsFragmentModule(Context context) {
        super(context);
    }


    /**
     * 获取法律资讯栏目
     */

    public void getCategoryList() {
        ServerUtil.getCategoryList(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("栏目列表", data);
                callback(ResultCode.GET_Column_LIST, data);
            }
        });


    }
}
