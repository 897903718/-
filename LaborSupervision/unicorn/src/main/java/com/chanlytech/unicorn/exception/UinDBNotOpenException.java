package com.chanlytech.unicorn.exception;

public class UinDBNotOpenException extends Exception
{
    private static final long serialVersionUID = 1L;

    public UinDBNotOpenException()
    {
        super();
    }

    public UinDBNotOpenException(String detailMessage)
    {
        super(detailMessage);
    }

}
