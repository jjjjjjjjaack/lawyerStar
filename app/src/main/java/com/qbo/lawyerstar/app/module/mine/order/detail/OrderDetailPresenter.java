package com.qbo.lawyerstar.app.module.mine.order.detail;

import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class OrderDetailPresenter extends BasePresent<IOrderDetailView, BaseModel> {

    OrderListBean orderDetailBean;

    GET_ORDER_DETAIL_REQ req = new GET_ORDER_DETAIL_REQ();

    public void getInfo() {
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, OrderListBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public OrderListBean doMap(BaseResponse baseResponse) {
                OrderListBean bean = OrderListBean.fromJSONAuto(baseResponse.datas, OrderListBean.class);
                return bean;
            }

            @Override
            public void onSuccess(OrderListBean orderDetailBean) throws Exception {
                view().setInfo(orderDetailBean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
