package com.qbo.lawyerstar.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.qbo.lawyerstar.app.utils.CEventUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import framework.mvp1.base.util.T;
import framework.mvp1.base.util.WeChatUtils;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private String TAG = WXPayEntryActivity.class.getSimpleName();
    private IWXAPI wxAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxAPI = WXAPIFactory.createWXAPI(this, WeChatUtils.APP_ID);
        wxAPI.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wxAPI.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq:" + baseReq);
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "微信支付回调 返回错误码:" + resp.errCode + " 错误名称:" + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                finish();
            } else if (resp.errCode == -2) {
                T.showShort(this, "您已取消付款!");
                EventBus.getDefault().post(new CEventUtils.WechatPayEevent(-1));
                finish();
            } else {
                T.showShort(this, "支付参数错误");
                EventBus.getDefault().post(new CEventUtils.WechatPayEevent(-2));
                finish();
            }
        } else {
            finish();
        }
    }
}