/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.chanlytech.unicorn.core.entity;

import android.view.View;

import com.chanlytech.unicorn.annotation.app.UinInjector;

/**
 * 适配器中的视图实体
 * Created by higgses on 14-4-21.
 */
public abstract class UinViewHolder
{
    public UinViewHolder(View view)
    {
        UinInjector.manualInjectView(this, view);
    }
}
