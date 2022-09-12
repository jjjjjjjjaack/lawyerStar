package com.qbo.lawyerstar.app.module.contract.library.detail;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qbo.lawyerstar.BuildConfig;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.list.ContractLibListAct;
import com.qbo.lawyerstar.app.module.contract.library.list.IContractLibListView;

import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class ContractLibDetailAct extends MvpAct<IContractLibDetailView, BaseModel
        , ContractLibDetailPresenter> implements IContractLibDetailView {

    public static void openAct(Context context, ContractLibBean bean) {
        Intent intent = new Intent(context, ContractLibDetailAct.class);
        intent.putExtra("contrcatBean", bean);
        context.startActivity(intent);
    }

    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.topprice_tv)
    TextView topprice_tv;
    @BindView(R.id.download_tv)
    TextView download_tv;
    @BindView(R.id.bottomprice_tv)
    TextView bottomprice_tv;

    private WebView webView;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;
    @BindView(R.id.zz_view)
    View zz_view;
    @BindView(R.id.more_tv)
    View more_tv;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_contract_library_detail;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.contract_lib_list_tx7);

        initWebView();
        zz_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initWebView() {
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setEnabled(false);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////handler.cancel(); 默认的处理⽅式，WebView变成空⽩页
//                handler.proceed();//接受证书
////handleMessage(Message msg); 其他处理
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//                String js = "";
////                js+= "alert('123123');";
//                js += " var oMeta = document.createElement('meta');";
//                js += "oMeta.name = 'viewport';";
//                js += "oMeta.content = 'width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0';";
//                js += "document.getElementsByTagName('head')[0].appendChild(oMeta);";
//                js += "var style = document.createElement(\"style\");";
//                js += "style.type = \"text/css\";";
//                js += "style.appendChild(document.createTextNode(\"body{max-width: 100%; width:100%; height:auto;word-wrap:break-word; font-family:Arial;padding:0px 5px 0px 5px;}\"));";
//                js += "var style2 = document.createElement(\"style\");";
//                js += "style2.type = \"text/css\";";
//                js += "style2.appendChild(document.createTextNode(\"iframe{max-width: 100%; width:100%; height:auto;}\"));";
//                js += "var head = document.getElementsByTagName(\"head\")[0];";
//                js += "head.appendChild(style);";
//                js += "head.appendChild(style2);";
//                webView.loadUrl("javascript:" + js);
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        bodyJsInject();
////                    }
////                }, 3000);
//
//            }
//        });

        webview_fl.addView(webView, 0, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void doBusiness() {
        presenter.bean = (ContractLibBean) getIntent().getSerializableExtra("contrcatBean");
        if (presenter.bean == null) {
            finish();
            return;
        }
        title_tv.setText(presenter.bean.title);
        content_tv.setText(presenter.bean.sub_title);
        topprice_tv.setText(presenter.bean.price + "元");
        bottomprice_tv.setText(presenter.bean.price);
        download_tv.setText(getString(R.string.contract_lib_list_tx8, presenter.bean.download_num));

        if (presenter.bean.template_html != null && presenter.bean.template_html.contains("http://192.168.1.143")) {
            presenter.bean.template_html = presenter.bean.template_html.replace("http://192.168.1.143", BuildConfig.API_URL);
        }
        webView.loadUrl(presenter.bean.template_html);
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {
        if (webView != null) {
            webView.stopLoading();
            webView.removeAllViews();
            webView = null;
        }
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public ContractLibDetailPresenter initPresenter() {
        return new ContractLibDetailPresenter();
    }
}
