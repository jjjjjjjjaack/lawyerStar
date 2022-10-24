package com.qbo.lawyerstar.app.module.mine.vip.introv2.base;

import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface IVipIntroV2View extends BaseView {
    void showInfo(List<VipIntroBean> beanList);
}
