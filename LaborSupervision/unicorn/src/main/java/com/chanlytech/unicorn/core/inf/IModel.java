/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.chanlytech.unicorn.core.inf;

/**
 * 模型
 * Create by YangQiang on 13-10-9.
 */
public interface IModel extends IObservable
{
    public IControl getControl();

    public void dataCallback();

    public void dataCallback(Class<?> dataClassType, Object data, String method);
}
