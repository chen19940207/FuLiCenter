package cn.ucai.fulicenter.controller.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.UserName)
    EditText mUserName;
    @BindView(R.id.rl_username)
    RelativeLayout rlUsername;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.Password)
    EditText mPassword;
    @BindView(R.id.rl_password)
    RelativeLayout rlPassword;
    @BindView(R.id.btn_Login)
    Button btnLogin;
    @BindView(R.id.btn_Register)
    Button btnRegister;
    @BindView(R.id.btnUrl)
    Button btnUrl;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    IModeUser model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btn_Login, R.id.btn_Register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finishActivity(this);
                break;
            case R.id.btn_Login:
                checkInput();
                break;
            case R.id.btn_Register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mUserName.setError(getString(R.string.user_name_connot_be_empty));
            mUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.password_connot_be_empty));
            mPassword.requestFocus();
        } else {
            login(username, password);
        }
    }

    private void login(String username, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModelUser();
        model.login(this, username, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    L.e(TAG, "result=" + result);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            User user = (User) result.getRetData();
                           boolean savaUser= UserDao.getInstance().savaUser(user);
                            L.e(TAG, "savaUser=" + savaUser);
                            if (savaUser) {
                                SharePrefrenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(user);
                            }
                            MFGT.finishActivity(LoginActivity.this);
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            }
                            if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    } else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                } else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();
            }


            @Override
            public void onError(String error) {

            }
        });
    }
}
