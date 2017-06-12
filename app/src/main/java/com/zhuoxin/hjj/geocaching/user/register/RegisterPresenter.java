package com.zhuoxin.hjj.geocaching.user.register;

import com.zhuoxin.hjj.geocaching.okHttp.OkHttpNetClient;
import com.zhuoxin.hjj.geocaching.user.User;
import com.zhuoxin.hjj.geocaching.user.UserPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gqq on 2017/6/12.
 */
// 注册的业务类
public class RegisterPresenter {

    private RegisterView mRegisterView;

    public RegisterPresenter(RegisterView registerView) {
        mRegisterView = registerView;
    }

    // 注册的业务实现
    public void register(User user){

        // 显示进度条
        mRegisterView.showProgress();

        OkHttpNetClient.getInstances().getTreasureApi().register(user).enqueue(new Callback<RegisterResult>() {

            // 请求成功
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                mRegisterView.hideProgress();// 隐藏进度
                // 响应成功
                if (response.isSuccessful()){
                    RegisterResult registerResult = response.body();

                    // 响应体是不是为null
                    if (registerResult==null){
                        mRegisterView.showMessage("未知的错误");
                        return;
                    }
                    if(registerResult.getCode()==1){

                        // 真正的注册成功了
                        // 保存Tokenid
                        UserPrefs.getInstance().setTokenid(registerResult.getTokenId());
                        // 跳转到Home页面
                        mRegisterView.navigateToHome();
                    }
                    mRegisterView.showMessage(registerResult.getMsg());
                }
            }

            // 请求失败
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                mRegisterView.hideProgress();// 隐藏进度
                mRegisterView.showMessage("请求失败："+t.getMessage());
            }
        });
    }
}
