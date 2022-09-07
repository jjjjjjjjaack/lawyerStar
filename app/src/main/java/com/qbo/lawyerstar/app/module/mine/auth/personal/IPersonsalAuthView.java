package com.qbo.lawyerstar.app.module.mine.auth.personal;

import com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean.LawyerAuthBean;
import com.qbo.lawyerstar.app.module.mine.auth.personal.bean.PersonalAuthBean;

import framework.mvp1.base.f.BaseView;

public interface IPersonsalAuthView extends BaseView {
    void authResult(boolean b);

    void showInfo(PersonalAuthBean bean);
}
