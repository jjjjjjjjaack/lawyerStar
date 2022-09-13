package com.qbo.lawyerstar.app.module.mine.about;

import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;

import framework.mvp1.base.f.BaseView;

public interface IAboutUsView extends BaseView {
    void showInfo(AboutUsBean bean);
}
