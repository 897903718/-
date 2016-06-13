/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.chanlytech.unicorn.exception.database;

/**
 * 数据库错误时抛出
 */
public class DBException extends UinRuntimeException
{
    private static final long serialVersionUID = 1L;

    public DBException()
    {
    }

    public DBException(String msg)
    {
        super(msg);
    }

    public DBException(Throwable ex)
    {
        super(ex);
    }

    public DBException(String msg, Throwable ex)
    {
        super(msg, ex);
    }

}
