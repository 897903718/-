package com.chanlytech.unicorn.annotation.app;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 白猫
 * @version V1.0
 * @Title TAPrimaryKey 主键配置
 * @Package com.ta.core.util.db.annotation
 * @Description 不配置的时候默认找类的id或_id字段作为主键，column不配置的是默认为字段名，如果不设置主键，自动生成ID
 * @date 2013-1-20
 */
@Target(
        {
                METHOD,
                FIELD
        })
@Retention(RUNTIME)
public @interface UinField
{
    /**
     * 设置配置的名
     *
     * @return
     */
    public String name() default "";

    /**
     * 设置配置的默认值
     *
     * @return
     */
    public String defaultValue() default "";

}