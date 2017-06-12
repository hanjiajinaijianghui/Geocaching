package com.zhuoxin.hjj.geocaching.user.login;

/**
 * Created by Administrator on 2017/6/12.
 */
//登陆的视图接口
public interface LoginView {
    void showProgress();//显示进度条
    void hideProgress();//隐藏进度条
    void showMessage(String messgage);//显示消息
    void navigateToHome();//跳转界面

}
