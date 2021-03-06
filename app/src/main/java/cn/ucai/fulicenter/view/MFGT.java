package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.Activity.BoutiqueActivity;
import cn.ucai.fulicenter.controller.Activity.CategoryChildActivity;
import cn.ucai.fulicenter.controller.Activity.CollectsActivity;
import cn.ucai.fulicenter.controller.Activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.controller.Activity.LoginActivity;
import cn.ucai.fulicenter.controller.Activity.OrederActivity;
import cn.ucai.fulicenter.controller.Activity.RegisterActivity;
import cn.ucai.fulicenter.controller.Activity.SettingsActivity;
import cn.ucai.fulicenter.controller.Activity.UpdateNickActivity;
import cn.ucai.fulicenter.controller.fragment.PersonalFragment;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void startActivity(Activity activity, Class<?> clz) {
        activity.startActivity(new Intent(activity, clz));
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean) {
        Intent intent = new Intent(context, BoutiqueActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, boutiqueBean.getId());
        intent.putExtra(I.Boutique.NAME, boutiqueBean.getName());
        startActivity((Activity) context, intent);
    }

    public static void gotoGoodsDetail(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity((Activity) context, intent);
    }


    public static void gotoCategoryChild(Context mContext, int goodsId, String groupName, ArrayList<CategoryChildBean> categoryChildBeen) {
        Intent intent = new Intent(mContext, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, goodsId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.DATA, categoryChildBeen);
        startActivity((Activity) mContext, intent);
    }

    public static void gotoLogin(Activity context) {
        context.startActivityForResult(new Intent(context,LoginActivity.class),I.REQUEST_CODE_LOGIN);
    }
    public static void gotoLogin(Activity context,int code) {
        context.startActivityForResult(new Intent(context,LoginActivity.class),code);
    }

    public static void gotoRegister(LoginActivity context) {
        startActivity(context, RegisterActivity.class);
    }

    public static void gotoSettings(Activity activity) {
        startActivity(activity, SettingsActivity.class);

    }

    public static void gotoUpdateNick(Activity activity) {
        activity.startActivityForResult(new Intent(activity, UpdateNickActivity.class), I.REQUEST_CODE_NICK);
    }


    public static void gotoCollects(Activity activity) {
        startActivity(activity, CollectsActivity.class);
    }

    public static void gotoOrder(Activity activity,int payPrice) {
        Intent intent = new Intent(activity, OrederActivity.class);
        intent.putExtra(I.Cart.PAY_PRICE, payPrice);
        startActivity(activity, intent);
    }
}
