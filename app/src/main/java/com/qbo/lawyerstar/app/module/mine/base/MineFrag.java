package com.qbo.lawyerstar.app.module.mine.base;

import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_12_DZQZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_16_HTXZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_17_AJWT;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_18_WDDD;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_19_TSJY;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_20_SZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_2_DXWS;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_3_FLZX;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_4_LSH;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_5_HTDZ;
import static com.qbo.lawyerstar.app.module.business.LawBusinessUtils.FUNCTION_6_HTSH;

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
import com.qbo.lawyerstar.app.module.mine.login.base.LoginAct;
import com.qbo.lawyerstar.app.utils.FCacheUtils;
import com.qbo.lawyerstar.app.view.ChangeGasStationImageView2;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;

public class MineFrag extends MvpFrag<IMineView, BaseModel, MinePresenter> implements IMineView {


    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;

    @BindView(R.id.tologin_tv)
    View tologin_tv;
    @BindView(R.id.login_rl)
    View login_rl;

    @BindView(R.id.function_1_rcy)
    RecyclerView function_1_rcy;
    private MCommAdapter function_1_Adapter;

    @BindView(R.id.function_2_rcy)
    RecyclerView function_2_rcy;
    private MCommAdapter function_2_Adapter;

    @BindView(R.id.username_tv)
    TextView username_tv;
    @BindView(R.id.usertype_tv)
    TextView usertype_tv;
    @BindView(R.id.userlogo_civ)
    ChangeGasStationImageView2 userlogo_civ;

    @Override
    public MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_mine;
    }

    @Override
    public void viewInitialization() {
        function_1_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        function_1_Adapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<MineFrag.FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_mine_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, MineFrag.FuntionBean bean) {
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
            }
        });
        function_1_rcy.setAdapter(function_1_Adapter);

        function_2_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        function_2_Adapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<MineFrag.FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_mine_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, MineFrag.FuntionBean bean) {
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
                        LawBusinessUtils.jumpAction(getMContext(), FUNCTION_20_SZ, "");
                    }
                });
            }
        });
        function_2_rcy.setAdapter(function_2_Adapter);


        List<MineFrag.FuntionBean> funtion_1_Beans = new ArrayList<>();
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_16_HTXZ, getString(R.string.mine_function_tx1), R.mipmap.ic_mine_function_1));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_2_DXWS, getString(R.string.mine_function_tx2), R.mipmap.ic_mine_function_2));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_3_FLZX, getString(R.string.mine_function_tx3), R.mipmap.ic_mine_function_3));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_4_LSH, getString(R.string.mine_function_tx4), R.mipmap.ic_mine_function_4));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_5_HTDZ, getString(R.string.mine_function_tx5), R.mipmap.ic_mine_function_5));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_6_HTSH, getString(R.string.mine_function_tx6), R.mipmap.ic_mine_function_6));
        funtion_1_Beans.add(new MineFrag.FuntionBean(FUNCTION_17_AJWT, getString(R.string.mine_function_tx7), R.mipmap.ic_mine_function_7));
        function_1_Adapter.setData(funtion_1_Beans);


        List<MineFrag.FuntionBean> funtion_2_Beans = new ArrayList<>();
        funtion_2_Beans.add(new MineFrag.FuntionBean(FUNCTION_18_WDDD, getString(R.string.mine_function_tx8), R.mipmap.ic_mine_function_8));
        funtion_2_Beans.add(new MineFrag.FuntionBean(FUNCTION_12_DZQZ, getString(R.string.mine_function_tx9), R.mipmap.ic_mine_function_9));
        funtion_2_Beans.add(new MineFrag.FuntionBean(FUNCTION_19_TSJY, getString(R.string.mine_function_tx10), R.mipmap.ic_mine_function_10));
        funtion_2_Beans.add(new MineFrag.FuntionBean(FUNCTION_20_SZ, getString(R.string.mine_function_tx11), R.mipmap.ic_mine_function_11));
        function_2_Adapter.setData(funtion_2_Beans);

        refresh_layout.setEnableLoadMore(false);
    }

    @Override
    public void doBusiness() {
        try {
            FTokenUtils.getToken(getContext(), false);
            setLoginView();
        } catch (NeedLoginException e) {
            setUnLoginView();
        }

    }

    /**
     * @param
     * @return
     * @description 已登陆
     * @author jiejack
     * @time 2022/8/27 10:10 上午
     */
    private void setLoginView() {
        login_rl.setVisibility(View.VISIBLE);
        tologin_tv.setVisibility(View.GONE);

        FCacheUtils.getUserInfo(getMContext(), true, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                username_tv.setText(userInfoBean.nick_name);
                usertype_tv.setText(userInfoBean.user_type_tx);
                GlideUtils.loadImageUserLogoDefult(getMContext(),userInfoBean.avatar,userlogo_civ);
            }

            @Override
            public void fail() {

            }
        });


    }

    /**
     * @param
     * @return
     * @description 未登录
     * @author jiejack
     * @time 2022/8/27 10:10 上午
     */
    private void setUnLoginView() {
        login_rl.setVisibility(View.GONE);
        tologin_tv.setVisibility(View.VISIBLE);

        tologin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(LoginAct.class);
            }
        });
    }

    @Override
    public void onWakeBussiness() {

    }

    public static class FuntionBean extends BaseBean {
        public String name;
        public int functionid;
        public int iconRes;

        public FuntionBean(int functionid, String name, int iconRes) {
            this.name = name;
            this.functionid = functionid;
            this.iconRes = iconRes;
        }

    }

    @Override
    public Context getMContext() {
        return getContext();
    }
}
