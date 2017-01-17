package cn.ucai.fulicenter.controller.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class RegisterActivity extends AppCompatActivity {
    IModeUser model;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.UserName)
    EditText mUserName;
    @BindView(R.id.iv_Nick)
    ImageView ivNick;
    @BindView(R.id.Nick)
    EditText mNick;
    @BindView(R.id.iv_Password)
    ImageView ivPassword;
    @BindView(R.id.Password)
    EditText mPassword;
    @BindView(R.id.ConfirmPassword)
    EditText mConfirmPassword;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.Avatar)
    TextView mAvatar;
    @BindView(R.id.layout_user_avatar)
    RelativeLayout layoutUserAvatar;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.layout_register)
    LinearLayout layoutRegister;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this,"用户注册");
    }

    @OnClick(R.id.btnRegister)
    public void checkInput() {
        String username = mUserName.getText().toString().trim();
        String usernick = mNick.getText().toString().trim();
        String password1 = mPassword.getText().toString().trim();
        String confirm = mConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            mUserName.requestFocus();
        } else if (!username.matches("[a-zA-z]\\w{5,15}")) {
            mUserName.setError(getResources().getString(R.string.illegal_user_name));
        } else if (TextUtils.isEmpty(usernick)) {
            mNick.setError(getResources().getString(R.string.user_name_connot_be_empty));
            mNick.requestFocus();
        } else if (TextUtils.isEmpty(password1)) {
            mPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            mPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirm)) {
            mConfirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            mConfirmPassword.requestFocus();
        } else if (!password1.equals(confirm)) {
            mConfirmPassword.setError(getResources().getString(R.string.two_input_password));
            mConfirmPassword.requestFocus();
        } else {
            register(username, usernick, password1);
        }
    }

    private void register(String username, String usernick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model = new ModelUser();
        model.register(this, username, usernick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Result result1 = ResultUtils.getResultFromJson(result, Result.class);
                    if (result1 != null) {
                        if (result1.isRetMsg()) {
                            CommonUtils.showLongToast(R.string.register_success);
                            MFGT.finishActivity(RegisterActivity.this);
                        } else {
                            CommonUtils.showLongToast(R.string.register_fail_exists);
                        }
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail);
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }

}
