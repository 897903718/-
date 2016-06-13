/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.chanlytech.unicorn.exception.database;

public class UinRuntimeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public UinRuntimeException()
    {
        super();
    }

    public UinRuntimeException(String msg)
    {
        super(msg);
    }

    public UinRuntimeException(Throwable ex)
    {
        super(ex);
    }

    public UinRuntimeException(String msg, Throwable ex)
    {
        super(msg, ex);
    }

}
