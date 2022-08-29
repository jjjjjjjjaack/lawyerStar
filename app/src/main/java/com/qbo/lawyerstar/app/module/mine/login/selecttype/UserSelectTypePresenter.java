package com.qbo.lawyerstar.app.module.mine.login.selecttype;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class UserSelectTypePresenter extends BasePresent<IUserSelectTypeView, BaseModel> {

    public String type;

    public void changeType() {
        if (ToolUtils.isNull(type)) {
            return;
        }
        POST_CHANGEUSER_TYPE_REQ req = new POST_CHANGEUSER_TYPE_REQ();
        req.type = type;
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
         view().changeTypeSuccess();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
