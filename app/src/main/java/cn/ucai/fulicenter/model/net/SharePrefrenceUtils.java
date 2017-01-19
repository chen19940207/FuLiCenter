package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME = "cn.user.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME = "cn.user.fulicenter_user_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferences mSharedPreferences;
    private static final String SHARE_KEY_USER_NAME = "share_key_user_name";


    public SharePrefrenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARE_PREFRENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharePrefrenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharePrefrenceUtils(context);
        }
        return instance;
    }

    public  void saveUser(String username) {
        mEditor.putString(SHARE_KEY_USER_NAME, username);
        mEditor.commit();
    }

    public  String getUser() {
        return mSharedPreferences.getString(SHARE_KEY_USER_NAME, null);
    }

    public void removeUser(){
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }


}
