package com.qbo.lawyerstar.app.module.mine.base;

import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeTypeBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class MinePresenter extends BasePresent<IMineView, BaseModel> {

    public NoticeTypeBean typeBean;

    public void getCount() {
        GET_NOTICE_MSGCOUNT_REQ req = new GET_NOTICE_MSGCOUNT_REQ();
        doCommRequest(req, false, false, new DoCommRequestInterface<BaseResponse, NoticeTypeBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public NoticeTypeBean doMap(BaseResponse baseResponse) {
                NoticeTypeBean bean = NoticeTypeBean.fromJSONAuto(baseResponse.datas, NoticeTypeBean.class);
                return bean;
            }

            @Override
            public void onSuccess(NoticeTypeBean notice) throws Exception {
                typeBean = notice;
                view().getMsgNumResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
