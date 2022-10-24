package com.qbo.lawyerstar.app.module.mine.vip.introv2.content;

import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class VipIntroV2ContentPresenter extends BasePresent<IVipIntroV2ContentView, BaseModel> {
    public VipIntroBean vipIntroBean;

    public VipIntroBean.YearPriceBean selectYearBean;
}
