package com.chanlytech.ui.widget.inf;

/**
 * 键盘状态监听
 */
public interface InputListener
{
    public static final  byte   KEYBOARD_STATE_SHOW = -3;
    public static final  byte   KEYBOARD_STATE_HIDE = -2;
    public static final  byte   KEYBOARD_STATE_INIT = -1;
    public void onKeyboardStateChange(int state);
}