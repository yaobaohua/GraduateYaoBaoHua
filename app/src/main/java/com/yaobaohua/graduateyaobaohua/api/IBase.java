package com.yaobaohua.graduateyaobaohua.api;

/**
 * @author LiBin
 * @ClassName: IntroductoryActivity
 * @Description: TODO Activity的支持类接口，主要定义了Activity中常用的功能
 * @date 2015年3月26日 下午4:01:00
 */
public interface IBase {
    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 显示dialog
     */
    void showLoading();

    /**
     * 隐藏 dialog
     */
    void hideLoading();

    /**
     * 判断是否有网络连接,没有返回false
     */
    boolean isInternetConnected();

    /**
     * 用户是否在线（当前网络是否重连成功）
     */
    boolean getUserOnlineState();

    /**
     * 设置用户在线状态 true 在线 false 不在线
     *
     * @param isOnline
     */
    void setUserOnlineState(boolean isOnline);


    /**
     * 修改--------增加 initViews initData接口
     * author ShenZhenWei
     * email  826337999@qq.com
     */
    public void initViews();//初始化view

    public void initData();//填充Data

}
