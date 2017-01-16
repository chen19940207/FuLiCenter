package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModeUser {
    void login(Context context,String username,String password, OnCompleteListener<String> listener);
    void register(Context context, String username, String nick, String password, OkHttpUtils.OnCompleteListener<String> listener);
}