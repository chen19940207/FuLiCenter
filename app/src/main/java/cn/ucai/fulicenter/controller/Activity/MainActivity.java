package cn.ucai.fulicenter.controller.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    int index, currentIndex;
    RadioButton rbNewGoods,rbBoutique,rbCategory,rbCart, rbPersonal;
    RadioButton[] rbs = new RadioButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        rbs[0] = rbNewGoods;
        rbs[1] = rbBoutique;
        rbs[2] = rbCategory;
        rbs[3] = rbCart;
        rbs[4] = rbPersonal;
    }

    private void initView() {
        rbNewGoods = (RadioButton) findViewById(R.id.layout_NewGoods);
        rbBoutique = (RadioButton) findViewById(R.id.layout_Boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_Category);
        rbCart = (RadioButton) findViewById(R.id.layout_Cart);
        rbPersonal = (RadioButton) findViewById(R.id.layout_Personal);
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_NewGoods:
                index = 0;
                break;
            case R.id.layout_Boutique:
                index = 1;
                break;
            case R.id.layout_Category:
                index = 2;
                break;
            case R.id.layout_Cart:
                index = 3;
                break;
            case R.id.layout_Personal:
                index = 4;
                break;
        }
        setRadioStatus();
    }

    private void setRadioStatus() {
        for (int i=0;i<rbs.length;i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
    }
}
