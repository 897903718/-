/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.chanlytech.unicorn.database.inf;

import android.database.sqlite.SQLiteDatabase;

/**
 * dbUpdateListener数据库升级监听器
 */
public interface IDBUpdateListener
{
    /**
     * 数据库更新回调
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    /**
     * 更新的时候是否删除表
     *
     * @return
     */
    public boolean isDeleteTables();
}