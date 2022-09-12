package com.qbo.lawyerstar.app.module.mine.account.cancle;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;
import com.qbo.lawyerstar.app.module.popup.PopupCancelAccountSendCodeView;
import com.qbo.lawyerstar.app.module.popup.PopupTipSuccessView;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WebViewUtil;

public class CancelAccountAct extends MvpAct<ICancelAccountView, BaseModel, CancelAccountPresenter> implements ICancelAccountView {

    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;
    @BindView(R.id.bottom_rl)
    View bottom_rl;

    private WebView webView;

    private PopupCancelAccountSendCodeView sendCodeView;

    @Override
    public CancelAccountPresenter initPresenter() {
        return new CancelAccountPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_cancel_account;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.account_delete_tx1);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.setVerticalScrollBarEnabled(false);


        webview_fl.addView(webView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));


        sendCodeView = new PopupCancelAccountSendCodeView(this, new PopupCancelAccountSendCodeView.PopupCancelInerface() {
            @Override
            public void onConfirm(String code) {
                presenter.doCancelAccount(code);
            }
        });

        bottom_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCodeView.showCenter(v);
            }
        });
    }

    @Override
    public void doBusiness() {
        presenter.getProtocol();
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {
        if (webview_fl != null) {
            webview_fl.removeAllViews();
        }
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
        }
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void cancleResult(boolean b) {
        if (b) {
            PopupTipSuccessView.showPopTipViewCenter(this, "注销成功", "账户已注销", new PopupTipSuccessView.OnDismissInterface() {
                @Override
                public void onDismiss() {
                    FTokenUtils.doLogout(getMContext());
                    ToolUtils.reStartApp(getMContext());
                }
            }, bottom_rl);
        }
    }

    @Override
    public void showProtocol(ProtocolBean protocolBean) {
        if (protocolBean != null) {
            webView.loadDataWithBaseURL("",
                    WebViewUtil.setWebViewContent(protocolBean.content),
                    "text/html", "UTF-8", "");
        }
    }
}
