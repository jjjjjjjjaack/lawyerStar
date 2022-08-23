package com.qbo.lawyerstar.app.module.home.base;

import com.qbo.lawyerstar.app.module.home.bean.HomeDataBean;

import framework.mvp1.base.f.BaseView;

public interface IHomeView extends BaseView {
    void getDataResult(HomeDataBean homeDataBean);
}
