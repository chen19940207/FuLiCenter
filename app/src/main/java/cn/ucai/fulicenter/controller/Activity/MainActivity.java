package cn.ucai.fulicenter.controller.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.view.MFGT;

public class MainActivity extends AppCompatActivity {
    int index, currentIndex;
    RadioButton[] rbs = new RadioButton[5];
    @BindView(R.id.layout_NewGoods)
    RadioButton mlayoutNewGoods;
    @BindView(R.id.layout_Boutique)
    RadioButton mlayoutBoutique;
    @BindView(R.id.layout_Category)
    RadioButton mlayoutCategory;
    @BindView(R.id.layout_Cart)
    RadioButton mlayoutCart;
    @BindView(R.id.layout_Personal)
    RadioButton mlayoutPersonal;

    Fragment[] mFragment = new Fragment[5];
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rbs[0] = mlayoutNewGoods;
        rbs[1] = mlayoutBoutique;
        rbs[2] = mlayoutCategory;
        rbs[3] = mlayoutCart;
        rbs[4] = mlayoutPersonal;

        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFragment;
        mFragment[2] = mCategoryFragment;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
                .commit();
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
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                }
                break;
        }
        if (index != currentIndex) {
            setFragment();
            setRadioStatus();
        }
    }

    private void setFragment() {
        getSupportFragmentManager().beginTransaction().show(mFragment[index])
                .hide(mFragment[currentIndex]).commit();
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }
}
