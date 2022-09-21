package com.qbo.lawyerstar.app.module.contract.library.detail;

import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.list.IContractLibListView;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class ContractLibDetailPresenter extends BasePresent<IContractLibDetailView, BaseModel> {

    ContractLibBean bean;


    public void createOrder() {
        POST_ORDER_CONTRACT_DOC_CREATE_REQ req = new POST_ORDER_CONTRACT_DOC_CREATE_REQ();
        req.contract_id = bean.id;
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, FOrderPayBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public FOrderPayBean doMap(BaseResponse baseResponse) {
                FOrderPayBean bean = FOrderPayBean.fromJSONAuto(baseResponse.datas,FOrderPayBean.class);
                return bean;
            }

            @Override
            public void onSuccess(FOrderPayBean bean) throws Exception {
//                T(baseResponse.msg);
                view().createSuccess(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
