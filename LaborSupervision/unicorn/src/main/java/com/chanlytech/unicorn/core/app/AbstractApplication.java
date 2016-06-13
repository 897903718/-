package com.chanlytech.unicorn.core.app;

import com.chanlytech.unicorn.core.UToast;

/**
 * 抽象Application
 *
 * @author YangQiang
 */
public abstract class AbstractApplication extends UinSharedPreferenceApplication
{
    /**
     * 显示提示
     *
     * @param msg
     */
    public void showToast(String msg)
    {
        UToast.show(this, msg);
    }

    public void showToast(int resId)
    {
        UToast.show(this, resId);
    }
}
