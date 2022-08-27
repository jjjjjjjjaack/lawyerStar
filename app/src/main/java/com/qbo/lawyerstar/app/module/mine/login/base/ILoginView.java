package com.qbo.lawyerstar.app.module.mine.login.base;

import framework.mvp1.base.f.BaseView;

public interface ILoginView extends BaseView {
    void getCodeResult(boolean b);

    /**
     * @param type 0 成功 1 需要认证
     * @return
     * @description
     * @author jiejack
     * @time 2022/8/27 10:24 下午
     */
    void loginResult(int type);
}
