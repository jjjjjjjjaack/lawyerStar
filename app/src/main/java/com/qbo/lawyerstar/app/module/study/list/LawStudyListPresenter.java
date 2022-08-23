package com.qbo.lawyerstar.app.module.study.list;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class LawStudyListPresenter extends BasePresent<ILawStudyListView, BaseModel> {

    GET_LAWSTUDY_LIST_DATA_REQ req = new GET_LAWSTUDY_LIST_DATA_REQ();

    public void getDataList(boolean isRefresh) {
        if (isRefresh) {
            req.pageNo = 1;
            req.pageSize = 10;
        } else {
            req.pageNo++;
        }
        doCommRequest(req, false, true, new DoCommRequestInterface<RES_Factory.GET_LAWSTUDY_LIST_DATA_RES, List<ArticleBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<ArticleBean> doMap(RES_Factory.GET_LAWSTUDY_LIST_DATA_RES res) {
                return res.rows;
            }

            @Override
            public void onSuccess(List<ArticleBean> articleBeans) throws Exception {
                if (articleBeans.size() == 0) {
                    req.pageNo--;
                }
                view().loadDataResult(isRefresh, articleBeans);
            }

            @Override
            public void onError(Throwable e) {
                req.pageNo--;
                view().loadDataResult(isRefresh, null);
            }
        });
    }

}
