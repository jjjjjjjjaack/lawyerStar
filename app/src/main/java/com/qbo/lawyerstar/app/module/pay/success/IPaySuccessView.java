package com.qbo.lawyerstar.app.module.pay.success;

import com.qbo.lawyerstar.app.module.pay.bean.PayResultBean;

import framework.mvp1.base.f.BaseView;

public interface IPaySuccessView extends BaseView {
    void showView(PayResultBean bean);
}
