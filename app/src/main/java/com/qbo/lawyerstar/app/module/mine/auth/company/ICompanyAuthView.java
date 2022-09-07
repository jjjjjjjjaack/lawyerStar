package com.qbo.lawyerstar.app.module.mine.auth.company;

import com.qbo.lawyerstar.app.module.mine.auth.company.bean.CompanyAuthBean;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean.LawyerAuthBean;

import framework.mvp1.base.f.BaseView;
import framework.mvp1.base.f.MvpAct;

public interface ICompanyAuthView extends BaseView {
    void authResult(boolean b);

    void showInfo(CompanyAuthBean bean);
}
