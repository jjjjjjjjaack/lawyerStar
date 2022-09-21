package com.qbo.lawyerstar.app.module.mine.order.list.all;

import com.qbo.lawyerstar.app.module.mine.order.bean.OrderTypeBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface IAllOrderListView extends BaseView {
    void getTypeResult(List<OrderTypeBean> orderTypeBeanList);
}
