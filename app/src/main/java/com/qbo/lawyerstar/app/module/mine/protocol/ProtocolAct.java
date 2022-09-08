package com.qbo.lawyerstar.app.module.mine.protocol;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.protocol.bean.ProtocolBean;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WebViewUtil;

public class ProtocolAct extends MvpAct<IProtocolView, BaseModel, ProtocolPresenter> implements IProtocolView {

    
    /**
     * @description
     * @param type  类型 privacy 隐私协议 user 用户协议 cancel 注销协议 lawyer 律师协议
     * @return 
     * @author jieja
     * @time 2022/9/8 15:46
     */
    public static void openAct(Context context, String type) {
        Intent intent = new Intent(context,ProtocolAct.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;
    private WebView webView;


    @Override
    public ProtocolPresenter initPresenter() {
        return new ProtocolPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_protocol;
    }

    @Override
    public void viewInitialization() {
        setBackPress();

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.setVerticalScrollBarEnabled(false);

        webview_fl.addView(webView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void doBusiness() {
        String type = getIntent().getStringExtra("type");
        if (ToolUtils.isNull(type)) {
            finish();
            return;
        }
        presenter.getProtocol(type);
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
    public void showProtocol(ProtocolBean protocolBean) {
        if (protocolBean != null) {
            setMTitle(protocolBean.title);
            webView.loadDataWithBaseURL("", WebViewUtil.setWebViewContent(protocolBean.content),"text/html", "UTF-8", "");
        }
    }
}
