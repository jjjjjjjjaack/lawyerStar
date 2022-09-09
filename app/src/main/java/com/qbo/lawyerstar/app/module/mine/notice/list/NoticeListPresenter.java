package com.qbo.lawyerstar.app.module.mine.notice.list;

import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;
import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeTypeBean;
import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class NoticeListPresenter extends BasePresent<INoticeListView, BaseModel> {

    GET_NOTICE_LIST_REQ req = new GET_NOTICE_LIST_REQ();

    public void getDataList(boolean isRefresh) {
        if (isRefresh) {
            req.pageNo = 1;
            req.pageSize = 10;
        } else {
            req.pageNo++;
        }
        doCommRequest(req, false, true, new DoCommRequestInterface<RES_Factory.GET_NOTICE_LIST_RES, List<NoticeBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<NoticeBean> doMap(RES_Factory.GET_NOTICE_LIST_RES res) {
                return res.rows;
            }

            @Override
            public void onSuccess(List<NoticeBean> noticeBeans) throws Exception {
                if (noticeBeans.size() == 0) {
                    req.pageNo--;
                }
                view().loadDataResult(isRefresh, noticeBeans);
            }

            @Override
            public void onError(Throwable e) {
                req.pageNo--;
                view().loadDataResult(isRefresh, null);
            }
        });
    }

}
