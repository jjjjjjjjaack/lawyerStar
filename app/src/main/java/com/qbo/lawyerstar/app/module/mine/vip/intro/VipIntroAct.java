package com.qbo.lawyerstar.app.module.mine.vip.intro;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.home.bean.HomeDataBean;
import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;
import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WebViewUtil;

public class VipIntroAct extends MvpAct<IVipIntroView, BaseModel, VipIntroPresenter> implements IVipIntroView {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.commit_tv)
    TextView commit_tv;

    private WebView webView;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_vip_intro;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.vip_intro_tx1);

        initWebView();
    }

    @Override
    public void doBusiness() {
        presenter.getInfo();
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
    public VipIntroPresenter initPresenter() {
        return new VipIntroPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }


    @Override
    public void showInfo(List<VipIntroBean> beanList) {
        if (beanList.size() != 0) {
            presenter.vipIntroBeans = beanList;
            banner.setAdapter(new BannerImageAdapter<VipIntroBean>(beanList) {
                @Override
                public void onBindView(BannerImageHolder holder, VipIntroBean data, int position, int size) {
//                    //图片加载自己实现
                    GlideUtils.loadImageDefult(getMContext(), data.getPic(), holder.imageView);
                }
            }).addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    try {
                        showItemInfo(presenter.vipIntroBeans.get(position));
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            }).addBannerLifecycleObserver(this)//添加生命周期观察者
                    .setIndicator(new CircleIndicator(getMContext()));
            showItemInfo(beanList.get(0));
        }
    }

    @Override
    public void getAppInfoResult(AboutUsBean bean) {
        if (bean != null) {
            ToolUtils.callPhone(getMContext(), bean.getConsumer_hotline());
        }
    }

    public void showItemInfo(VipIntroBean vipIntroBean) {
        webView.loadDataWithBaseURL("",
                WebViewUtil.setWebViewContent(vipIntroBean.intro),
                "text/html", "UTF-8", "");
        commit_tv.setText(vipIntroBean.getPrice() + "/年 联系客服开通");
        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToolUtils.callPhone(getMContext(),);
                presenter.getAppInfo();
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
}
