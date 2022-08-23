package com.qbo.lawyerstar.app.module.home.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.home.bean.HomeDataBean;
import com.qbo.lawyerstar.app.view.scrolltextview.MAutoScrollTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;

public class HomeFrag extends MvpFrag<IHomeView, BaseModel, HomePresenter> implements IHomeView {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.deviceinfo_tv)
    MAutoScrollTextView deviceinfo_tv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.function_rcy)
    RecyclerView function_rcy;
    private MCommAdapter functionAdapter;

    @BindView(R.id.lawyer_rcv)
    RecyclerView lawyer_rcv;
    private MCommAdapter laywerAdapter;

    @BindView(R.id.news_rcv)
    RecyclerView news_rcv;
    private MCommAdapter newsAdapter;

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_home;
    }

    @Override
    public void viewInitialization() {
        function_rcy.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        functionAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<FuntionBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_function_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, FuntionBean bean) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position < 4) {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 12);
                    layoutParams.bottomMargin = 0;
                } else if (position >= 4 && position < 8) {
                    layoutParams.topMargin = ResourceUtils.dp2px(context, 15);
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
        function_rcy.setAdapter(functionAdapter);

        lawyer_rcv.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.HORIZONTAL, false));
        laywerAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<HomeDataBean.LawyerListBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_lawyer_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, HomeDataBean.LawyerListBean bean) {
                GlideUtils.loadImageUserLogoDefult(context, bean.getAvatar(), (ImageView) mCommVH.getView(R.id.logo_iv));
                mCommVH.setText(R.id.lawyer_name_tv, bean.getReal_name());
                mCommVH.setText(R.id.lawyer_year_tv, getMContext().getString(R.string.home_frag_tx3_3, bean.getEmployment_year()));
                mCommVH.setText(R.id.desc_tv, bean.expertiseString);
            }
        });
        lawyer_rcv.setAdapter(laywerAdapter);

        news_rcv.setLayoutManager(new LinearLayoutManager(getMContext()));
        newsAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<HomeDataBean.ArticleListBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_home_news_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, HomeDataBean.ArticleListBean bean) {
                GlideUtils.loadImageDefult(context, bean.getImage(), (ImageView) mCommVH.getView(R.id.newslogo_iv));
                mCommVH.setText(R.id.newsname_tv, bean.getTitle());
                mCommVH.setText(R.id.newsreadnum_tv, getMContext().getString(R.string.home_frag_tx4_3, bean.getReading()));
                mCommVH.setText(R.id.newsdate_tv, bean.getCreate_time());
            }
        });
        news_rcv.setAdapter(newsAdapter);

        List<FuntionBean> funtionBeans = new ArrayList<>();
        funtionBeans.add(new FuntionBean(0, getString(R.string.home_frag_function_tx1), R.mipmap.ic_h_function_1));
        funtionBeans.add(new FuntionBean(1, getString(R.string.home_frag_function_tx2), R.mipmap.ic_h_function_2));
        funtionBeans.add(new FuntionBean(2, getString(R.string.home_frag_function_tx3), R.mipmap.ic_h_function_3));
        funtionBeans.add(new FuntionBean(3, getString(R.string.home_frag_function_tx4), R.mipmap.ic_h_function_4));
        funtionBeans.add(new FuntionBean(4, getString(R.string.home_frag_function_tx5), R.mipmap.ic_h_function_5));
        funtionBeans.add(new FuntionBean(5, getString(R.string.home_frag_function_tx6), R.mipmap.ic_h_function_6));
        funtionBeans.add(new FuntionBean(6, getString(R.string.home_frag_function_tx7), R.mipmap.ic_h_function_7));
        funtionBeans.add(new FuntionBean(7, getString(R.string.home_frag_function_tx8), R.mipmap.ic_h_function_8));
        funtionBeans.add(new FuntionBean(8, getString(R.string.home_frag_function_tx9), R.mipmap.ic_h_function_9));
        funtionBeans.add(new FuntionBean(9, getString(R.string.home_frag_function_tx10), R.mipmap.ic_h_function_10));
        funtionBeans.add(new FuntionBean(10, getString(R.string.home_frag_function_tx11), R.mipmap.ic_h_function_11));
        funtionBeans.add(new FuntionBean(11, getString(R.string.home_frag_function_tx12), R.mipmap.ic_h_function_12));

        functionAdapter.setData(funtionBeans);

        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getData();
            }
        });
        refresh_layout.setEnableLoadMore(false);
    }

    @Override
    public void doBusiness() {
        presenter.getData();
    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void getDataResult(HomeDataBean homeDataBean) {
        refresh_layout.finishRefresh();
        if (homeDataBean != null) {
            try {
                if (homeDataBean.getAnnouncement() == null || homeDataBean.getAnnouncement().size() == 0) {
                    homeDataBean.setAnnouncement(Arrays.asList("暂无信息"));
                }
                if (homeDataBean.getAnnouncement().size() == 1) {
                    deviceinfo_tv.stopAutoScroll();
                    deviceinfo_tv.getCurrentView().setText(homeDataBean.getAnnouncement().get(0));
                } else {
                    deviceinfo_tv.stopAutoScroll();
                    deviceinfo_tv.setTextList(homeDataBean.getAnnouncement());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            deviceinfo_tv.startAutoScroll();
                        }
                    }, 1000);
//            deviceinfo_tv.setText(ToolUtils.isNull(datas) ? "暂无设备信息" : datas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            banner.setAdapter(new BannerImageAdapter<HomeDataBean.BannerBean>(homeDataBean.getBanner()) {
                @Override
                public void onBindView(BannerImageHolder holder, HomeDataBean.BannerBean data, int position, int size) {
//                    //图片加载自己实现
//                    Glide.with(holder.itemView)
//                            .load(data.getUrl())
//                            .placeholder(R.mipmap.ic_noimage2)
//                            .error(R.mipmap.ic_noimage2)
//                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                            .into(holder.imageView);
                    GlideUtils.loadImageDefult(getMContext(),data.getUrl(),holder.imageView);
                }
            }).addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(getMContext()));

            laywerAdapter.setData(homeDataBean.getLawyer_list());

            newsAdapter.setData(homeDataBean.getArticle_list());
        }
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
}
