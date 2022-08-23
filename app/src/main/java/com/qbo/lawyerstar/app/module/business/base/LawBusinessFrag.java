package com.qbo.lawyerstar.app.module.business.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.home.base.HomeFrag;
import com.qbo.lawyerstar.app.module.study.base.ILawStudyView;
import com.qbo.lawyerstar.app.module.study.base.LawStudyPresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
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
            }
        });
        function_3_rcy.setAdapter(function_3_Adapter);


        List<HomeFrag.FuntionBean> funtion_1_Beans = new ArrayList<>();
        funtion_1_Beans.add(new HomeFrag.FuntionBean(0, getString(R.string.home_frag_function_tx1), R.mipmap.ic_h_function_1));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(1, getString(R.string.home_frag_function_tx2), R.mipmap.ic_h_function_2));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(3, getString(R.string.home_frag_function_tx4), R.mipmap.ic_h_function_4));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(4, getString(R.string.home_frag_function_tx5), R.mipmap.ic_h_function_5));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(5, getString(R.string.home_frag_function_tx6), R.mipmap.ic_h_function_6));
        funtion_1_Beans.add(new HomeFrag.FuntionBean(11, getString(R.string.home_frag_function_tx12), R.mipmap.ic_h_function_12));
        function_1_Adapter.setData(funtion_1_Beans);

        List<HomeFrag.FuntionBean> funtion_2_Beans = new ArrayList<>();
        funtion_2_Beans.add(new HomeFrag.FuntionBean(12, getString(R.string.home_frag_function_tx13), R.mipmap.ic_h_function_13));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(13, getString(R.string.home_frag_function_tx14), R.mipmap.ic_h_function_14));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(14, getString(R.string.home_frag_function_tx15), R.mipmap.ic_h_function_15));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(15, getString(R.string.home_frag_function_tx16), R.mipmap.ic_h_function_16));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(6, getString(R.string.home_frag_function_tx7), R.mipmap.ic_h_function_7));
        funtion_2_Beans.add(new HomeFrag.FuntionBean(7, getString(R.string.home_frag_function_tx8), R.mipmap.ic_h_function_8));
        function_2_Adapter.setData(funtion_2_Beans);

        List<HomeFrag.FuntionBean> funtion_3_Beans = new ArrayList<>();
        funtion_3_Beans.add(new HomeFrag.FuntionBean(8, getString(R.string.home_frag_function_tx9), R.mipmap.ic_h_function_9));
        funtion_3_Beans.add(new HomeFrag.FuntionBean(9, getString(R.string.home_frag_function_tx10), R.mipmap.ic_h_function_10));
        funtion_3_Beans.add(new HomeFrag.FuntionBean(10, getString(R.string.home_frag_function_tx11), R.mipmap.ic_h_function_11));
        function_3_Adapter.setData(funtion_3_Beans);

        refresh_layout.setEnableRefresh(false);
        refresh_layout.setEnableLoadMore(false);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }
}
