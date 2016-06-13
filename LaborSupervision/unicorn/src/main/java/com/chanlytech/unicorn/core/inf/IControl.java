package com.chanlytech.unicorn.core.inf;

import android.os.Bundle;

import java.util.List;

/**
 * 控制器，实现了观察者接口，android中把XML布局的视图是视图层，Activity属于控制层
 */
public interface IControl<Model extends IModel> extends IObserver
{
    /**
     * 初始化界面之前的一些初始化工作
     */
    public void preOnCreate();

    /**
     * 准备数据完成
     */
    public void onDataFinish(Bundle savedInstanceState);

    /**
     * 返回布局文件ID
     */
    public int getContentView();

    /**
     * 添加模型
     */
    public List<IModel> initModels();

    public IModel initModel();

    /**
     * 返回Model
     *
     * @return
     */
    public <M extends IModel> M getModel(Class<M> modelType);


    /**
     * 返回Model
     *
     * @return
     */
    public Model getModel();
}