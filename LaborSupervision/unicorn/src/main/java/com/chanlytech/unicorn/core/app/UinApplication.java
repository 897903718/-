package com.chanlytech.unicorn.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.chanlytech.unicorn.annotation.app.UinInjector;
import com.chanlytech.unicorn.core.UinAppManager;
import com.chanlytech.unicorn.core.inf.IUinActivity;
import com.chanlytech.unicorn.exception.UinAppException;
import com.chanlytech.unicorn.log.UinLog;
import com.chanlytech.unicorn.netstate.UinNetChangeObserver;
import com.chanlytech.unicorn.netstate.UinNetWorkUtil.NetType;
import com.chanlytech.unicorn.netstate.UinNetworkStateReceiver;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Application
 */
public class UinApplication extends Application
{
    private static final String TAG = "UinApplication";
    /**
     * 程序崩溃问题异常处理
     */
    private UncaughtExceptionHandler mUncaughtExceptionHandler;
    /**
     * 当前的Activity,在Activity栈最顶上的Activity
     */
    private IUinActivity             mCurrentActivity;
    /**
     * Activity管理器
     */
    private UinAppManager            mAppManager;
    /**
     * 网络是否有效
     */
    private Boolean mNetworkAvailable = false;
    /**
     * 网络改变的观察者，网络改变后会被通知
     */
    private        UinNetChangeObserver mNetChangeObserver;
    /**
     * 注解加载器
     */
    private        UinInjector          mInjector;
    /**
     * Application的单实例
     */
    private static UinApplication       mInstance;
    /**
     * CoreHelper
     */
    private        CoreHelper           mCoreHelper;

    @Override
    public void onCreate()
    {
        onPreCreate();
        super.onCreate();
        init();
        onCreating();
        onAfterCreate();
        getAppManager();
    }

    /**
     * 初始化数据,紧接着super.onCreate()后调用
     */
    private void init()
    {
        mInstance = this;
        mCoreHelper = CoreHelper.getInstance();
        initImageLoader(getApplicationContext());
    }

    /**
     * 初始化图片加载类型
     * 重写这个方法能提供缓存图片的方式
     *
     * @param context
     */
    protected void initImageLoader(Context context)
    {
        mCoreHelper.initImageLoader(context);
    }

    /**
     * 获取Application实例
     *
     * @return
     */
    public static UinApplication getApplication()
    {
        return mInstance;
    }

    /**
     * 正在创建Application，在Application中的onCreate（）中调用，
     */
    private void onCreating()
    {
        mInstance = this;
        // 注册App异常崩溃处理器
        if (isUncaughtException())
        {
            if (mUncaughtExceptionHandler == null)
            {
                mUncaughtExceptionHandler = getUncaughtExceptionHandler();
            }
            Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
        }
        // 网络改变的通知
        mNetChangeObserver = new UinNetChangeObserver()
        {
            @Override
            public void onConnect(NetType type)
            {
                super.onConnect(type);
                UinApplication.this.onConnect(type);
            }

            @Override
            public void onDisConnect()
            {
                super.onDisConnect();
                UinApplication.this.onDisConnect();
            }
        };
        // 注册网络改变观察者
        UinNetworkStateReceiver.registerObserver(mNetChangeObserver);
    }

    /**
     * 是否异常补获，如果返回true，那么程序崩溃就不会出现FC窗口
     *
     * @return
     */
    protected boolean isUncaughtException()
    {
        return false;
    }

    /**
     * 没有网络连接的时候回调
     */
    protected void onDisConnect()
    {
        mNetworkAvailable = false;
        if (mCurrentActivity != null)
        {
            mCurrentActivity.onDisConnect();
        }
    }

    /**
     * 网络连接连接时回调
     */
    protected void onConnect(NetType type)
    {
        mNetworkAvailable = true;
        if (mCurrentActivity != null)
        {
            mCurrentActivity.onConnect(type);
        }
    }

    /**
     * Application.onCreate()执行后执行
     */
    protected void onAfterCreate()
    {

    }

    /**
     * Application.onCreate()执行前执行
     */
    protected void onPreCreate()
    {

    }

    /**
     * 返回一个自定义的程序异常处理类，默认返回一个UinAppException处理
     *
     * @return
     *
     * @see com.chanlytech.unicorn.exception.UinAppException
     */
    protected UncaughtExceptionHandler getUncaughtExceptionHandler()
    {
        return new UinAppException(this);
    }

    /**
     * 返回应用管理类，批量退出Activity或者获取当前Activity等
     *
     * @return
     */
    public UinAppManager getAppManager()
    {
        if (mAppManager == null)
        {
            mAppManager = UinAppManager.getAppManager();
        }
        return mAppManager;
    }

    /**
     * 退出应用程序
     *
     * @param isBackground
     *         是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground)
    {
        UinLog.d(TAG, "退出程序");
        mAppManager.AppExit(this, isBackground);
    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public boolean isNetworkAvailable()
    {
        return mNetworkAvailable;
    }

    /**
     * 当一个Activity正在创建的时候回调，它被回调会在
     * Activity.onCreate()中的super.onCreate()之前调用。
     *
     * @param activity
     *         正在被创建的Activity
     */
    protected void onActivityCreating(IUinActivity activity)
    {
    }

    /**
     * 当一个Activity被创建的时候回调，它会被回调在Activity.onCreate()中super.onCreate()后调用
     *
     * @param activity
     */
    protected void onActivityCreated(IUinActivity activity)
    {
        if (activity == null)
        {
            throw new NullPointerException("Activity is don't allow null");
        }
        mCurrentActivity = activity;
    }

    /**
     * 返回Activity，在Activity的onBackPressed中调用，用于管理Activity
     */
    public void onBackPressed()
    {
        mAppManager.onBackPressed();
        setCurrentActivity();
    }

    /**
     * 停止一个Activity，同Activity.finish(),用于管理Activity
     */
    public void finishActivity(Activity activity)
    {
        mAppManager.finishActivity(activity);
        setCurrentActivity();
    }

    /**
     * 停止一个Activity，同Activity.finish(),用于管理Activity
     */
    public void finishActivity()
    {
        mAppManager.finishActivity();
        setCurrentActivity();
    }

    /**
     * 从AppManger中移除最后一个Activity，和finishActivity()不同的是，这里移除的Activity不会调用Activity.finish();
     */
    public void removeLastActivity()
    {
        mAppManager.removeActivity(mCurrentActivity);
        setCurrentActivity();
    }

    /**
     * 设置当前显示的Activity
     */
    private void setCurrentActivity()
    {
        Activity currentActivity = mAppManager.currentActivity();
        if (currentActivity instanceof UinActivity)
        {
            mCurrentActivity = (UinActivity) currentActivity;
        }
    }

    /**
     * 返回注解加载器
     *
     * @return
     */
    public UinInjector getInjector()
    {
        if (mInjector == null)
        {
            mInjector = UinInjector.getInstance();
        }
        return mInjector;
    }
}
