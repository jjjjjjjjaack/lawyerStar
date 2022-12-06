package com.qbo.lawyerstar.app.module.business.base;

import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_10_WYJJS;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_11_GSPC;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_12_DZQZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_13_FWZX;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_14_ZXZX;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_15_AIFW;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_1_HTWK;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_2_DXWS;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_3_FLZX;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_4_LSH;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_5_HTDZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_6_HTSH;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_7_FSCG;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_8_ZCSS;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_9_SSFJS;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.business.LawBusinessUtils;
import com.qbo.lawyerstar.app.module.home.base.HomeFrag;
import com.qbo.lawyerstar.app.module.mine.info.base.UserInfoBaseAct;
import com.qbo.lawyerstar.app.module.mine.vip.intro.VipIntroAct;
import com.qbo.lawyerstar.app.module.study.base.ILawStudyView;
import com.qbo.lawyerstar.app.module.study.base.LawStudyPresenter;
import com.qbo.lawyerstar.app.utils.FCacheUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;

public class LawBusinessFrag extends MvpFrag<ILawBusinessView, BaseModel, LawBusinessPresenter> implements ILawBusinessView {

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;

    @BindView(R.id.function_1_rcy)
    RecyclerView function_1_rcy;
    private MCommAdapter function_1_Adapter;

    @BindView(R.id.function_2_rcy)
    RecyclerView function_2_rcy;
    private MCommAdapter function_2_Adapter;

    @BindView(R.id.function_3_rcy)
    RecyclerView function_3_rcy;
    private MCommAdapter function_3_Adapter;

//    @BindView(R.id.vipname_tv)
//    TextView vipname_tv;
//    @BindView(R.id.vipdate_tv)
//    TextView vipdate_tv;

    @BindView(R.id.vipbg_iv)
    ImageView vipbg_iv;

    @Override
    public LawBusinessPresenter initPresenter() {
        return new LawBusinessPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_law_business;
    }

    @Override
    public void viewInitialization() {
        function_1_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        function_1_Adapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<HomeFrag.FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, HomeFrag.FuntionBean bean) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position < 4) {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 12);
                    layoutParams.bottomMargin = 0;
                } else {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 15);
                    layoutParams.bottomMargin = ResourceUtils.dp2px(context, 16);
                }

                ImageView imageView = (ImageView) mCommVH.getView(R.id.icon_iv);
                imageView.setImageResource(bean.iconRes);
                mCommVH.setText(R.id.name_tv, bean.name);
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LawBusinessUtils.jumpAction(getMContext(), bean.functionid, "");
                    }
                });
            }
        });
        function_1_rcy.setAdapter(function_1_Adapter);

        function_2_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        function_2_Adapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<HomeFrag.FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, HomeFrag.FuntionBean bean) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position < 4) {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 12);
                    layoutParams.bottomMargin = 0;
                } else {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 15);
                    layoutParams.bottomMargin = ResourceUtils.dp2px(context, 16);
                }

                ImageView imageView = (ImageView) mCommVH.getView(R.id.icon_iv);
                imageView.setImageResource(bean.iconRes);
                mCommVH.setText(R.id.name_tv, bean.name);
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LawBusinessUtils.jumpAction(getMContext(), bean.functionid, "");
                    }
                });
            }
        });
        function_2_rcy.setAdapter(function_2_Adapter);

        function_3_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        function_3_Adapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<HomeFrag.FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, HomeFrag.FuntionBean bean) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position < 4) {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 12);
                    layoutParams.bottomMargin = ResourceUtils.dp2px(context, 16);
                }
                ImageView imageView = (ImageView) mCommVH.getView(R.id.icon_iv);
                imageView.setImageResource(bean.iconRes);
                mCommVH.setText(R.id.name_tv, bean.name);
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LawBusinessUtils.jumpAction(getMContext(), bean.functionid, "");
                    }
                });
            }
        });
        function_3_rcy.setAdapter(function_3_Adapter);


        List<HomeFrag.FuntionBean> funtion_1_Beans = new ArrayList<>();
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_1_HTWK, getString(R.string.home_frag_function_tx1), R.mipmap.ic_h_function_1));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_2_DXWS, getString(R.string.home_frag_function_tx2), R.mipmap.ic_h_function_2));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_4_LSH, getString(R.string.home_frag_function_tx4), R.mipmap.ic_h_function_4));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_5_HTDZ, getString(R.string.home_frag_function_tx5), R.mipmap.ic_h_function_5));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_6_HTSH, getString(R.string.home_frag_function_tx6), R.mipmap.ic_h_function_6));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(FUNCTION_12_DZQZ, getString(R.string.home_frag_function_tx12), R.mipmap.ic_h_function_12));
        function_1_Adapter.setData(funtion_1_Beans);

        List<HomeFrag.FuntionBean> funtion_2_Beans = new ArrayList<>();
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_13_FWZX, getString(R.string.home_frag_function_tx13), R.mipmap.ic_h_function_13));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_14_ZXZX, getString(R.string.home_frag_function_tx14), R.mipmap.ic_h_function_14));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_3_FLZX, getString(R.string.home_frag_function_tx15), R.mipmap.ic_h_function_15));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_15_AIFW, getString(R.string.home_frag_function_tx16), R.mipmap.ic_h_function_16));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_8_ZCSS, getString(R.string.home_frag_function_tx8), R.mipmap.ic_h_function_8));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(FUNCTION_7_FSCG, getString(R.string.home_frag_function_tx7), R.mipmap.ic_h_function_7));
        function_2_Adapter.setData(funtion_2_Beans);

        List<HomeFrag.FuntionBean> funtion_3_Beans = new ArrayList<>();
        funtion_3_Beans.add(new HomeFrag.FuntionBean(FUNCTION_9_SSFJS, getString(R.string.home_frag_function_tx9), R.mipmap.ic_h_function_9));
        funtion_3_Beans.add(new HomeFrag.FuntionBean(FUNCTION_10_WYJJS, getString(R.string.home_frag_function_tx10), R.mipmap.ic_h_function_10));
        funtion_3_Beans.add(new HomeFrag.FuntionBean(FUNCTION_11_GSPC, getString(R.string.home_frag_function_tx11), R.mipmap.ic_h_function_11));
        function_3_Adapter.setData(funtion_3_Beans);

        refresh_layout.setEnableRefresh(false);
        refresh_layout.setEnableLoadMore(false);
    }

    @Override
    public void doBusiness() {
        showData();
    }

    public void showData() {
        FCacheUtils.getUserInfo(getMContext(), false, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                try {
                    if (userInfoBean.isVip()) {
//                    vipdate_tv.setText("有效期至" + userInfoBean.getRank_date());
//                    if (userInfoBean.rank != null) {
//                        vipname_tv.setText(userInfoBean.rank.getName());
//                    }
                        vipbg_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    FTokenUtils.getToken(getContext(), true);
                                } catch (NeedLoginException e) {
                                    return;
                                }
                                gotoActivity(VipIntroAct.class);
                            }
                        });
                    }else{
                        vipbg_iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    FTokenUtils.getToken(getContext(), true);
                                } catch (NeedLoginException e) {
                                    return;
                                }
                                gotoActivity(VipIntroAct.class);
                            }
                        });
                    }
                    GlideUtils.loadImageDefult(getMContext(), userInfoBean.rank_img, vipbg_iv);
                }catch (Exception e){
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    public void refresh(){
        showData();
    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }
}
