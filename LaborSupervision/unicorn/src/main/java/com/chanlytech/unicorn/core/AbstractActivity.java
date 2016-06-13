package com.chanlytech.unicorn.core;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.chanlytech.unicorn.core.app.AbstractApplication;
import com.chanlytech.unicorn.core.app.UinActivity;
import com.chanlytech.unicorn.core.inf.IControl;
import com.chanlytech.unicorn.core.inf.IModel;
import com.chanlytech.unicorn.core.inf.IObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的活动，为子类提供一些初始化的方法等 泛型T代表模型更新后返回给当前Activity视图的数据类型 Create by YangQiang on
 * 13-10-9.
 */
public abstract class AbstractActivity<Model extends IModel> extends UinActivity implements IControl<Model>
{
    /**
     * 模型
     */
    private ArrayList<IModel> mModels;
    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        preOnCreate();
        super.onCreate(savedInstanceState);
        int contentView = getContentView();
        if (contentView != 0)
        {
            setContentView(contentView);
        }
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
        onDataFinish(savedInstanceState);
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
    public void onDataFinish(Bundle savedInstanceState)
    {

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


    @Override
    public void preOnCreate()
    {
    }

    @Override
    public void update(IObservable observable, Object data)
    {
    }

    /**
     * 双击退出
     */
    public boolean onDoubleClickExit(long timeSpace)
    {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace)
        {
            doubleExitCallBack();
            mFirstClickTime = currentTimeMillis;
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 双击退出，间隔时间为2000ms
     *
     * @return
     */
    public boolean onDoubleClickExit()
    {
        return onDoubleClickExit(2000);
    }

    /**
     * 双击退出不成功的回调。 第一次点击后回调，直到第二次点击的时间超过了给定时间，每一个回合回调一次
     */
    public void doubleExitCallBack()
    {
        Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard()
    {
        hideKeyboard(InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘，使用hideSoftInputFromWindow中的flags
     *
     * @param flags
     *         InputMethodManager.hideSoftInputFromWindow（IBinder windowToken, int flags）中的flags
     */
    public void hideKeyboard(int flags)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View peekDecorView = getWindow().peekDecorView();
        inputMethodManager.hideSoftInputFromWindow(peekDecorView.getWindowToken(), flags);
    }

    /**
     * 显示键盘
     */
    public void showKeyboard()
    {
        showKeyboard(InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示键盘
     *
     * @param flags
     */
    public void showKeyboard(int flags)
    {
        InputMethodManager inputMethodManager = getInputMethodManager();
        View peekDecorView = getWindow().peekDecorView();
        inputMethodManager.showSoftInputFromInputMethod(peekDecorView.getWindowToken(), flags);
    }

    public void toggleSoftInputFromWindow(int hideFlags, int showFlags)
    {
        View peekDecorView = getWindow().peekDecorView();
        getInputMethodManager().toggleSoftInputFromWindow(peekDecorView.getWindowToken(), showFlags, hideFlags);
    }

    public void toggleSoftInputFromWindow()
    {
        View peekDecorView = getWindow().peekDecorView();
        getInputMethodManager().toggleSoftInputFromWindow(peekDecorView.getWindowToken(), InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public InputMethodManager getInputMethodManager()
    {
        return (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    /**
     * 点击事件,所有子类必须重写这个方法，在XML中配置onClick属性名称一定要是onClick
     *
     * @param view
     */
    public void onClick(View view)
    {
        hideKeyboard();
    }
}
