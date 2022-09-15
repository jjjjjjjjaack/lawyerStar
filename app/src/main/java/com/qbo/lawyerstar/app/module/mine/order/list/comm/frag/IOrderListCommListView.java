package com.qbo.lawyerstar.app.module.mine.order.list.comm.frag;

import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface IOrderListCommListView extends BaseView {
    void getDataResult(boolean isRefresh, List<OrderListBean> beans);
}
