package com.qbo.lawyerstar.app.module.mine.order.list.comm.frag;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;
import com.qbo.lawyerstar.app.module.mine.order.detail.OrderDetailAct;
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

public class OrderListCommListFrag extends MvpFrag<IOrderListCommListView, BaseModel, OrderListCommListPresenter> implements IOrderListCommListView {

    public static OrderListCommListFrag newInstance(String orderType, String orderStatus) {
        OrderListCommListFrag frag = new OrderListCommListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("orderType", orderType);
        bundle.putString("orderStatus", orderStatus);
        frag.setArguments(bundle);
        return frag;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;

    public MCommAdapter mCommAdapter;

    @Override
    public OrderListCommListPresenter initPresenter() {
        return new OrderListCommListPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_order_list_commlist;
    }

    @Override
    public void viewInitialization() {
        rcy.setLayoutManager(new LinearLayoutManager(getMContext()));
        mCommAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<OrderListBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_order_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, OrderListBean bean) {
                mCommVH.setText(R.id.name_tv, bean.getTitle());
                mCommVH.setTextCheckEmpty(R.id.tag_tv, bean.getType_text());
                mCommVH.setText(R.id.status_tv, bean.getStatus_text());
                mCommVH.setText(R.id.content_tv, bean.getContent());
                mCommVH.setText(R.id.lawyer_name_tv, getString(R.string.law_ask_comm_tx3, bean.getLawyerDetail().getReal_name()));
                mCommVH.setText(R.id.price_tv, getString(R.string.law_ask_comm_tx4, bean.getPrice()));
                mCommVH.setText(R.id.time_tv, getString(R.string.law_ask_comm_tx5,bean.getCreate_time()));

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDetailAct.openAct(context, bean.getId(), bean.getType());
                    }
                });
            }
        });
        rcy.setAdapter(mCommAdapter);
        mCommAdapter.setShowEmptyView(true);

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
    }

    @Override
    public void doBusiness() {
        String orderType = getArguments().getString("orderType");
        String orderStatus = getArguments().getString("orderStatus");
        presenter.req.type = orderType;
        presenter.req.status = orderStatus;
        presenter.getData(true);
    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void getDataResult(boolean isRefresh, List<OrderListBean> beans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (isRefresh) {
            if (beans != null) {
                mCommAdapter.setData(beans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (beans != null) {
                mCommAdapter.addData(beans);
            }
        }
    }
}
