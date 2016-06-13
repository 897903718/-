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
 * Created by Lyy on 2015/9/10.
 */
public class InformationModule extends BaseModule {
    public InformationModule(Context context) {
        super(context);
    }

    public void getPosterAndNews(String catId, int pageIndex,String keywordid,String bookid) {
        Map<String, String> map = new HashMap<>();
        map.put("catId",catId);
        map.put("pageIndex", pageIndex + "");
        map.put("pageCount", "10");
        map.put("keywordid",keywordid);
        map.put("bookid",bookid);
        ServerUtil.getPosterAndNews(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                L.i("新闻列表", data);
                callback(ResultCode.GET_POSTER_NEWS, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });


    }


}
