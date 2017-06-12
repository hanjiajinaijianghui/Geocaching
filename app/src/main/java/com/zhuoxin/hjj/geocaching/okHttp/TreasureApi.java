package com.zhuoxin.hjj.geocaching.okHttp;

import com.zhuoxin.hjj.geocaching.user.User;
import com.zhuoxin.hjj.geocaching.user.login.LoginResult;
import com.zhuoxin.hjj.geocaching.user.register.RegisterResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/12.
 */

// 对应服务器接口
public interface TreasureApi {

    // 登录的请求
    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    // 注册的请求
    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

}
