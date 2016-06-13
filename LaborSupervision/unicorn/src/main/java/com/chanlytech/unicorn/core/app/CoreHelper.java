package com.chanlytech.unicorn.core.app;

import android.content.Context;

import com.chanlytech.unicorn.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.chanlytech.unicorn.imageloader.core.ImageLoader;
import com.chanlytech.unicorn.imageloader.core.ImageLoaderConfiguration;
import com.chanlytech.unicorn.imageloader.core.assist.QueueProcessingType;

/**
 * 核心辅助类
 * Created by Carlton on 2014/7/4.
 */
public class CoreHelper
{
    /**
     * CoreHelper单例
     */
    private static CoreHelper mInstance;

    /**
     * 获取单实例
     *
     * @return
     */
    public static CoreHelper getInstance()
    {
        if (mInstance == null)
        {
            synchronized (CoreHelper.class)
            {
                if (mInstance == null)
                {
                    mInstance = new CoreHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化图片加载类型
     *
     * @param context
     */
    public void initImageLoader(Context context)
    {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
