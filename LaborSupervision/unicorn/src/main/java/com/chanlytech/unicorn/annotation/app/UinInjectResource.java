package com.chanlytech.unicorn.annotation.app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(
        {
                ElementType.FIELD,
                ElementType.PARAMETER,
                ElementType.METHOD
        })
@Retention(RetentionPolicy.RUNTIME)
public @interface UinInjectResource
{
    int id() default -1;
}
