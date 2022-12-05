package com.qbo.lawyerstar.app.module.lawyer.list;

import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;
import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class LawyerListPresenter extends BasePresent<ILawyerListView, BaseModel> {

    public GET_LAWYER_LIST_REQ req = new GET_LAWYER_LIST_REQ();
    public void getData(boolean isRefresh){
        if (isRefresh) {
            req.pageNo = 1;
            req.pageSize = 10;
        } else {
            req.pageNo++;
        }
        doCommRequest(req, false, true, new DoCommRequestInterface<RES_Factory.GET_LAWYER_LIST_RES, List<LawyerBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<LawyerBean> doMap(RES_Factory.GET_LAWYER_LIST_RES res) {
                return res.rows;
            }

            @Override
            public void onSuccess(List<LawyerBean> lawyerBeans) throws Exception {
                if (lawyerBeans.size() == 0) {
                    req.pageNo--;
                }
                view().loadDataResult(isRefresh, lawyerBeans);
            }

            @Override
            public void onError(Throwable e) {
                req.pageNo--;
                view().loadDataResult(isRefresh, null);
            }
        });
    }
}
