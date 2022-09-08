package com.qbo.lawyerstar.app.module.mine.account.cancle;

import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class CancelAccountPresenter extends BasePresent<ICancelAccountView, BaseModel> {

    public void getProtocol(){
        GET_PROTOCOL_INFO_REQ req = new GET_PROTOCOL_INFO_REQ();
        req.type = "cancel";
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
            public void onSuccess(ProtocolBean bean) throws Exception {
                view().showProtocol(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * @description
     * @param
     * @return
     * @author jieja
     * @time 2022/9/8 14:52
     */
    public void doCancelAccount(String code){
        POST_DO_CACNEL_ACCOUNT_REQ req = new POST_DO_CACNEL_ACCOUNT_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, BaseResponse>() {
            @Override
            public void doStart() {

            }

            @Override
            public BaseResponse doMap(BaseResponse baseResponse) {
                return baseResponse;
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) throws Exception {
               T(baseResponse.msg);
               view().cancleResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
