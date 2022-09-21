package com.qbo.lawyerstar.app.module.mine.order.list.all;

import com.qbo.lawyerstar.app.module.mine.order.bean.OrderTypeBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class AllOrderListPresenter extends BasePresent<IAllOrderListView, BaseModel> {


    public void getOrderType() {
        GET_ORDER_ALL_TYPE_REQ req = new GET_ORDER_ALL_TYPE_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, List<OrderTypeBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<OrderTypeBean> doMap(BaseResponse baseResponse) {
                List<OrderTypeBean> rows = (List<OrderTypeBean>) OrderTypeBean.fromJSONListAuto(baseResponse.datas,OrderTypeBean.class);
                return rows;
            }

            @Override
            public void onSuccess(List<OrderTypeBean> orderTypeBeanList) throws Exception {
                view().getTypeResult(orderTypeBeanList);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
