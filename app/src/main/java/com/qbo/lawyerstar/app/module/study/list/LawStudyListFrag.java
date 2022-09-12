package com.qbo.lawyerstar.app.module.study.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.article.detail.ArticleDetailAct;
import com.qbo.lawyerstar.app.module.home.bean.HomeDataBean;
import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.GlideUtils;

public class LawStudyListFrag extends MvpFrag<ILawStudyListView, BaseModel, LawStudyListPresenter> implements ILawStudyListView {

    public static LawStudyListFrag newInstance(String category_id) {
        LawStudyListFrag frag = new LawStudyListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", category_id);
        frag.setArguments(bundle);
        return frag;
    }

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    public MCommAdapter mCommAdapter;

    @Override
    public LawStudyListPresenter initPresenter() {
        return new LawStudyListPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_law_study_list;
    }

    @Override
    public void viewInitialization() {
        rcy.setLayoutManager(new LinearLayoutManager(getMContext()));
        mCommAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<ArticleBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_lawstudy_news_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, ArticleBean bean) {
                GlideUtils.loadImageDefult(context, bean.getImage(), (ImageView) mCommVH.getView(R.id.newslogo_iv));
                mCommVH.setText(R.id.newsname_tv, bean.getTitle());
                mCommVH.setText(R.id.newsreadnum_tv, getMContext().getString(R.string.home_frag_tx4_3, bean.getReading()));
                mCommVH.setText(R.id.newsdate_tv, bean.getCreate_time());

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArticleDetailAct.openAct(context,bean.id);
                    }
                });
            }
        });
        mCommAdapter.setShowEmptyView(true);
        rcy.setAdapter(mCommAdapter);

        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getDataList(true);
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getDataList(false);
            }
        });
    }

    @Override
    public void doBusiness() {
        String category_id = getArguments().getString("category_id");
        if (!"0".equals(category_id)) {
            presenter.req.filter.is_rec =null;
            presenter.req.filter.article_clazz = category_id;
        } else {
            presenter.req.filter.is_rec = "1";
            presenter.req.filter.article_clazz =null;
        }
        presenter.getDataList(true);
    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void loadDataResult(boolean isRefresh, List<ArticleBean> articleBeans) {
        refresh_layout.finishRefresh();
        refresh_layout.finishLoadMore();
        if (isRefresh) {
            if (articleBeans != null) {
                mCommAdapter.setData(articleBeans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (articleBeans != null) {
                mCommAdapter.addData(articleBeans);
            }
        }
    }
}
