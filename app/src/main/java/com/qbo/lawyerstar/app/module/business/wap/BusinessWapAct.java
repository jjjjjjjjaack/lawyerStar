package com.qbo.lawyerstar.app.module.business.wap;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.luck.picture.lib.config.PictureConfig.CHOOSE_REQUEST;
import static com.luck.picture.lib.config.PictureConfig.REQUEST_CAMERA;
import static framework.mvp1.base.constant.BROConstant.CLOSE_EXTRAACT_ACTION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.business.FazH5WebViewUtils;
import com.qbo.lawyerstar.app.module.business.LawBusinessUtils;
import com.qbo.lawyerstar.app.module.business.bean.H5URLBean;
import com.qbo.lawyerstar.app.module.mine.login.base.LoginAct;
import com.qbo.lawyerstar.app.module.pay.success.PaySuccessAct;
import com.qbo.lawyerstar.app.module.popup.PopupToPayView;
import com.qbo.lawyerstar.app.utils.CEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.FileProviderUtil;
import framework.mvp1.base.util.FileUtils;
import framework.mvp1.base.util.GetFilePathFromUri;
import framework.mvp1.base.util.LoadingUtils;
import framework.mvp1.base.util.LoginoutUtis;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.pics.GlideEngine;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BusinessWapAct extends MvpAct<IBusinessWapView, BaseModel, BusinessWapPresenter> implements IBusinessWapView {

    public static void openAct(Context context, String urlkey) {
        Intent intent = new Intent(context, BusinessWapAct.class);
        intent.putExtra("urltype", 0);
        intent.putExtra("urlkey", urlkey);
        context.startActivity(intent);
    }

    public static void openAct(Context context, int urltype, String url, String params) {
        Intent intent = new Intent(context, BusinessWapAct.class);
        intent.putExtra("urltype", urltype);
        H5URLBean h5URLBean = new H5URLBean(url, params);
        intent.putExtra("H5URLBean", h5URLBean);
        context.startActivity(intent);
    }

    public static void openAct(Context context, int urltype, String url, String params, boolean hideTitle) {
        Intent intent = new Intent(context, BusinessWapAct.class);
        intent.putExtra("urltype", urltype);
        H5URLBean h5URLBean = new H5URLBean(url, params);
        intent.putExtra("H5URLBean", h5URLBean);
        intent.putExtra("hideTitle", hideTitle);
        context.startActivity(intent);
    }

    public static void openActForPay(Context context, String orderSn, String orderType) {
        Intent intent = new Intent(context, BusinessWapAct.class);
        intent.putExtra("urltype", 0);
        intent.putExtra("urlkey", "payment");
        intent.putExtra("orderSn", orderSn);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

//    WebView fazWebView;

    WebView fazWebView;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;
    @BindView(R.id.tv_back_right)
    TextView tv_back_right;
    @BindView(R.id.title_rl)
    View title_rl;

    PopupToPayView popupToPayView;

    private H5URLBean h5URLBean;
    private boolean isNavigateTo = false;
    private boolean isFront = false;
    private String title;
    private String rightbtn;


    boolean needLoadP;

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
//        setBackPress();
//        initWebView();

        boolean hideTitle = getIntent().getBooleanExtra("hideTitle", false);
        if (hideTitle) {
            title_rl.setVisibility(View.GONE);
        }
        findViewById(R.id.rl_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FazH5WebViewUtils.backPage(fazWebView, "''");
                finish();
            }
        });
//        fazWebView = FazH5WebViewUtils.initFAZH5Web(getMContext(),"https://www.baidu.com",true);
//        FazH5WebViewUtils.addWebView(this,fazWebView, webview_fl);
    }

    /**
     * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据。
     * LOAD_DEFAULT: 根据cache-control决定是否从网络上取数据。
     * LOAD_CACHE_NORMAL: API level 17中已经废弃，从API level 11开始作用同LOAD_DEFAULT模式
     * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据。
     * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。本地没有缓存时才从网络上获取。
     */
//    public void initWebView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            android.webkit.WebView.enableSlowWholeDocumentDraw();
//        }
//        fazWebView = new WebView(this);
//        fazWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);      //设置 缓存模式
//        fazWebView.getSettings().setDomStorageEnabled(true);// 开启 DOM storage API 功能
//        fazWebView.getSettings().setJavaScriptEnabled(true);//
//        fazWebView.getSettings().setBlockNetworkImage(true);//先阻塞加载图片
//        fazWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);//开启硬件加速
//        fazWebView.getSettings().setUseWideViewPort(true);
//        fazWebView.setVerticalScrollBarEnabled(false);
//        fazWebView.addJavascriptInterface(new FazJavascriptInterface(), "android");
//        WebView.setWebContentsDebuggingEnabled(true);
////允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            fazWebView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        fazWebView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                //如果想要屏蔽只需要返回ture 即可
//                return false;
//            }
//        });
//        webview_fl.addView(fazWebView,
//                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//    }
    public int urltype;

    @Override
    public void doBusiness() {
        urltype = getIntent().getIntExtra("urltype", 0);
        if (urltype == 0) {
            String urlkey = getIntent().getStringExtra("urlkey");
            presenter.orderSn = getIntent().getStringExtra("orderSn");
            presenter.orderType = getIntent().getStringExtra("orderType");
            presenter.getWapUrl(urlkey);
        } else {
            h5URLBean = (H5URLBean) getIntent().getSerializableExtra("H5URLBean");
            if (h5URLBean != null) {
                if (!ToolUtils.isNull(h5URLBean.toString())) {
                    if (!ToolUtils.isNull(h5URLBean.url) && h5URLBean.url.contains("http")) {
                        fazWebView = FazH5WebViewUtils.initFAZH5Web(this, h5URLBean.url, false);
                        FazH5WebViewUtils.addWebView(this, fazWebView, webview_fl);
//                        fazWebView.loadUrl(h5URLBean.url);
                    } else {
                        fazWebView = FazH5WebViewUtils.initFAZH5Web(this, "https://www.fatianping.com/front/#/", false);
//                        FazH5WebViewUtils.loadPage(this, fazWebView, "", h5URLBean.toString());
                        needLoadP = true;
                        FazH5WebViewUtils.addWebView(this, fazWebView, webview_fl);
                    }
                }
            }
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {
//        if (fazWebView != null) {
//            fazWebView.stopLoading();
//            fazWebView.removeAllViews();
//            fazWebView = null;
//        }
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
            fazWebView = FazH5WebViewUtils.initFAZH5Web(this, bean.page, false);
            webview_fl.addView(fazWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//            FazH5WebViewUtils.addWebView(this, fazWebView, webview_fl);
//            fazWebView.loadUrl(bean.page);
//            FazH5WebViewUtils.initFAZH5Web(this, bean.page, false);
//            FazH5WebViewUtils.addWebView(this,w webview_fl);
        }
    }

    private boolean isSelect = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_REQUEST || requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList == null || selectList.size() <= 0) {
                    return;
                }
//                String path = selectList.get(0).getRealPath();
                String path = "";
                if (selectList.get(0).getCompressPath() != null) {
                    path = selectList.get(0).getCompressPath();
                } else if (selectList.get(0).getCutPath() != null) {
                    path = selectList.get(0).getCutPath();
                } else {
                    path = selectList.get(0).getRealPath();
                }
//                Uri uri = Uri.parse(path);
//                Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", new File(path));
                Uri uri = FileProviderUtil.getUri(this, new File(path));
                Uri[] imgTaskUris = {uri};
                if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                    FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(imgTaskUris);
                    FazH5WebViewUtils.mUploadCallbackAboveL = null;
                    isSelect = false;
                }
            } else {
                if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                    FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(null);
                    FazH5WebViewUtils.mUploadCallbackAboveL = null;
                    isSelect = false;
                }
            }
        }
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (resultCode == RESULT_OK) {
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (result == null) {
                    if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(null);
                        FazH5WebViewUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
                String path = GetFilePathFromUri.getFileAbsolutePath(this, result);
                if (TextUtils.isEmpty(path)) {
                    if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(null);
                        FazH5WebViewUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
                Uri uri = FileProviderUtil.getUri(this, new File(path));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Uri[] imgTaskUris = {uri};
                    if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(imgTaskUris);
                        FazH5WebViewUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
            }
            if (FazH5WebViewUtils.mUploadCallbackAboveL != null) {
                FazH5WebViewUtils.mUploadCallbackAboveL.onReceiveValue(null);
                FazH5WebViewUtils.mUploadCallbackAboveL = null;
                isSelect = false;
            }
        }
//        if (requestCode == 8768 && resultCode == RESULT_OK) {
//            try {
//                String params = data.getStringExtra("params");
//                FazH5WebViewUtils.invoke("NewHouse", params);
//            } catch (Exception e) {
//            }
//        }
    }


    @Override
    protected void onResume() {
//        if (preAgentWeb != null)
//            preAgentWeb.get().getWebLifeCycle().onResume();
        super.onResume();
        isFront = true;
        EventBus.getDefault().register(this);
//        if (isNavigateTo) {
//            FazH5WebViewUtils.addWebView(this, webview_fl);
//            isNavigateTo = false;
//        }
    }

    @Override
    protected void onDestroy() {
        if (fazWebView != null) {
            fazWebView.stopLoading();
            webview_fl.removeAllViews();
            fazWebView = null;
        }
        super.onDestroy();
//        FazH5WebViewUtils.backPage("");
//        if (urltype == 0) {
//            FazH5WebViewUtils.finishWeb();
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        isFront = false;
        EventBus.getDefault().unregister(this);
    }

    public final static int FILECHOOSER_RESULTCODE = 1124;

    public void choosePhoto(String acceptTypes) {
        isSelect = true;
//        PictureSelector.create((Activity) getMContext())
//                .openGallery(PictureMimeType.ofImage())
//                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                .isCamera(true)// 是否显示拍照按钮
//                .isGif(false)// 是否显示gif图片
//                .isPreviewImage(true)// 是否可预览图片
//                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
//                .isEnableCrop(false)// 是否裁剪
//                .isCompress(true)// 是否压缩
//                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
//                .circleDimmedLayer(false)// 是否圆形裁剪
//                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .forResult(CHOOSE_REQUEST);

        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(acceptTypes);
        startActivityForResult(Intent.createChooser(i, "选择文件"), FILECHOOSER_RESULTCODE);

    }

    Boolean isLogut = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleH5Event(CEventUtils.H5Event event) {
        if (!isFront) {
            return;
        }
        switch (event.what) {
            case -8://编辑文章，选择房源
                break;
            case -7:
                choosePhoto(event.object);
                break;
            case -5:
                break;
            case -4://login
                synchronized (isLogut) {
                    if (isLogut) {
                        return;
                    }
                    isLogut = true;
                    T.showShort(getMContext(), "登录过期，请重新登录");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            FazH5WebViewUtils.backPage("");
                            sendBrocastCloseTragetAct("BusinessWapAct");
                            LoginoutUtis.getInstance().doLogOut(getMContext());
                        }
                    }, 1000);
                }
//                gotoActivity(LoginAct.class);
                break;
            case -3:// toast
                try {
                    String str = event.object;
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String title = jsonObject.getString("title");
                    if (!ToolUtils.isNull(title)) {
                        T.showShort(this, title);
                    }
                } catch (Exception e) {
                }
                break;
            case -2:// quit
//                FazH5WebViewUtils.backPage(event.object);
                finish();
                break;
            case -1://加载完成
                if (h5URLBean != null && needLoadP) {
                    needLoadP = false;
                    FazH5WebViewUtils.loadPage(this, fazWebView, "", h5URLBean.toString());
                }
                break;
            case 0:
                if (tv_back_right != null) {
                    final String ObjectStr = event.object;
                    tv_back_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FazH5WebViewUtils.invoke(fazWebView, ObjectStr, "");
                        }
                    });
                }
                break;
            case 1:
                LoadingUtils.getLoadingUtils().hideLoadingView(this);
                try {
                    String str = event.object;
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String title = jsonObject.getString("title");
                    rightbtn = jsonObject.getString("rightText");
//                    path = jsonObject.getString("path");
                    if (!ToolUtils.isNull(title)) {
                        this.title = title;
                        setMTitle(title);
                    }
                    if (!ToolUtils.isNull(rightbtn)) {
                        if ("chatSetting".equals(rightbtn)) {
                            tv_back_right.setText("");
                            // 使用代码设置drawableleft
                            Drawable drawable = getResources().getDrawable(
                                    R.mipmap.ic_rigttop_dian_1);
                            // 这一步必须要做，否则不会显示。
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                                    drawable.getMinimumHeight());
                            tv_back_right.setCompoundDrawables(null, null, drawable, null);
                        } else {
                            tv_back_right.setText(rightbtn);
                            tv_back_right.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tv_back_right.setText(rightbtn);
                        tv_back_right.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
                break;
            case 2:
                try {
                    String str = event.object;
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String url = jsonObject.getString("url");
                    String params = jsonObject.getString("params");
                    boolean hideHeader = jsonObject.getBoolean("hideHeader");
                    BusinessWapAct.openAct(this, 1, url, params);
                    isNavigateTo = true;
//                    if (hideHeader) {
//                        title_rl.setVisibility(View.VISIBLE);
//                    }
                } catch (Exception e) {

                }
                break;
            case 11://支付 {"amount":120,"sn":"OL220922614443","type":"legal_advice"}
                try {
                    JSONObject json = JSONObject.parseObject(event.object);
                    if (popupToPayView != null) {
                        popupToPayView.dismiss();
                        popupToPayView = null;
                    }
                    FOrderPayBean payBean = new FOrderPayBean();
                    payBean.sn = json.getString("sn");
                    payBean.price = json.getString("amount");
                    popupToPayView = new PopupToPayView(getMContext(), json.getString("type"), webview_fl, payBean,
                            new PopupToPayView.ToPayInterface() {
                                @Override
                                public void toPayFinish(FOrderPayBean fOrderPayBean) {
                                    PaySuccessAct.openAct(getMContext(), fOrderPayBean);
                                }
//                                @Override
//                                public void alipayRequest() {
//                                }
//
//                                @Override
//                                public void paySuccess() {
//                                    gotoActivity(PaySuccessAct.class);
//                                }
                            });

                } catch (Exception e) {
                    T.showShort(getMContext(), "暂无法发起支付");
                }
                break;
            case 12:
                LawBusinessUtils.showVipTipView(getMContext(), webview_fl);
//                T.showShort(getMContext(), "请先开通vip");
                break;
            case 13:
                Intent intent = new Intent(CLOSE_EXTRAACT_ACTION);
                intent.putExtra("CLOSE_EXARTACT_KEY", "VpMainAct");
                sendBroadcast(intent);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FazH5WebViewUtils.backPage("");
    }


//    public static class FazJavascriptInterface {
//
//        public FazJavascriptInterface() {
//        }
//
//        //页面初始化成功
//        @JavascriptInterface
//        public void Back() {
//        }
//    }
}
