package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.Activity.SplashActivity;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void startActivity(Activity context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void finish(Activity context) {
        context.finish();
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
