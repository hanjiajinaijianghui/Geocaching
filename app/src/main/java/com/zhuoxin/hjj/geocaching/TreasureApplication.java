package com.zhuoxin.hjj.geocaching;

import android.app.Application;

import com.zhuoxin.hjj.geocaching.user.UserPrefs;

/**
 * Created by Administrator on 2017/6/12.
 */

public class TreasureApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserPrefs.init(this);
    }
}
