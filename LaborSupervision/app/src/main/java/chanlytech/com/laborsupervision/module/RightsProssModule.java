package chanlytech.com.laborsupervision.module;

import android.content.Context;

import com.arialyy.frame.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2016/1/28.
 */
public class RightsProssModule extends BaseModule {
    public RightsProssModule(Context context) {
        super(context);
    }

    /**
     * 用户获取维权进度列表
     */
    public void getProssList(int pageIndex) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageCount", 2 + "");
        ServerUtil.getProssList(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.PRO_LIST, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }

    /**
     * 专业用户获取维权任务
     */
    public void getTaskList(int pageIndex) {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageCount", 10 + "");
        ServerUtil.getTaskList(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.GET_TASK_LIST, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
            }
        });
    }
    /**
     * 维权任务详情
     *
     * */
    public void taskDetail(String id){
        Map<String,String>map=new HashMap<>();
        map.put("id",id);
        ServerUtil.getTaskDetail(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.TASK_DETAIL, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }


    /**
     * 处理维权任务
     *
     * */
    public void proTask(String id){
        Map<String,String>map=new HashMap<>();
        map.put("id",id);
        ServerUtil.ProTask(map, new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.TASK_PRO, data);
            }

            @Override
            public void onError(Object error) {
                super.onError(error);
                callback(ResultCode.EORROR_CODE, ResultCode.EORROR_MSG);
            }
        });
    }
}
