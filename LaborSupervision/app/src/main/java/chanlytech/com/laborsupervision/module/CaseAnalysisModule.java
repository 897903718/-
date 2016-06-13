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
 * Created by Lyy on 2015/10/12.
 */
public class CaseAnalysisModule extends BaseModule {
    public CaseAnalysisModule(Context context) {
        super(context);
    }

    /**
     * 获取评论列表
     */
    public void getCommentList(String id, int pageIndex,int pageCount) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("pageIndex", pageIndex + "");
        map.put("pageCount", pageCount+"");//每页5条数据
        ServerUtil.getCommentList(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.GET_COMMENT_LIST, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }

    /**
     * 评论
     * */
    public void commentSub(String newid ,String content){
        Map<String,String>map=new HashMap<>();
        map.put("newid",newid);
        map.put("content",content);
        ServerUtil.commentSub(map, new HttpUtil.Response() {
            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }

            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.COMMENT_SUB, data);
            }
        });
    }

    /**
    * 点赞
    * */
    public void commentZan(String id){
        Map<String,String>map=new HashMap<>();
        map.put("id",id);
        ServerUtil.commentZan(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.COMMENT_ZAN, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
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
