package com.qbo.lawyerstar.app.module.mine.auth.lawyer;

import com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean.LawyerAuthBean;

import framework.mvp1.base.f.BaseView;

public interface ILawyerAuthView extends BaseView {
    void authResult(boolean b);

    void showInfo(LawyerAuthBean bean);
}
