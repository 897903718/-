package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/12/7.
 */
public class LegalProvisionsModule extends BaseModule {
    public LegalProvisionsModule(Context context) {
        super(context);
    }

    public  void getLegal(String catId, int pageIndex,String keywordid,String bookid){
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
                callback(ResultCode.LEGAL, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);

            }
        });
    }



    /**
     * 拉取法条书和章节列表
     * */
    public void FtBook(){
        ServerUtil.FtBook(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.FtBook, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }
}
