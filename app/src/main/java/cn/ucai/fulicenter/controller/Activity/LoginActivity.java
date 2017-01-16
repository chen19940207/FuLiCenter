package cn.ucai.fulicenter.controller.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class LoginActivity extends AppCompatActivity {
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
                break;
            case R.id.btn_Login:
                break;
            case R.id.btn_Register:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
