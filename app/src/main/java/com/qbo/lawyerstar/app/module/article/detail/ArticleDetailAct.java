package com.qbo.lawyerstar.app.module.article.detail;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WebViewUtil;

public class ArticleDetailAct extends MvpAct<IArticleDetailView, BaseModel
        , ArticleDetailPresenter> implements IArticleDetailView {

    public static void openAct(Context context, String article_id) {
        Intent intent = new Intent(context, ArticleDetailAct.class);
        intent.putExtra("article_id", article_id);
        context.startActivity(intent);
    }

    @BindView(R.id.tv_back_right)
    ImageView tv_back_right;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;

    private WebView webView;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_article_detial;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.law_study_frag_tx5);
//        tv_back_right.setImageResource(R.mipmap.ic_top_share_1);

        initWebView();
    }

    @Override
    public void doBusiness() {
        presenter.article_id = getIntent().getStringExtra("article_id");
        if (ToolUtils.isNull(presenter.article_id)) {
            finish();
            return;
        }
        presenter.getDetail(presenter.article_id);
//        title_tv.setText(presenter.articleBean.title);
//        time_tv.setText(presenter.articleBean.create_time);
//
//        webView.loadUrl(presenter.articleBean.);
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

    private void initWebView() {
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
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
    public ArticleDetailPresenter initPresenter() {
        return new ArticleDetailPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void showDetail(ArticleBean bean) {
        if (bean != null) {
            title_tv.setText(bean.title);
            time_tv.setText(bean.create_time);
            webView.loadDataWithBaseURL("",
                    WebViewUtil.setWebViewContent(bean.content),
                    "text/html", "UTF-8", "");
//        webView.loadUrl(presenter.articleBean.);
        }
    }
}
