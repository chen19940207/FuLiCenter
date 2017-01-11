package cn.ucai.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvFooter)
   TextView mtvFooter;


    public void setFooterString(String str) {
        mtvFooter.setText(str);
    }

    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}