package com.qbo.lawyerstar.app.module.mine.info.rename;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseRequest;
import framework.mvp1.base.net.BaseResponse;

public class UserReNamePresenter extends BasePresent<IUserReNameView, BaseModel> {


    public void editInfo(String name) {
        POST_EDIT_USERINFO_REQ req = new POST_EDIT_USERINFO_REQ();
        req.nick_name = name;
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
                view().editResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
