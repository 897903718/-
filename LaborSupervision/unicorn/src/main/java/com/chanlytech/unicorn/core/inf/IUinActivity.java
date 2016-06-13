package com.chanlytech.unicorn.core.inf;

import com.chanlytech.unicorn.netstate.UinNetWorkUtil.NetType;

/**
 * Activity接口
 */
public interface IUinActivity
{
    public void onConnect(NetType type);

    public void onDisConnect();
}
