package com.qbo.lawyerstar.app.module.mine.protocol;

import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;

import framework.mvp1.base.f.BaseView;

public interface IProtocolView extends BaseView {
    void showProtocol(ProtocolBean protocolBean);
}
