package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

import static cn.ucai.fulicenter.application.I.TYPE_FOOTER;
import static cn.ucai.fulicenter.application.I.TYPE_ITEM;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;
    boolean isDragging;
    String footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public GoodsAdapter(Context Context, ArrayList<NewGoodsBean> List) {
        this.mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(List);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*RecyclerView.ViewHolder holder =
                new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));*/
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        switch (viewType) {
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.item_goods, null);
                return new GoodsViewHolder(view);
            case TYPE_FOOTER:
                view = inflater.inflate(R.layout.item_footer, null);
                return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            holder1.mtvFooter.setText(getFooter());
            return;
        }
        GoodsViewHolder gvh = (GoodsViewHolder) holder;
        ImageLoader.downloadImg(mContext, gvh.mivGoodsThumb, mList.get(position).getGoodsThumb());
        gvh.mtvGoodsName.setText(mList.get(position).getGoodsName());
        gvh.mtvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView mivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mtvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mlayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFooter)
        TextView mtvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
