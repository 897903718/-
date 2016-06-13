package com.chanlytech.unicorn.core.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.chanlytech.unicorn.annotation.app.UinInjector;
import com.chanlytech.unicorn.core.inf.IUinActivity;
import com.chanlytech.unicorn.log.UinLog;
import com.chanlytech.unicorn.netstate.UinNetWorkUtil.NetType;

/**
 * 框架Activity，支持v4的FragmentActivity
 */
public class UinActivity extends FragmentActivity implements IUinActivity
{
    private static final String TAG = "UinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        notifyApplicationActivityCreating();
        super.onCreate(savedInstanceState);
        getApp().getAppManager().addActivity(this);
        initActivity();
        notifyApplicationActivityCreated();
    }

    @Override
    public void startActivity(Intent intent)
    {
        UinLog.d(TAG, "        ->>>>>>||启动Activity:" + this);
        super.startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(layoutResID);
        getApp().getInjector().injectView(this);
    }

    private void initActivity()
    {
        initInjector();
    }

    /**
     * 初始化注入器
     */
    private void initInjector()
    {
        UinInjector injector = getApp().getInjector();
        injector.injectResource(this);
        injector.inject(this);
    }

    @Override
    public void onBackPressed()
    {
        getApp().onBackPressed();
    }

    public void finishActivity(Activity activity)
    {
        getApp().finishActivity(activity);
    }

    public void finishActivity()
    {
        //        getApp().finishActivity();
        getApp().finishActivity(this);
    }

    public UinApplication getApp()
    {
        return (UinApplication) getApplication();
    }

    private void notifyApplicationActivityCreating()
    {
        getApp().onActivityCreating(this);
    }

    private void notifyApplicationActivityCreated()
    {
        getApp().onActivityCreated(this);
    }

    @Override
    public void onConnect(NetType type)
    {

    }

    @Override
    public void onDisConnect()
    {

    }
}
