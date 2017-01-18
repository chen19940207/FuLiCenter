package cn.ucai.fulicenter.controller.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = SettingsActivity.class.getSimpleName();

    @BindView(R.id.iv_user_profile_avatar)
    ImageView mivUserProfileAvatar;
    @BindView(R.id.tv_user_profile_name)
    TextView mtvUserProfileName;
    @BindView(R.id.tv_user_profile_nick)
    TextView mtvUserProfileNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this, "设置");
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mivUserProfileAvatar);
        mtvUserProfileName.setText(user.getMuserName());
        mtvUserProfileNick.setText(user.getMuserNick());
    }


    @OnClick(R.id.btn_logout)
    public void logout() {
        FuLiCenterApplication.setUser(null);
        SharePrefrenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }

    @OnClick(R.id.layout_user_profile_nickname)
    public void updateNick() {
        L.e(TAG, "1");
        MFGT.gotoUpdateNick(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG,"resultCode="+requestCode);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK) {
            L.e(TAG,"resultCode="+requestCode);
            mtvUserProfileNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        }
    }
}
