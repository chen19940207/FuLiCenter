package cn.ucai.fulicenter.controller.Activity;

import android.app.ProgressDialog;
import android.app.backup.FullBackupDataOutput;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
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


    OnSetAvatarListener mOnSetAvatarListener;
    ModelUser model;

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
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == I.REQUEST_CODE_NICK) {
            mtvUserProfileNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        } else {
            mOnSetAvatarListener.setAvatar(requestCode, data, mivUserProfileAvatar);
        }
    }

    private void uploadAvatar() {
        final User user = FuLiCenterApplication.getUser();
        model = new ModelUser();
        final ProgressDialog dilog = new ProgressDialog(this);
        dilog.setMessage(getString(R.string.update_user_avatar));
        dilog.show();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                I.AVATAR_TYPE_USER_PATH+ "/" + user.getMuserName() + user.getMavatarSuffix())));
        L.e(TAG, "file=" + file.getAbsolutePath());
        model.updateAvatar(this,
                user.getMuserName(),
                file,
                new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        int msg = R.string.update_user_avatar_fail;
                        if (s != null) {
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if (result != null) {
                                if (result.isRetMsg()) {
                                    msg = R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dilog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        dilog.dismiss();
                    }
                });
    }

    @OnClick(R.id.layout_user_profile_username)
    public void onClickUserName() {
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.iv_user_profile_avatar)
    public void onClickAvatar() {
        mOnSetAvatarListener = new OnSetAvatarListener(this,
                R.id.iv_user_profile_avatar,
                FuLiCenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }
}
