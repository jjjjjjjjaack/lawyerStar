package com.qbo.lawyerstar.app.module.business;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;


import com.qbo.lawyerstar.app.utils.CEventUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.net.NET_URL;
import framework.mvp1.base.util.CachePathUtil;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.ToolUtils;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

public class FazH5WebViewUtils {


    //    public static WebView fazWebView;
    public static boolean isReady;
    public static final String H5CachePath = (File.separator + "lawyerH5");
    public static final String TAG = "lawyerH5";
    public static ValueCallback<Uri[]> mUploadCallbackAboveL;

    /**
     * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
     * LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
     * LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
     *
     * @param context
     */
    private static WebView initWebView(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
        WebView fazWebView = new WebView(context);
        fazWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);      //设置 缓存模式
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
        return fazWebView;
    }


    public static class FazJavascriptInterface {

        public FazJavascriptInterface() {
        }

        //页面初始化成功
        @JavascriptInterface
        public void onLaunch(String o) {
            Log.i(TAG, "onLaunch(" + o + ")");
            isReady = true;
        }

        //页面加载完成
        @JavascriptInterface
        public void pageOnLoad(String o) {
            Log.i(TAG, "pageOnLoad(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-1, o));
        }

        //右上方按钮点击
        @JavascriptInterface
        public void onNavigationBarButtonTap(String o) {
            Log.i(TAG, "onNavigationBarButtonTap(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(0, o));
        }


        //设置标题与右上方按钮文字
        @JavascriptInterface
        public void setNavbar(String o) {
            Log.i(TAG, "setNavbar(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(1, o));
        }

        //触发finish act
        @JavascriptInterface
        public void quit(String o) {
            Log.i(TAG, "quit(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-2, o));
            if (o.contains("\"refresh\":true")) {
                EventBus.getDefault().post(new CEventUtils.H5QuitRefreshEvent());
            }
        }

        //跳转
        @JavascriptInterface
        public void navigateTo(String o) {
            Log.i(TAG, "navigateTo(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(2, o));
        }

        //跳转
        @JavascriptInterface
        public void showToast(String o) {
            Log.i(TAG, "showToast(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-3, o));
        }

        //跳转
        @JavascriptInterface
        public void login(String o) {
            Log.i(TAG, "login(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-4, o));
        }

        @JavascriptInterface
        public void share(String o) {
            Log.i(TAG, "share(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-5, o));
        }

        @JavascriptInterface
        public void Back(String o) {
            Log.i(TAG, "Back(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(-2, o));
        }

        @JavascriptInterface
        public void Pay(String o) {
            Log.i(TAG, "Pay(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(11, o));
        }

        @JavascriptInterface
        public void IsVip(String o) {
            Log.i(TAG, "IsVip(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(12, o));
        }

        @JavascriptInterface
        public void toIndex(String o) {
            Log.i(TAG, "toIndex(" + o + ")");
            EventBus.getDefault().post(new CEventUtils.H5Event(13, o));
        }
        //页面加载完成
//        @JavascriptInterface
//        public void receiveAppMessages(String method, String params) {
//            Log.i(TAG, "receiveAppMessages(" + method + "," + params + ")");
//        }
    }

    /**
     *
     */
    public static class CookieStr {
        public String name;
        public String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public CookieStr(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }


    private static final Set<String> offlineResources = new HashSet<>();

    private void fetchOfflineResources() {
        try {
            String[] res = CachePathUtil.getCachePathFile(H5CachePath).list();
            if (res != null) {
                Collections.addAll(offlineResources, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param url
     * @param cookies
     */
    public static void syncCookie(Context context, String url, List<CookieStr> cookies) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(context);
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeAllCookie();
            StringBuffer sb = new StringBuffer();
            for (CookieStr cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                if (!ToolUtils.isNull(cookieName)
                        && !ToolUtils.isNull(cookieValue)) {
                    sb.append(cookieName + "=");
                    sb.append(cookieValue + ";");
                }
            }
            String[] cookie = sb.toString().split(";");
            for (int i = 0; i < cookie.length; i++) {
                cookieManager.setCookie(url, cookie[i]);// cookies是在HttpClient中获得的cookie
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();
            } else {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        CookieManager.getInstance().flush();
                    }
                });
            }
        } catch (Exception e) {

        }

//        CookieSyncManager.getInstance().sync();
    }

    /**
     * @param context
     */
    public static WebView initFAZH5Web(Context context,String url, boolean needInit) {
//        String url = NET_URL.getInstance().getWapBaseUrl("?t=" + System.currentTimeMillis());
        if (url.contains("?")) {
            url += ("&tsec=" + System.currentTimeMillis());
        } else {
            url += ("?tsec=" + System.currentTimeMillis());
        }
        List<CookieStr> cookieStrs = new ArrayList<>();
        try {
            FToken token = FTokenUtils.getToken(context, false);
            cookieStrs.add(new CookieStr("Token", token.getToken()));
            cookieStrs.add(new CookieStr("k", token.getKey()));
//            cookieStrs.add(new CookieStr("k", "-SAFG-"));
        } catch (Exception e) {
//            return;
        }
        cookieStrs.add(new CookieStr("url", NET_URL.getInstance().getUrl("")));
        cookieStrs.add(new CookieStr("App", "android"));

        syncCookie(context, url, cookieStrs);
//        LoadingUtils.getLoadingUtils().showLoadingView(context);
//        if (needInit || fazWebView == null) {
        WebView   fazWebView =   initWebView(context);
//        }
        fazWebView.loadUrl(url);
        fazWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                Log.i(TAG, "onJsAlert(" + s + "," + s1 + ")");
                jsResult.confirm();
                return super.onJsAlert(webView, s, s1, jsResult);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.i(TAG, "onConsoleMessage([" + consoleMessage.messageLevel() + "] " + consoleMessage.message() + "(" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + "))");
                return super.onConsoleMessage(consoleMessage);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(null);
                    mUploadCallbackAboveL = null;
                }
                mUploadCallbackAboveL = valueCallback;
                String acceptTypes = "*/*";
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    acceptTypes = (fileChooserParams.getAcceptTypes()[0]);
                }
                EventBus.getDefault().post(new CEventUtils.H5Event(-7, acceptTypes));
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg);
                Log.e("点击", "3");
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg);
                Log.e("点击", "4");
            }
        });
        fazWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                try {
                    webView.getSettings().setBlockNetworkImage(false);
                    //判断webview是否加载了，图片资源
                    if (!webView.getSettings().getLoadsImagesAutomatically()) {
                        //设置wenView加载图片资源
                        webView.getSettings().setLoadsImagesAutomatically(true);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//                int lastSlash = url.lastIndexOf("/");
//                if (lastSlash != -1) {
//                    String suffix = url.substring(lastSlash + 1);
//                    if (!offlineResources.contains(suffix)) {
//                        Log.i(TAG, url);
////                        DownloadImpl.getInstance(MyApplication.getApp()).with(MyApplication.getApp())
////                                .url(url)
////                                .target(new File())
////                                .enqueue();
//                    }
//                }
                return super.shouldOverrideUrlLoading(webView, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                String url = webResourceRequest.getUrl().toString();
                if (url.contains("alipays://")) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        // intent.setSelector(null);
                        context.startActivity(intent);
                    } catch (Exception e) {
                    }
                    return true;
                } else {
//                int lastSlash = url.lastIndexOf("/");
//                if (lastSlash != -1) {
//                    String suffix = url.substring(lastSlash + 1);
//                    if (!offlineResources.contains(suffix)) {
//                        Log.i(TAG, url);
////                        DownloadImpl.getInstance(MyApplication.getApp()).with(MyApplication.getApp())
////                                .url(url)
////                                .target(new File())
////                                .enqueue();
//                    }
//                }
                    return super.shouldOverrideUrlLoading(webView, webResourceRequest);
                }
            }
        });
        return fazWebView;
//        LoadingUtils.getLoadingUtils().hideLoadingView(context);
    }


    /**
     * @param context
     * @param parentView
     */
    public static void addWebView(Context context, WebView fazWebView, ViewGroup parentView) {
        if (fazWebView == null) {
//            initWebView(context);
            return;
        }
        if (fazWebView.getParent() != null) {
            ((ViewGroup) fazWebView.getParent()).removeAllViews();
        }
        parentView.addView(fazWebView, new ViewGroup.LayoutParams(-1, -1));
    }

//    /**
//     * @param context
//     * @param parentView
//     */
//    public static void addWebView(Context context, FrameLayout parentView) {
//        if (fazWebView == null) {
////            initWebView(context);
//            return;
//        }
//        if (fazWebView.getParent() != null) {
//            ((ViewGroup) fazWebView.getParent()).removeAllViews();
//        }
//        parentView.addView(fazWebView,0);
//    }


    /**
     * 跳转界面
     *
     * @param params
     */
    public static void loadPage(Context context, WebView fazWebView, String url, String params) {
//        if (fazWebView == null || !isReady) {
//            initFAZH5Web(context, url, false);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loadPage(context, url, params);
//                }
//            }, 3000);
//            return;
//        }
        if (fazWebView != null) {
//            fazWebView.loadUrl("javascript:receiveAppMessages('route','" + params + "'");
            // 只需要将第一种方法的loadUrl()换成下面该方法即可
            Log.i(TAG, "javascript:receiveAppMessages('route'," + params + ")");
            fazWebView.evaluateJavascript("javascript:receiveAppMessages('route'," + params + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Log.i(TAG, value);
                }
            });
        }
    }


    /**
     * 跳转界面
     */
    public static void backPage(WebView fazWebView, String params) {
        if (fazWebView != null) {
            // 只需要将第一种方法的loadUrl()换成下面该方法即可
            Log.i(TAG, "javascript:receiveAppMessages('back'," + params + ")");
            fazWebView.loadUrl("javascript:receiveAppMessages('back'," + params + ")");
//            fazWebView.evaluateJavascript("javascript:receiveAppMessages('back')", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    //此处为 js 返回的结果
//                    Log.i(TAG, value);
//                }
//            });
        }
    }

    public static void finishWeb(WebView fazWebView) {
        if (fazWebView != null) {
            try {
//                fazWebView.stopLoading();
//                fazWebView.removeAllViews();
                fazWebView.loadUrl("");
//                if (fazWebView.getParent() != null) {
//                    ((ViewGroup) fazWebView.getParent()).removeAllViews();
//                }
            } catch (Exception e) {
            }
        }
    }


    /**
     * 跳转界面
     *
     * @param params
     */
    public static void invoke(WebView fazWebView, String method, String params) {
        if (fazWebView != null) {
            String methodName = "javascript:FAZApp_" + method + "(" + params + ")";
            Log.i(TAG, methodName);
            fazWebView.evaluateJavascript(methodName, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Log.i(TAG, value);
                }
            });
        }
    }

//    public static AgentWeb.PreAgentWeb initFazWebView(Activity mContext, View parentView) {
//        AgentWeb.PreAgentWeb mPreAgentWeb = AgentWeb.with(mContext)
//                .setAgentWebParent((FrameLayout) parentView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
//                .closeIndicator()
////                .useDefaultIndicator()
//                .createAgentWeb()
//                .ready();
//
//
//        return mPreAgentWeb;
//    }
//
//    /**
//     * 加载地址
//     *
//     * @param mPreAgentWeb
//     * @param url
//     * @return
//     */
//    public static AgentWeb.PreAgentWeb loadUrl(Context context, AgentWeb.PreAgentWeb mPreAgentWeb, String url) {
//        try {
//            FToken token = FTokenUtils.getToken(context, true);
//            AgentWebConfig.syncCookie(url, "Token=" + token.getToken());
//            AgentWebConfig.syncCookie(url, "url=" + NET_URL.getInstance().getBaseUrl());
//            AgentWebConfig.syncCookie(url, "App=android");
//            mPreAgentWeb.ready().go(url);
//        } catch (NeedLoginException e) {
//        }
//        return mPreAgentWeb;
//    }

}
