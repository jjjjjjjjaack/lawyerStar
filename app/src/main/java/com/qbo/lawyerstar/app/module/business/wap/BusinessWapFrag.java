package com.qbo.lawyerstar.app.module.business.wap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.business.FazH5WebViewForFragUtils;
import com.qbo.lawyerstar.app.module.business.LawBusinessUtils;
import com.qbo.lawyerstar.app.module.business.bean.H5URLBean;
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
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.FileProviderUtil;
import framework.mvp1.base.util.GetFilePathFromUri;
import framework.mvp1.base.util.LoadingUtils;
import framework.mvp1.base.util.LoginoutUtis;
import framework.mvp1.base.util.StatusBarUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;

import static android.app.Activity.RESULT_OK;
import static com.luck.picture.lib.config.PictureConfig.CHOOSE_REQUEST;
import static com.luck.picture.lib.config.PictureConfig.REQUEST_CAMERA;
import static framework.mvp1.base.constant.BROConstant.CLOSE_EXTRAACT_ACTION;

public class BusinessWapFrag extends MvpFrag<IBusinessWapView, BaseModel, BusinessWapPresenter> implements IBusinessWapView {

//    WebView fazWebView;

    @BindView(R.id.statusiv)
    View statusiv;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;

    PopupToPayView popupToPayView;

//    private boolean isFront = false;

    @Override
    public void baseInitialization() {
    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_business_wap;
    }

    @Override
    public void viewInitialization() {
        statusiv.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(getMContext());
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
        EventBus.getDefault().register(this);
        presenter.getWapUrl("company_index");
    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public BusinessWapPresenter initPresenter() {
        return new BusinessWapPresenter();
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void setWap(WapUrlBean bean) {
        if (bean != null) {
//            fazWebView.loadUrl(bean.page);
            FazH5WebViewForFragUtils.initFAZH5Web(getMContext(), bean.page, false);
            FazH5WebViewForFragUtils.addWebView(getMContext(), webview_fl);
        }
    }

    private boolean isSelect = false;

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                Uri uri = FileProviderUtil.getUri(getMContext(), new File(path));
                Uri[] imgTaskUris = {uri};
                if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                    FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(imgTaskUris);
                    FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                    isSelect = false;
                }
            } else {
                if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                    FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(null);
                    FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                    isSelect = false;
                }
            }
        }
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (resultCode == RESULT_OK) {
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (result == null) {
                    if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(null);
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
                String path = GetFilePathFromUri.getFileAbsolutePath(getMContext(), result);
                if (TextUtils.isEmpty(path)) {
                    if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(null);
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
                Uri uri = FileProviderUtil.getUri(getMContext(), new File(path));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Uri[] imgTaskUris = {uri};
                    if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(imgTaskUris);
                        FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                        isSelect = false;
                    }
                    return;
                }
            }
            if (FazH5WebViewForFragUtils.mUploadCallbackAboveL != null) {
                FazH5WebViewForFragUtils.mUploadCallbackAboveL.onReceiveValue(null);
                FazH5WebViewForFragUtils.mUploadCallbackAboveL = null;
                isSelect = false;
            }
        }
//        if (requestCode == 8768 && resultCode == RESULT_OK) {
//            try {
//                String params = data.getStringExtra("params");
//                FazH5WebViewForFragUtils.invoke("NewHouse", params);
//            } catch (Exception e) {
//            }
//        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        FazH5WebViewForFragUtils.backPage("");
        if (urltype == 0) {
            FazH5WebViewForFragUtils.finishWeb();
        }
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
    public void handleH5Event(CEventUtils.H5ForFragEvent event) {
//        if (!isFront) {
//            return;
//        }
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
//                            FazH5WebViewForFragUtils.backPage("");
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
                        T.showShort(getMContext(), title);
                    }
                } catch (Exception e) {
                }
                break;
            case -2:// quit
//                FazH5WebViewForFragUtils.backPage(event.object);
//                finish();
                break;
            case -1:
                break;
            case 0:
//                if (tv_back_right != null) {
//                    final String ObjectStr = event.object;
//                    tv_back_right.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            FazH5WebViewForFragUtils.invoke(ObjectStr, "");
//                        }
//                    });
//                }
                break;
            case 1:
                LoadingUtils.getLoadingUtils().hideLoadingView(getMContext());
//                try {
//                    String str = event.object;
//                    JSONObject jsonObject = JSONObject.parseObject(str);
//                    String title = jsonObject.getString("title");
//                    rightbtn = jsonObject.getString("rightText");
////                    path = jsonObject.getString("path");
//                    if (!ToolUtils.isNull(title)) {
//                        this.title = title;
//                    }
//                } catch (Exception e) {
//
//                }
                break;
            case 2:
                try {
                    String str = event.object;
                    JSONObject jsonObject = JSONObject.parseObject(str);
                    String url = jsonObject.getString("url");
                    String params = jsonObject.getString("params");
                    boolean hideHeader = jsonObject.getBoolean("hideHeader");
                    BusinessWapAct.openAct(getMContext(), 1, url, params,hideHeader);
//                    isNavigateTo = true;
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
                getMContext().sendBroadcast(intent);
                break;
        }

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
