package com.chanlytech.unicorn.core.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.chanlytech.unicorn.annotation.app.UinInjector;

import java.util.List;

/**
 * Fragment
 */
public class UinFragment extends Fragment
{
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getApp().getInjector().injectView(this);
        initActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments)
        {
            UinFragment uinFragment = (UinFragment) fragment;
            uinFragment.onUinActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 修复系统中Fragment嵌套onActivityResult不调用的BUG,在最内Fragment使用：getParentFragment().startActivityForResult()
     * 方式启动Activity，然后这个方法会代替onActivityResult被回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * @see #onActivityResult(int, int, android.content.Intent)
     */
    public void onUinActivityResult(int requestCode, int resultCode, Intent data)
    {

    }

    /**
     * 初始化
     */
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

    public UinApplication getApp()
    {
        return (UinApplication) mActivity.getApplication();
    }
}
