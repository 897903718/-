package com.chanlytech.unicorn.core.helper;

/**
 * 流程回调接口
 *
 * @author YangQiang
 */
public interface IProcess<S, T>
{
    /**
     * 开始前的准本
     */
    public void preProcess();

    /**
     * 正在执行
     */
    public void processing();

    /**
     * 执行完毕
     *
     * @throws Exception
     */
    public void processFinish(S self, T data);
}
