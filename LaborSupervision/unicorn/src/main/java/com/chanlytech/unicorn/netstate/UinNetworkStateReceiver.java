package com.chanlytech.unicorn.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.chanlytech.unicorn.log.UinLog;
import com.chanlytech.unicorn.netstate.UinNetWorkUtil.NetType;

import java.util.ArrayList;

/**
 * @Title NetworkStateReceiver
 * @Package com.ta.util.netstate
 * @Description 是一个检测网络状态改变的，需要配置： <code>
 * <receiver android:name="com.chanlytech.unicorn.netstate.UinNetworkStateReceiver" >
 * <intent-filter>
 * <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 * <action android:name="uin.android.net.conn.CONNECTIVITY_CHANGE" />
 * </intent-filter>
 * </receiver>
 * </code> 需要开启权限 <uses-permission
 * android:name="android.permission.CHANGE_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.CHANGE_WIFI_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_WIFI_STATE" />
 */
public class UinNetworkStateReceiver extends BroadcastReceiver
{
    private final  String  TAG               = "UinNetworkStateReceiver";
    /**
     * 网络是否有效
     */
    private static Boolean mNetworkAvailable = false;
    private static NetType mNetType;
    private static       ArrayList<UinNetChangeObserver> mNetChangeObserverArrayList  = new ArrayList<UinNetChangeObserver>();
    private final static String                          ANDROID_NET_CHANGE_ACTION    = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static  String                          DK_ANDROID_NET_CHANGE_ACTION = "uin.android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver mReceiver;

    private static BroadcastReceiver getReceiver()
    {
        if (mReceiver == null)
        {
            mReceiver = new UinNetworkStateReceiver();
        }
        return mReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mReceiver = UinNetworkStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(DK_ANDROID_NET_CHANGE_ACTION))
        {
            UinLog.i(TAG, "网络状态改变.");
            if (!UinNetWorkUtil.isNetworkAvailable(context))
            {
                UinLog.i(TAG, "没有网络连接.");
                mNetworkAvailable = false;
            }
            else
            {
                UinLog.i(TAG, "网络连接成功.");
                mNetType = UinNetWorkUtil.getAPNType(context);
                mNetworkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     *
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DK_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 检查网络状态
     *
     * @param mContext
     */
    public static void checkNetworkState(Context mContext)
    {
        Intent intent = new Intent();
        intent.setAction(DK_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     *
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext)
    {
        if (mReceiver != null)
        {
            try
            {
                mContext.getApplicationContext().unregisterReceiver(mReceiver);
            }
            catch (Exception e)
            {
                UinLog.d("UinNetworkStateReceiver", e.getMessage());
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public static Boolean isNetworkAvailable()
    {
        return mNetworkAvailable;
    }

    public static NetType getAPNType()
    {
        return mNetType;
    }

    private void notifyObserver()
    {

        for (int i = 0; i < mNetChangeObserverArrayList.size(); i++)
        {
            UinNetChangeObserver observer = mNetChangeObserverArrayList.get(i);
            if (observer != null)
            {
                if (isNetworkAvailable())
                {
                    observer.onConnect(mNetType);
                }
                else
                {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     *
     * @param observerKey
     *         observerKey
     */
    public static void registerObserver(UinNetChangeObserver observer)
    {
        if (mNetChangeObserverArrayList == null)
        {
            mNetChangeObserverArrayList = new ArrayList<UinNetChangeObserver>();
        }
        mNetChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     *
     * @param resID
     *         observerKey
     */
    public static void removeRegisterObserver(UinNetChangeObserver observer)
    {
        if (mNetChangeObserverArrayList != null)
        {
            mNetChangeObserverArrayList.remove(observer);
        }
    }
}
