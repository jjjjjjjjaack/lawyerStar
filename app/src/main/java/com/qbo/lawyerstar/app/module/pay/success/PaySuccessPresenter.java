package com.qbo.lawyerstar.app.module.pay.success;

import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.pay.bean.PayResultBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class PaySuccessPresenter extends BasePresent<IPaySuccessView, BaseModel> {


    FOrderPayBean orderPayBean;

    public void checkOrderPayStatus(){
        if(orderPayBean==null){
            return;
        }
        REQ_Factory.GET_ORDER_PAY_STATUS_REQ req = new REQ_Factory.GET_ORDER_PAY_STATUS_REQ();
        req.pay_type = orderPayBean.payType;
        req.sn = orderPayBean.sn;
        req.type = orderPayBean.orderType;
        doCommRequest(req, false, true, new BasePresent.DoCommRequestInterface<BaseResponse, PayResultBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public PayResultBean doMap(BaseResponse baseResponse) {
                return PayResultBean.fromJSONAuto(baseResponse.datas,PayResultBean.class);
            }

            @Override
            public void onSuccess(PayResultBean bean) throws Exception {
                view().showView(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
