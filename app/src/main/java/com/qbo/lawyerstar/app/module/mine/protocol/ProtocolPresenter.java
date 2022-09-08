package com.qbo.lawyerstar.app.module.mine.protocol;

import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class ProtocolPresenter extends BasePresent<IProtocolView, BaseModel> {

    public void getProtocol(String type) {
        GET_PROTOCOL_INFO_REQ req = new GET_PROTOCOL_INFO_REQ();
        req.type = type;
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, ProtocolBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public ProtocolBean doMap(BaseResponse baseResponse) {
                ProtocolBean bean = ProtocolBean.fromJSONAuto(baseResponse.datas,ProtocolBean.class);
                return bean;
            }

            @Override
            public void onSuccess(ProtocolBean protocolBean) throws Exception {
                view().showProtocol(protocolBean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
