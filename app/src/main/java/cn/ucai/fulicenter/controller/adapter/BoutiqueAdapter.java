package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.Activity.BoutiqueActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

import static cn.ucai.fulicenter.application.I.TYPE_FOOTER;
import static cn.ucai.fulicenter.application.I.TYPE_ITEM;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context Context, ArrayList<BoutiqueBean> List) {
        this.mContext = Context;
        mList = new ArrayList<>();
        if (List != null) {
            mList.addAll(List);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*RecyclerView.ViewHolder holder =
                new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_goods, null));*/
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            holder1.setFooterString(mContext.getString(getFooterString()));
        } else {
            BoutiqueViewHolder gvh = (BoutiqueViewHolder) holder;
            ImageLoader.downloadImg(mContext, gvh.mivBoutiqueImg, mList.get(position).getImageurl());
            gvh.mtvBoutiqueName.setText(mList.get(position).getName());
            gvh.mtvBoutiqueTitle.setText(mList.get(position).getTitle());
            gvh.mtvBoutiqueDescription.setText(mList.get(position).getDescription());
            gvh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MFGT.gotoBoutiqueChild(mContext,mList.get(position));
//                    mContext.startActivity(new Intent(mContext, BoutiqueActivity.class)
//                            .putExtra(I.NewAndBoutiqueGoods.CAT_ID, mList.get(position).getId())
//                            .putExtra(I.Boutique.NAME,mList.get(position).getName()));


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }
    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivBoutiqueImg)
        ImageView mivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mtvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mtvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mtvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mlayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
