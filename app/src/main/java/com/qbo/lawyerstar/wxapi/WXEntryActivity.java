package com.qbo.lawyerstar.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.fastjson.JSONObject;
import com.qbo.lawyerstar.app.MyApplication;
import com.qbo.lawyerstar.app.module.main.VpMainAct;
import com.qbo.lawyerstar.app.module.mine.bindphone.BindPhoneAct;
import com.qbo.lawyerstar.app.net.REQ_Factory;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.exception.NetException;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.net.NET_CODE;
import framework.mvp1.base.util.FTokenUtils;

import static framework.mvp1.base.net.NET_CODE.C_501;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.modelmsg.SendAuth;

public class WXEntryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("WXEntryActivity", "onCreate");
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("WXEntryActivity", "onNewIntent");
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        if (resp != null) {
            Log.i(MyApplication.JTAG, "Wechat:" + JSONObject.toJSONString(resp));
//            Log.i("WXEntryActivity", "handleIntent.errCode: " + resp.errCode);
//            Log.i("WXEntryActivity", "handleIntent.errStr: " + resp.errStr);
//            Log.i("WXEntryActivity", "handleIntent.openId: " + resp.openId);
//            Log.i("WXEntryActivity", "handleIntent.code: " + resp.code);
//            Log.i("WXEntryActivity", "handleIntent.transaction：" + resp.transaction);
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(resp.transaction);
                    int actionType = jsonObject.getIntValue("actionType");
                    switch (actionType) {
                        case 0://登陆
                            REQ_Factory.POST_LOGIN_REQ req = new REQ_Factory.POST_LOGIN_REQ();
                            req.type = "1";
                            req.wechatCode = resp.code;
                            BasePresent.doStaticCommRequest(WXEntryActivity.this,req,
                                    true, true, new BasePresent.DoCommRequestInterface<BaseResponse, FToken>() {
                                @Override
                                public void doStart() {

                                }

                                @Override
                                public FToken doMap(BaseResponse baseResponse) {
                                    FToken token = FToken.fromJSONAuto(baseResponse.datas, FToken.class);
                                    FTokenUtils.saveToken(token);
                                    return token;
                                }

                                @Override
                                public void onSuccess(FToken s) throws Exception {
                                    if (s.isIs_rz()) {
                                        VpMainAct.openMainAct(WXEntryActivity.this);
                                    } else {
                                        VpMainAct.openMainAct(WXEntryActivity.this);
                                    }
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (e instanceof NetException) {
                                        NetException netException = (NetException) e;
                                        switch (netException.netCode) {
                                            case NET_CODE.C_405:
                                                BindPhoneAct.openAct(WXEntryActivity.this,resp.code);
                                                break;
                                        }
                                    }

                                    finish();
                                }
                            });
                            return;
                        case 1:
                            return;
                    }
                } catch (Exception e) {
                }
            }
        }
        finish();
    }
}
