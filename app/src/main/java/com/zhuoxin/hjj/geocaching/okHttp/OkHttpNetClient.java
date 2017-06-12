package com.zhuoxin.hjj.geocaching.okHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/6/11.
 */

public class OkHttpNetClient {
    public static final String BASE_URL = "http://admin.syfeicuiedu.com";
    private static OkHttpNetClient mOkHttpNetClient;
    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    private TreasureApi mTreasureApi;

    private OkHttpNetClient(){

        // 日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                // 设置超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        // Gson的非严格模式:setLenient
        Gson gson = new GsonBuilder().setLenient().create();
        // retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized OkHttpNetClient getInstances(){
        if (mOkHttpNetClient == null){
            mOkHttpNetClient = new OkHttpNetClient();
        }
        return mOkHttpNetClient;
    }

    //获取实现后的API
    public TreasureApi getTreasureApi(){
        if (mTreasureApi==null) {

            // 对接口请求的实现
            mTreasureApi = mRetrofit.create(TreasureApi.class);
        }
        return mTreasureApi;
    }

}
