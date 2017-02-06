package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModeUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    @BindView(R.id.iv_persona_center_msg)
    ImageView ivPersonaCenterMsg;
    @BindView(R.id.tv_center_settings)
    TextView tvCenterSettings;
    @BindView(R.id.center_top)
    RelativeLayout centerTop;
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.iv_user_qrcode)
    ImageView ivUserQrcode;
    @BindView(R.id.center_user_info)
    RelativeLayout centerUserInfo;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;
    @BindView(R.id.layout_center_collect)
    LinearLayout layoutCenterCollect;
    @BindView(R.id.center_user_collects)
    RelativeLayout centerUserCollects;
    @BindView(R.id.center_user_order_lis)
    GridView centerUserOrderLis;
    @BindView(R.id.ll_user_life)
    LinearLayout llUserLife;
    @BindView(R.id.ll_user_store)
    LinearLayout llUserStore;
    @BindView(R.id.ll_user_members)
    LinearLayout llUserMembers;


    IModeUser model;

    public PersonalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, layout);
        initOrderList();
        initData();
        return layout;
    }

    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> oreder1 = new HashMap<String, Object>();
        oreder1.put("order", R.drawable.order_list1);
        data.add(oreder1);
        HashMap<String, Object> oreder2 = new HashMap<String, Object>();
        oreder2.put("order", R.drawable.order_list2);
        data.add(oreder2);
        HashMap<String, Object> oreder3 = new HashMap<String, Object>();
        oreder3.put("order", R.drawable.order_list3);
        data.add(oreder3);
        HashMap<String, Object> oreder4 = new HashMap<String, Object>();
        oreder4.put("order", R.drawable.order_list4);
        data.add(oreder4);
        HashMap<String, Object> oreder5 = new HashMap<String, Object>();
        oreder5.put("order", R.drawable.order_list5);
        data.add(oreder5);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.iv_order});
        centerUserOrderLis.setAdapter(adapter);
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
            getCollectCount();
        } else {
            //    MFGT.gotoLogin(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void loadUserInfo(User user) {
        //  ImageLoader.downloadImg(getContext(), ivUserAvatar, user.getAvatarPath());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivUserAvatar);
        tvUserName.setText(user.getMuserNick());
        loadCollectCount("0");
    }

    private void getCollectCount() {
        model = new ModelUser();
        model.collectCount(getContext(), FuLiCenterApplication.getUser().getMuserName(),
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            loadCollectCount(result.getMsg());
                        } else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void loadCollectCount(String count) {
        tvCollectCount.setText(String.valueOf(count));

    }


    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void settings(View view) {
        MFGT.gotoSettings(getActivity());
    }


    @OnClick(R.id.layout_center_collect)
    public void collects() {
        MFGT.gotoCollects(getActivity());
    }
}
