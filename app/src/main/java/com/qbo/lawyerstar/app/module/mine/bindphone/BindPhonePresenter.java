package com.qbo.lawyerstar.app.module.mine.bindphone;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.qbo.lawyerstar.R;

import org.greenrobot.eventbus.EventBus;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WeChatUtils;

public class BindPhonePresenter extends BasePresent<IBindPhoneView, BaseModel> {


    public String wxCode;

    public void getPhoneCode(String mobile) {
        POST_SEND_CODE_REQ req = new POST_SEND_CODE_REQ();
        req.type = "bind";
        req.mobile = mobile;
        if (ToolUtils.isNull(mobile)) {
            T(R.string.login_tx3);
            return;
        }
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


    public void toBindPhone(String mobile,String code) {
        if (ToolUtils.isNull(wxCode)) {
            return;
        }
        if(ToolUtils.isNull(mobile)){
            T(R.string.login_tx3);
            return;
        }
        if(ToolUtils.isNull(code)){
            T(R.string.login_tx4);
            return;
        }
        WeChatUtils.GET_WX_BASE_REQ getWxBaseReq = new WeChatUtils.GET_WX_BASE_REQ();
        getWxBaseReq.appid = WeChatUtils.APP_ID;
        getWxBaseReq.secret = WeChatUtils.APP_SECRET;
        getWxBaseReq.code = wxCode;
        doCommRequest(getWxBaseReq, false, false, new BasePresent.DoCommRequestInterface<BaseResponse, String>() {
            @Override
            public void doStart() {

            }

            @Override
            public String doMap(BaseResponse baseResponse) {
                return baseResponse.orgin;
            }

            @Override
            public void onSuccess(String s) throws Exception {
                Log.i("WXEntryActivity", "wxlogin1:" + s);
                JSONObject json = JSONObject.parseObject(s);
                String access_token = json.getString("access_token");
                String openid = json.getString("openid");
                if (!ToolUtils.isNull(access_token) && !ToolUtils.isNull(openid)) {
                    WeChatUtils.GET_WX_USERINFO_REQ getWxUserinfoReq = new WeChatUtils.GET_WX_USERINFO_REQ();
                    getWxUserinfoReq.access_token = access_token;
                    getWxUserinfoReq.openid = openid;
                    doCommRequest(getWxUserinfoReq, false, false, new BasePresent.DoCommRequestInterface<BaseResponse, String>() {
                        @Override
                        public void doStart() {

                        }

                        @Override
                        public String doMap(BaseResponse baseResponse) {
                            return baseResponse.orgin;
                        }

                        @Override
                        public void onSuccess(String s) throws Exception {
                            Log.i("WXEntryActivity", "wxlogin2:" + s);
                            POST_BINDPHONE_REQ req = new POST_BINDPHONE_REQ();
                            req.info = s;
                            req.mobile = mobile;
                            req.code = code;
                            req.type = "1";
                            req.wechatCode = wxCode;
                            doCommRequest(req, false, false, new BasePresent.DoCommRequestInterface<BaseResponse, String>() {
                                @Override
                                public void doStart() {

                                }

                                @Override
                                public String doMap(BaseResponse baseResponse) {
                                    return baseResponse.datas;
                                }

                                @Override
                                public void onSuccess(String s) throws Exception {
                                    Log.i("WXEntryActivity", "wxlogin3:" + s);
                                    T("绑定成功");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    T("绑定失败：" + e.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable e) {
                            T("绑定失败：" + e.getMessage());
                        }
                    });
                } else {
                    T("绑定失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                T("绑定失败：" + e.getMessage());
            }
        });

    }

}
