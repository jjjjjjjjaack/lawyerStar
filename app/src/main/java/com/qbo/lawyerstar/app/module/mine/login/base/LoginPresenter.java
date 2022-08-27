package com.qbo.lawyerstar.app.module.mine.login.base;

import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.ToolUtils;

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
                getUserInfo();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * @param
     * @return
     * @description 获取用户信息
     * @author jiejack
     * @time 2022/8/27 10:13 下午
     */
    public void getUserInfo() {
        GET_USERINFO_REQ req = new GET_USERINFO_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, FUserInfoBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public FUserInfoBean doMap(BaseResponse baseResponse) {
                return FUserInfoBean.fromJSONAuto(baseResponse.datas, FUserInfoBean.class);
            }

            @Override
            public void onSuccess(FUserInfoBean fUserInfoBean) throws Exception {
                if (fUserInfoBean == null) {
                    T("获取用户信息失败,请重新登录");
                } else {
                    FCacheUtils.saveUserInfo(context(), fUserInfoBean);
                    if (ToolUtils.String2Boolean(fUserInfoBean.is_rz)) {
                        view().loginResult(0);
                    } else {
                        view().loginResult(1);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
