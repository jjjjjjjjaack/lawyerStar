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

    public void doCancleOrder() {
        POST_CANCLE_ORDER_REQ req = new POST_CANCLE_ORDER_REQ();
        req.sn =   orderDetailBean.getSn();
        req.type = orderDetailBean.getType();
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
