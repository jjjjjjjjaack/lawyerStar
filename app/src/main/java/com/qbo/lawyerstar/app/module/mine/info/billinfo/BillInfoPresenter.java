package com.qbo.lawyerstar.app.module.mine.info.billinfo;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class BillInfoPresenter extends BasePresent<IBillInfoView, BaseModel> {

    public void doChangeBillInfo(String name, String tax) {
        POST_CHANGE_USER_BILLINFO_REQ req = new POST_CHANGE_USER_BILLINFO_REQ();
        req.name = name;
        req.tax_num = tax;
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
                view().changeResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
