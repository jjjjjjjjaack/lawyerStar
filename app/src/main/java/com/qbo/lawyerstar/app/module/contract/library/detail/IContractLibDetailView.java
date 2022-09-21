package com.qbo.lawyerstar.app.module.contract.library.detail;

import com.qbo.lawyerstar.app.bean.FOrderPayBean;

import framework.mvp1.base.f.BaseView;

public interface IContractLibDetailView extends BaseView {
    void createSuccess(FOrderPayBean bean);
}
