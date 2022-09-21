package com.qbo.lawyerstar.app.module.mine.vip.intro;

import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface IVipIntroView extends BaseView {
    void showInfo(List<VipIntroBean> beanList);
}
