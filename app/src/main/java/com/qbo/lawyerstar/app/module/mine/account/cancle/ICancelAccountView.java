package com.qbo.lawyerstar.app.module.mine.account.cancle;

import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;

import framework.mvp1.base.f.BaseView;

public interface ICancelAccountView extends BaseView {
    void cancleResult(boolean b);

    void showProtocol(ProtocolBean bean);
}
