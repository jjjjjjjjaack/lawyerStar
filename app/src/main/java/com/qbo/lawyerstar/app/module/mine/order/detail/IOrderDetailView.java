package com.qbo.lawyerstar.app.module.mine.order.detail;


import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;

import framework.mvp1.base.f.BaseView;

public interface IOrderDetailView extends BaseView {
    void setInfo(OrderListBean orderDetailBean);

    void cancleResult(boolean b);
}
