package com.qbo.lawyerstar.app.module.mine.login.base;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.FTokenUtils;

public class LoginPresenter extends BasePresent<ILoginView, BaseModel> {

    public void getPhoneCode(String mobile) {
        POST_SEND_CODE_REQ req = new POST_SEND_CODE_REQ();
        req.type = "reg";
        req.mobile = mobile;
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
                view().getCodeResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * @param
     * @return
     * @description
     * @author jiejack
     * @time 2022/8/27 3:58 下午
     */
    public void toLogin(String mobile, String code, String type) {
        POST_LOGIN_REQ req = new POST_LOGIN_REQ();
        req.account = mobile;
        req.type = type;
        req.code = code;
        req.wechatCode = "";
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, String>() {
            @Override
            public void doStart() {

            }

            @Override
            public String doMap(BaseResponse baseResponse) {
                FToken token = FToken.fromJSONAuto(baseResponse.datas, FToken.class);
                FTokenUtils.saveToken(token);
                return baseResponse.msg;
            }

            @Override
            public void onSuccess(String s) throws Exception {
                T(s);
                view().loginResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
