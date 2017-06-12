package com.zhuoxin.hjj.geocaching.user.register;

/**
 * Created by gqq on 2017/6/12.
 */

// 注册的视图接口
public interface RegisterView {

    void showProgress();// 显示进度

    void hideProgress();// 隐藏进度

    void showMessage(String msg);// 显示信息

    void navigateToHome();// 跳转页面

}
