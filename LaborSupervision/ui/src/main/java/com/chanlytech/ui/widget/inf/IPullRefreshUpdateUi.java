package com.chanlytech.ui.widget.inf;

/**
 * 下拉刷新更新界面状态的接口
 * 
 * @author YangQiang
 * 
 */
public interface IPullRefreshUpdateUi
{
	/**
	 * 下拉状态
	 */
	public void pull();

	/**
	 * 松手可刷新状态
	 */
	public void leaveHandCanRefresh();

	/**
	 * 正在刷新状态
	 */
	public void refreshing();

	/**
	 * 重置位置
	 */
	public void resetPosition();
}