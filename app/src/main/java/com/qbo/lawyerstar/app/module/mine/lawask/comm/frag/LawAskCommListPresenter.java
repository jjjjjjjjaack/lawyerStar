package com.qbo.lawyerstar.app.module.mine.lawask.comm.frag;

import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class LawAskCommListPresenter extends BasePresent<ILawAskCommListView, BaseModel> {

    GET_ORDER_LIST_REQ req = new GET_ORDER_LIST_REQ();
    public void getData(boolean isRefresh,String searchKey){
        if (isRefresh) {
            req.pageNo = 1;
            req.pageSize = 10;
        } else {
            req.pageNo++;
        }
        doCommRequest(req, false, true,
                new DoCommRequestInterface<RES_Factory.GET_CONTRACT_LIBRARY_LIST_RES,
                        List<ContractLibBean>>() {
                    @Override
                    public void doStart() {

                    }

                    @Override
                    public List<ContractLibBean> doMap(RES_Factory.GET_CONTRACT_LIBRARY_LIST_RES res) {
                        return res.rows;
                    }

                    @Override
                    public void onSuccess(List<ContractLibBean> contractLibBeans) throws Exception {
                        if (contractLibBeans.size() == 0) {
                            req.pageNo--;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        req.pageNo--;
                    }
                });
    }

}
