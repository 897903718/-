package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.activity.SearchActivity;
import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/12/2.
 */
public class SearchModule extends BaseModule {
    public SearchModule(Context context) {
        super(context);
    }

    /**
     * 关键字提交
     */
    public void keywordGather(String keyword) {
        Map<String, String> parmaps = new HashMap<>();
        parmaps.put("keyword", keyword);
        ServerUtil.keywordGather(parmaps, new HttpUtil.Response() {
            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE,ResultCode.EORROR_MSG);
            }

            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.keywordGather,data);
            }
        });
    }

    /**
     * 获取关键词列表
     */
    public void keywordList() {
        ServerUtil.keywordList(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }

            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.keywordList, data);
            }
        });
    }

}
