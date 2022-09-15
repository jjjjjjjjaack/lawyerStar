package com.qbo.lawyerstar.app.module.mine.order.list.comm.frag;

import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.mine.lawask.comm.frag.ILawAskCommListView;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class OrderListCommListPresenter extends BasePresent<IOrderListCommListView, BaseModel> {

    GET_ORDER_LIST_REQ req = new GET_ORDER_LIST_REQ();
    public void getData(boolean isRefresh){
        if (isRefresh) {
            req.pageNo = 1;
            req.pageSize = 10;
        } else {
            req.pageNo++;
        }
        doCommRequest(req, false, true,
                new DoCommRequestInterface<RES_Factory.GET_ORDER_LIST_RES,
                        List<OrderListBean>>() {
                    @Override
                    public void doStart() {

                    }

                    @Override
                    public List<OrderListBean> doMap(RES_Factory.GET_ORDER_LIST_RES res) {
                        return res.rows;
                    }

                    @Override
                    public void onSuccess(List<OrderListBean> beans) throws Exception {
                        if (beans.size() == 0) {
                            req.pageNo--;
                        }
                        view().getDataResult(isRefresh,beans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        req.pageNo--;
                        view().getDataResult(isRefresh,null);
                    }
                });
    }

}
