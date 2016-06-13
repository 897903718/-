/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.chanlytech.unicorn.core;

import android.os.Bundle;
import android.view.View;

import com.chanlytech.unicorn.core.app.AbstractApplication;
import com.chanlytech.unicorn.core.app.UinFragment;
import com.chanlytech.unicorn.core.inf.IControl;
import com.chanlytech.unicorn.core.inf.IModel;
import com.chanlytech.unicorn.core.inf.IObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象Fragment
 *
 * @author Higgses
 */
public abstract class AbstractFragment<Model extends IModel> extends UinFragment implements IControl<Model>, View.OnClickListener
{
    /**
     * 模型
     */
    private List<IModel>     mModels;
    /**
     * Activity
     */
    private AbstractActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        preOnCreate();
        super.onCreate(savedInstanceState);
        mActivity = (AbstractActivity) getActivity();
        mModels = new ArrayList<>();
        IModel iModel = initModel();
        if (iModel != null)
        {
            mModels.add(iModel);
        }
        List<IModel> iModels = initModels();
        if (iModels != null && iModels.size() > 0)
        {
            mModels.addAll(iModels);
        }
        registerObserves();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        onDataFinish(savedInstanceState);
    }

    @Override
    public void onDataFinish(Bundle savedInstanceState)
    {

    }

    /**
     * 注册观察者
     */
    private void registerObserves()
    {
        for (IModel model : mModels)
        {
            model.addObserves(this);
        }
    }

    @Override
    public List<IModel> initModels()
    {
        return null;
    }

    @Override
    public IModel initModel()
    {
        return null;
    }

    @Override
    public <M extends IModel> M getModel(Class<M> modelType)
    {
        for (IModel model : mModels)
        {
            if (modelType.getName().equals(model.getClass().getName()))
            {
                return (M) model;
            }
        }
        try
        {
            throw new ClassNotFoundException("class" + modelType.toString() + " not found in register models");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Model getModel()
    {
        if (mModels != null && mModels.size() > 0)
        {
            return (Model) mModels.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void preOnCreate()
    {

    }

    @Override
    public void update(IObservable observable, Object data)
    {

    }

    @Override
    public int getContentView()
    {
        return 0;
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view)
    {
        hideKeyboard();
    }

    /**
     * 显示提示
     *
     * @param msg
     */
    public void showToast(String msg)
    {
        ((AbstractApplication) getApp()).showToast(msg);
    }

    public void showToast(int resId)
    {
        ((AbstractApplication) getApp()).showToast(resId);
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard()
    {
        mActivity.hideKeyboard();
    }

    /**
     * 隐藏键盘，使用hideSoftInputFromWindow中的flags
     *
     * @param flags
     *         InputMethodManager.hideSoftInputFromWindow（IBinder windowToken, int flags）中的flags
     */
    public void hideKeyboard(int flags)
    {
        mActivity.hideKeyboard(flags);
    }

    /**
     * 显示键盘
     */
    public void showKeyboard()
    {
        mActivity.showKeyboard();
    }

    public void showKeyboard(int flags)
    {
        mActivity.showKeyboard(flags);
    }

    public void toggleSoftInputFromWindow()
    {
        mActivity.toggleSoftInputFromWindow();
    }
}
