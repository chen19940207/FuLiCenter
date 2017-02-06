package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;

import static android.R.attr.action;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;
    IModeUser model;
    User user;

    public CartAdapter(Context mContext, ArrayList<CartBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        this.mList.addAll(mList);
        model = new ModelUser();
        user = new User();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder vh =
                new CartViewHolder(View.inflate(mContext, R.layout.item_cart, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartViewHolder vh = (CartViewHolder) holder;
        vh.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvCartCount)
        TextView tvCartCount;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.chkSelect)
        CheckBox chkSelect;
        int listPosition;


        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            listPosition = position;
            GoodsDetailsBean detailsBean = mList.get(position).getGoods();
            if (detailsBean != null) {
                ImageLoader.downloadImg(mContext, ivGoodsThumb, detailsBean.getGoodsThumb());
                tvGoodsName.setText(detailsBean.getGoodsName());
                tvGoodsPrice.setText(detailsBean.getCurrencyPrice());
            }
            tvCartCount.setText("(" + mList.get(position).getCount() + ")");
            chkSelect.setChecked(mList.get(listPosition).isChecked());
        }

        @OnCheckedChanged(R.id.chkSelect)
        public void checkListener(boolean checked) {
            mList.get(listPosition).setChecked(checked);
            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
        }

        @OnClick(R.id.ivAddCart)
        public void addCart() {
            model.updateCart(mContext, I.ACTION_CART_ADD, user.getMuserName(),
                    mList.get(listPosition).getGoodsId(), 1, mList.get(listPosition).getId(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                mList.get(listPosition).setCount(mList.get(listPosition).getCount() + 1);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }

        @OnClick(R.id.ivReduceCart)
        public void delCart() {
            final int count = mList.get(listPosition).getCount();
            int action = I.ACTION_CART_ADD;
            if (count > 1) {
                action = I.ACTION_CART_UPDATE;
            } else {
                action = I.ACTION_CART_DEL;
            }
            model.updateCart(mContext, I.ACTION_CART_UPDATE, user.getMuserName(),
                    mList.get(listPosition).getGoodsId(), count - 1, mList.get(listPosition).getId(),
                    new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                if (count <= 1) {
                                    mList.remove(listPosition);
                                } else {
                                    mList.get(listPosition).setCount(count - 1);
                                }
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
        }
    }
}


