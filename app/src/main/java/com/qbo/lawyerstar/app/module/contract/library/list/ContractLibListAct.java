package com.qbo.lawyerstar.app.module.contract.library.list;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.detail.ContractLibDetailAct;
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
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ResourceUtils;

public class ContractLibListAct extends MvpAct<IContractLibListView, BaseModel,
        ContractLibListPresenter> implements IContractLibListView {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    MCommAdapter mCommAdapter;


    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_contract_library_list;
    }

    @Override
    public void viewInitialization() {
        setBackPress();


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(false);
            }
        });

        rcy.setLayoutManager(new GridLayoutManager(this, 2));
        mCommAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<ContractLibBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_contract_lib_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, ContractLibBean bean) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position % 2 == 0) {
                    lp.leftMargin = ResourceUtils.dip2px2(context, 12);
                    lp.rightMargin = ResourceUtils.dip2px2(context, 5.5f);
                } else {
                    lp.rightMargin = ResourceUtils.dip2px2(context, 12);
                    lp.leftMargin = ResourceUtils.dip2px2(context, 5.5f);
                }
                lp.topMargin = ResourceUtils.dip2px2(context, 14);
                mCommVH.loadImageResourceByGilde(R.id.logo_iv, bean.cover_img);
                mCommVH.setText(R.id.title_tv, bean.title);
                mCommVH.setText(R.id.desc_tv, bean.sub_title);
                mCommVH.setText(R.id.price_tv, bean.price + "元");

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContractLibDetailAct.openAct(context,bean);
                    }
                });
            }
        });
        rcy.setAdapter(mCommAdapter);
    }

    @Override
    public void doBusiness() {
        presenter.getData(true);
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public ContractLibListPresenter initPresenter() {
        return new ContractLibListPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void loadDataResult(boolean isRefresh, List<ContractLibBean> contractLibBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (isRefresh) {
            if (contractLibBeans != null) {
                mCommAdapter.setData(contractLibBeans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (contractLibBeans != null) {
                mCommAdapter.addData(contractLibBeans);
            }
        }
    }
}
