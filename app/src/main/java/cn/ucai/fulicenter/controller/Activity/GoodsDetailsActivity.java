package cn.ucai.fulicenter.controller.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.MFGT;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {
    private static final String TAG = GoodsDetailsActivity.class.getSimpleName();
    int goodsId = 0;
    ModelGoods model;
    @BindView(R.id.textBo)
    TextView textBo;
    @BindView(R.id.vline)
    LinearLayout vline;
    @BindView(R.id.iv_good_share)
    ImageView ivGoodShare;
    @BindView(R.id.iv_good_collect)
    ImageView ivGoodCollect;
    @BindView(R.id.iv_good_cart)
    ImageView ivGoodCart;
    @BindView(R.id.iv_cart_count)
    TextView ivCartCount;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.layout_image)
    RelativeLayout layoutImage;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;
    @BindView(R.id.layout_banner)
    RelativeLayout layoutBanner;
    @BindView(R.id.activity_goods_details)
    RelativeLayout activityGoodsDetails;

    boolean isCollect;
    IModeUser userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finishActivity(this);
        } else {
            initData();
        }
    }

    private void initData() {
        model = new ModelGoods();
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                } else {
                    MFGT.finishActivity(GoodsDetailsActivity.this);
                }
            }

            @Override
            public void onError(String error) {
                L.e(TAG, "error=" + error);
            }
        });
    }

    private void showGoodsDetail(GoodsDetailsBean result) {
        tvGoodName.setText(result.getGoodsName());
        tvGoodNameEnglish.setText(result.getGoodsEnglishName());
        tvGoodPriceCurrent.setText(result.getCurrencyPrice());
        tvGoodPriceShop.setText(result.getShopPrice());
        salv.startPlayLoop(indicator, getAlbumUrl(result), getAlbumCount(result));
        wvGoodBrief.loadDataWithBaseURL(null, result.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumCount(GoodsDetailsBean result) {
        if (result != null && result.getProperties() != null && result.getProperties().length > 0) {
            return result.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumUrl(GoodsDetailsBean result) {
        if (result != null && result.getProperties() != null && result.getProperties().length > 0) {
            AlbumsBean[] albums = result.getProperties()[0].getAlbums();
            if (albums != null && albums.length > 0) {
                String[] urls = new String[albums.length];
                for (int i = 0; i < albums.length; i++) {
                    urls[i] = albums[i].getImgUrl();
                }
                return urls;
            }
        }
        return new String[0];
    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
        MFGT.finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
    }

    @OnClick(R.id.iv_good_collect)
    public void setCollectListener() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            setCollect(user);
        } else {
            MFGT.gotoLogin(this);
            ivGoodCollect.setEnabled(true);
        }
    }

    @OnClick(R.id.iv_good_share)
    public void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }


    private void setCollect(User user) {
        model.setCollect(this, goodsId, user.getMuserName(), isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            setCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_COLLECT)
                                    .putExtra(I.Collect.GOODS_ID, goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }


    private void setCollectStatus() {
        if (isCollect) {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    private void initCollectStatus() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.isCollect(this, goodsId, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                }
            });
        }
    }

    @OnClick(R.id.iv_good_cart)
    public void addCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {

            userModel = new ModelUser();
            userModel.updateCart(this, I.ACTION_CART_ADD, user.getMuserName(), goodsId, 1, 0, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        //  FuLiCenterApplication.getMyCartList().put(goodsId, null);
                        CommonUtils.showLongToast(R.string.add_goods_success);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            MFGT.gotoLogin(this);

        }
    }
}

