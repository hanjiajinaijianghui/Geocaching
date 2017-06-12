package com.zhuoxin.hjj.geocaching.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户仓库,使用前必须先对其进行初始化 init(),否则会报错
 */
public class UserPrefs {

    private final SharedPreferences preferences;

    private static UserPrefs userPrefs;

    public static void init(Context context){
        userPrefs = new UserPrefs(context);
    }

    private static final String PREFS_NAME = "user_info";

    private static final String KEY_TOKENID = "key_tokenid";
    private static final String KEY_PHOTO = "key_photo";

    private UserPrefs(Context context) {
        preferences = context.getApplicationContext().
                getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static UserPrefs getInstance() {
        return userPrefs;
    }

    public void setTokenid(int tokenid) {
        preferences.edit().putInt(KEY_TOKENID, tokenid).commit();
    }

    public void setPhoto(String photoUrl) {
        preferences.edit().putString(KEY_PHOTO, photoUrl).commit();
    }

    public int getTokenid() {
        return preferences.getInt(KEY_TOKENID, -1);
    }

    public String getPhoto() {
        return preferences.getString(KEY_PHOTO, null);
    }

    public void clearUser(){
        preferences.edit().clear().commit();
    }
}
