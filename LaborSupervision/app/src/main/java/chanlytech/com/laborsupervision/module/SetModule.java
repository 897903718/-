package chanlytech.com.laborsupervision.module;

import android.content.Context;
import android.os.Handler;

import com.arialyy.frame.http.HttpUtil;
import com.chanlytech.unicorn.utils.FileUtils;

import java.io.File;
import java.util.HashMap;

import chanlytech.com.laborsupervision.base.BaseApplication;
import chanlytech.com.laborsupervision.base.BaseModule;
import chanlytech.com.laborsupervision.config.ResultCode;
import chanlytech.com.laborsupervision.http.ServerUtil;

/**
 * Created by Lyy on 2015/7/23.
 */
public class SetModule extends BaseModule {
    public SetModule(Context context) {
        super(context);
    }

    /**
     * 计算应用缓存大小
     */
    public long getFolderSize() {
        long size;
        File file = BaseApplication.getApp().getExternalCacheDir();
        size = getCacheSize(file);
        File file1 = BaseApplication.getApp().getCacheDir();
        size = size + getCacheSize(file1);
        return size / 1048576;
    }

    /**
     * 计算文件大小
     *
     * @param file
     * @return
     */
    private long getCacheSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getCacheSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }

        } catch (Exception e) {

        }
        return size;
    }
    /**
     * 清楚缓存
     */
    public boolean deleteCacheFile() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String  isSuccess = "0";
                File externalCache = BaseApplication.getApp().getExternalCacheDir();
                File cache = BaseApplication.getApp().getCacheDir();
                if (FileUtils.deleteDir(externalCache)) {
                    isSuccess = "1";
                }
                if (FileUtils.deleteDir(cache)) {
                    isSuccess = "1";
                }
                callback(ResultCode.CLEAN_CACHE,isSuccess);
//                dataCallback(Boolean.class, isSuccess, "clearCacheCallback");
            }
        });

        return false;
    }

    /**
     * 版本更新
     * */
    public void update(){
        ServerUtil.updateVersion(new HashMap<String, String>(), new HttpUtil.Response() {
            @Override
            public void onResponse(String data) {
                super.onResponse(data);
                callback(ResultCode.UPDATE, data);
            }
        });
    }
}
