package cn.ucai.fulicenter.controller.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.IModelGoods;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

import static cn.ucai.fulicenter.application.I.TYPE_FOOTER;
import static cn.ucai.fulicenter.application.I.TYPE_ITEM;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;
    IModelGoods model;
    User user;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectAdapter(Context Context, ArrayList<CollectBean> List) {
        this.mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(List);
        model = new ModelGoods();
        user = FuLiCenterApplication.getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new CollectViewHolder(View.inflate(mContext, R.layout.item_collect, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.setFooterString(mContext.getString(getFooterString()));
        } else {
            CollectViewHolder gvh = (CollectViewHolder) holder;
            gvh.bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFooterString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void removeItem(int goodsId) {
        if (goodsId != 0) {
            mList.remove(new CollectBean(goodsId));
            notifyDataSetChanged();
        }
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView mivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mivCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout mlayoutGoods;

        int itemPosition;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            ImageLoader.downloadImg(mContext, mivGoodsThumb, mList.get(position).getGoodsThumb());
            mtvGoodsName.setText(mList.get(position).getGoodsName());
            itemPosition = position;
        }

        @OnClick({R.id.iv_collect_del, R.id.layout_goods})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_collect_del:
                    model.setCollect(mContext, mList.get(itemPosition).getGoodsId(),
                            user.getMuserName(), I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    mList.remove(itemPosition);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                    break;
                case R.id.layout_goods:
                    MFGT.gotoGoodsDetail(mContext, mList.get(itemPosition).getGoodsId());
                    break;
            }
        }
    }
}
