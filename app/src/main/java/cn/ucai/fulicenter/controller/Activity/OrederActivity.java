package cn.ucai.fulicenter.controller.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;

public class OrederActivity extends AppCompatActivity {

    int payPrice = 0;
    @BindView(R.id.ed_order_name)
    EditText medOrderName;
    @BindView(R.id.ed_order_phone)
    EditText medOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner mspinOrderProvince;
    @BindView(R.id.ed_order_street)
    EditText medOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView mtvOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oreder);
        ButterKnife.bind(this);
        payPrice = getIntent().getIntExtra(I.Cart.PAY_PRICE, 0);
        setView();
    }

    private void setView() {
        mtvOrderPrice.setText("合计：" + payPrice);
    }
}
