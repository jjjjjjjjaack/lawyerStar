package com.qbo.lawyerstar.app.module.business.wap;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.qbo.lawyerstar.R;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class BusinessWapAct extends MvpAct<IBusinessWapView, BaseModel, BusinessWapPresenter> implements IBusinessWapView {

    public static void openAct(Context context, String urlkey) {
        Intent intent = new Intent(context, BusinessWapAct.class);
        intent.putExtra("urlkey", urlkey);
        context.startActivity(intent);
    }

    WebView fazWebView;

    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_business_wap;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        initWebView();
    }

    /**
     * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
     * LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
     * LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
     */
    public void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
        fazWebView = new WebView(this);
        fazWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);      //设置 缓存模式
        fazWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
        fazWebView.getSettings().setJavaScriptEnabled(true);//
        fazWebView.getSettings().setBlockNetworkImage(true);//先阻塞加载图片
        fazWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);//开启硬件加速
        fazWebView.getSettings().setUseWideViewPort(true);
        fazWebView.setVerticalScrollBarEnabled(false);
        fazWebView.addJavascriptInterface(new FazJavascriptInterface(), "android");
        WebView.setWebContentsDebuggingEnabled(true);
//允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fazWebView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
        fazWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //如果想要屏蔽只需要返回ture 即可
                return false;
            }
        });
        webview_fl.addView(fazWebView,
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void doBusiness() {
        String urlkey = getIntent().getStringExtra("urlkey");
        presenter.getWapUrl(urlkey);
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {
        if (fazWebView != null) {
            fazWebView.stopLoading();
            fazWebView.removeAllViews();
            fazWebView = null;
        }
    }

    @Override
    public BusinessWapPresenter initPresenter() {
        return new BusinessWapPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void setWap(WapUrlBean bean) {
        if (bean != null) {
            setMTitle(bean.label);
            fazWebView.loadUrl(bean.page);
        }
    }

    public static class FazJavascriptInterface {

        public FazJavascriptInterface() {
        }

        //页面初始化成功
        @JavascriptInterface
        public void Back() {
        }
    }
}
