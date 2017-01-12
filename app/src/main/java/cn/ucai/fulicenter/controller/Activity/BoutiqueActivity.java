package cn.ucai.fulicenter.controller.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class BoutiqueActivity extends AppCompatActivity {

    @BindView(R.id.boutique_back)
    ImageView boutiqueBack;
    @BindView(R.id.textBo)
    TextView textBo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        ButterKnife.bind(this);
        textBo.setText(getIntent().getStringExtra(I.CategoryGood.GOODS_NAME));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
    }

    public void onCheckedChange(View view) {
        BoutiqueActivity.this.finish();
    }
}
