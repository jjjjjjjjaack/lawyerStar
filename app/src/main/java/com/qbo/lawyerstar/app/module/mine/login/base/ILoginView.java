package com.qbo.lawyerstar.app.module.mine.login.base;

import framework.mvp1.base.f.BaseView;

public interface ILoginView extends BaseView {
    void getCodeResult(boolean b);

    void loginResult(boolean b);
}
