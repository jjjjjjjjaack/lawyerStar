package com.qbo.lawyerstar.app.module.lawyer.detail;

import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class LawyerDetailPresenter extends BasePresent<ILawyerDetailView, BaseModel> {

    LawyerBean lawyerBean;


    public void getDetail(String lawyerid) {
        GET_LAWYER_DETAIL_INFO_REQ req = new GET_LAWYER_DETAIL_INFO_REQ();
        req.id = lawyerid;
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, LawyerBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public LawyerBean doMap(BaseResponse baseResponse) {
                LawyerBean bean = LawyerBean.fromJSONAuto(baseResponse.datas, LawyerBean.class);
                return bean;
            }

            @Override
            public void onSuccess(LawyerBean bean) throws Exception {
                lawyerBean = bean;
                view().showInfo(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
