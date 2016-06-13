package com.chanlytech.unicorn.thread.inf;

/**
 * 回调
 * Created by Carlton on 2014/6/11.
 */
public interface ICallback
{
    public void onFinish(Object object);

    public class SimpleCallback implements ICallback
    {
        @Override
        public void onFinish(Object object)
        {

        }
    }
}
