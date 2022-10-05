package com.qbo.lawyerstar.app.module.contract.library.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.provider.DocumentsContract;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qbo.lawyerstar.BuildConfig;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.business.LawBusinessUtils;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.list.ContractLibListAct;
import com.qbo.lawyerstar.app.module.contract.library.list.IContractLibListView;
import com.qbo.lawyerstar.app.module.pay.success.PaySuccessAct;
import com.qbo.lawyerstar.app.module.popup.PopupToPayView;
import com.qbo.lawyerstar.app.utils.CEventUtils;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.CachePathUtil;
import framework.mvp1.base.util.DownloadUtil;
import framework.mvp1.base.util.FileProviderUtil;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.T;

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
    @BindView(R.id.topay_tv)
    TextView topay_tv;

    PopupToPayView popupToPayView;

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
        popupToPayView = new PopupToPayView(this, "contract_documents");

        more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FCacheUtils.getUserInfo(getMContext(), true, new FCacheUtils.GetUserInfoInterface() {
                    @Override
                    public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                        if (userInfoBean.isVip()) {
                            more_tv.setVisibility(View.GONE);
                            zz_view.setVisibility(View.GONE);
                        } else {
//                            T.showShort(getMContext(), "请先开通vip");
                            LawBusinessUtils.showVipTipView(getMContext(), more_tv);
                        }
                    }

                    @Override
                    public void fail() {

                    }
                });


//                more_tv.setVisibility(View.GONE);
//                zz_view.setVisibility(View.GONE);
            }
        });
    }

    private void initWebView() {
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > maxwidth){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width = maxwidth;" +
                        "}" +
                        "}" +
                        "}";
                String width = String.valueOf(ResourceUtils.getWindowsWidth(ContractLibDetailAct.this));
                view.loadUrl(javascript);
                view.loadUrl("javascript:ResizeImages();");
            }
        });
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
        showInfo();
    }

    public void showInfo() {
        if (presenter.bean == null) {
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

        if (presenter.bean.is_pay) {
            topay_tv.setText(getString(R.string.contract_lib_list_tx4_1));
            topay_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.confirmDownload();
                }
            });
        } else {
            topay_tv.setText(getString(R.string.contract_lib_list_tx4));
            topay_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.createOrder();
                }
            });
        }
    }


    /**
     * 根据网址用浏览器打开
     */
    @Override
    public void confirmDownLoad() {
        if (presenter.bean == null) {
            return;
        }
        download_tv.setText(getString(R.string.contract_lib_list_tx8, presenter.bean.download_num));
        EventBus.getDefault().post(new CEventUtils.ContractDownLoadSuccess(presenter.bean.id, presenter.bean.download_num));

        final ProgressDialog dialog = new ProgressDialog(getMContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("文件下载中");
        dialog.setMax(100);
        dialog.show();
        DownloadUtil downloadUtil = DownloadUtil.get();
        downloadUtil.download(presenter.bean.template, presenter.bean.file_name,
                "pdfdownload", new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        if (isDestroyed()) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                String path = CachePathUtil.getCachePathFile("pdfdownload").getPath();
                                T.showLong(getMContext(), "下载成功，文件保存在" + path);
                                File file = new File(path);
                                if (null == file || !file.exists()) {
                                    return;
                                }
//                                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//                                //intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
//                                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, FileProviderUtil.getUri(getMContext(),file));
//                               startActivity(intent);

//                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setDataAndType(FileProviderUtil.getUri(getMContext(), file), "file/*");
//                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onDownloading(int progress) {
                        dialog.setProgress(progress);
                    }

                    @Override
                    public void onDownloadFailed() {
                        if (isDestroyed()) {
                            return;
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                T.showShort(getMContext(), "下载失败，请联系客服");
                            }
                        });
                    }
                });
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

    @Override
    public void createSuccess(FOrderPayBean bean) {
        if (bean != null) {
            popupToPayView.show(topay_tv, bean, new PopupToPayView.ToPayInterface() {
                @Override
                public void alipayRequest() {

                }

                @Override
                public void paySuccess() {
                    EventBus.getDefault().post(new CEventUtils.ContractPaySuccessEvent(presenter.bean.id));
                    presenter.bean.is_pay = true;
                    try {
                        showInfo();
                    } catch (Exception e) {
                    }
                    gotoActivity(PaySuccessAct.class);
                }

            });
//            gotoActivity(PaySuccessAct.class);
        }
    }
}
