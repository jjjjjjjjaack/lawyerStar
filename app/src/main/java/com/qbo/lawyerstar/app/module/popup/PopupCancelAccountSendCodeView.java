package com.qbo.lawyerstar.app.module.popup;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import butterknife.BindView;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.CountDownMsgUtils;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupCancelAccountSendCodeView extends PopupBaseView {

    TextView getcode_tv;
    TextView confirm_tv;
    TextView dismiss_tv;
    EditText code_et;

    CountDownMsgUtils countDownMsgUtils;

    public interface PopupCancelInerface {

        void onConfirm(String code);
    }

    public PopupCancelInerface onCancelInerface;

    public PopupCancelAccountSendCodeView(Context context, PopupCancelInerface onCancelInerface) {
        super(context, ResourceUtils.getWindowsWidth((Activity) context) - 2 * ResourceUtils.dp2px(context, 48), 0);
        this.onCancelInerface = onCancelInerface;
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_cancleaccount_sendcode_view;
    }

    @Override
    public void initPopupView() {
        getcode_tv = popView.findViewById(R.id.getcode_tv);
        confirm_tv = popView.findViewById(R.id.confirm_tv);
        dismiss_tv = popView.findViewById(R.id.dismiss_tv);
        code_et = popView.findViewById(R.id.code_et);
        countDownMsgUtils = CountDownMsgUtils.registerCountDownAction(context, "cancleAccountCode",
                60, getcode_tv, new CountDownMsgUtils.ICountDownPostCode() {
                    @Override
                    public void allowToRequestCode() {
                        getPhoneCode();
                    }
                });
        confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code_et.getText().toString();
                if (ToolUtils.isNull(code)) {
                    T.showShort(context, context.getString(R.string.login_tx4));
                    return;
                }
                if (onCancelInerface != null) {
                    onCancelInerface.onConfirm(code);
                }
            }
        });
        dismiss_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void getPhoneCode() {
        REQ_Factory.POST_SEND_CODE_REQ req = new REQ_Factory.POST_SEND_CODE_REQ();
        req.type = "grant";
        BasePresent.doStaticCommRequest(context, req, true, true, new BasePresent.DoCommRequestInterface<BaseResponse, BaseResponse>() {
            @Override
            public void doStart() {
            }

            @Override
            public BaseResponse doMap(BaseResponse baseResponse) {
                return baseResponse;
            }

            @Override
            public void onSuccess(BaseResponse baseResponse) throws Exception {
                if (countDownMsgUtils != null) {
                    countDownMsgUtils.requestSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
