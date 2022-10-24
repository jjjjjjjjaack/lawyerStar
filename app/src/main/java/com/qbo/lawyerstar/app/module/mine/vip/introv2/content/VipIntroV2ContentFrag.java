package com.qbo.lawyerstar.app.module.mine.vip.introv2.content;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.order.list.comm.frag.OrderListCommListFrag;
import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.base.util.WebViewUtil;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

public class VipIntroV2ContentFrag extends MvpFrag<IVipIntroV2ContentView, BaseModel, VipIntroV2ContentPresenter> implements IVipIntroV2ContentView {

    public static VipIntroV2ContentFrag newInstance(VipIntroBean contentBean) {
        VipIntroV2ContentFrag frag = new VipIntroV2ContentFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("contentBean", contentBean);
        frag.setArguments(bundle);
        return frag;
    }


    @BindView(R.id.type_rcv)
    RecyclerView type_rcv;
    @BindView(R.id.commit_tv)
    View commit_tv;

    private WebView webView;
    @BindView(R.id.webview_fl)
    FrameLayout webview_fl;

    private MCommAdapter mCommAdapter;

    public int selectPos = -1;

    @Override
    public VipIntroV2ContentPresenter initPresenter() {
        return new VipIntroV2ContentPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_vipintro_content;
    }

    @Override
    public void viewInitialization() {
        type_rcv.setLayoutManager(new GridLayoutManager(getMContext(), 3));
        int itemWidth = (ResourceUtils.getScreenWidth(getMContext()) - ResourceUtils.dp2px(getMContext(), 38)) / 3;
        mCommAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<VipIntroBean.YearPriceBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_vip_typeprice_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, VipIntroBean.YearPriceBean yearPriceBean) {
                mCommVH.itemView.getLayoutParams().width = itemWidth;
                mCommVH.setText(R.id.typename_tv, yearPriceBean.memo);
                mCommVH.setText(R.id.price_tv, yearPriceBean.price);

                if (selectPos == position) {
                    mCommVH.itemView.setSelected(true);
                } else {
                    mCommVH.itemView.setSelected(false);
                }

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPos = position;
                        mCommVH.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        type_rcv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int itemPosition = parent.getChildLayoutPosition(view);
                if (itemPosition == 0) {
                    outRect.left = ResourceUtils.dip2px2(getContext(), 12);
                    outRect.right = ResourceUtils.dip2px2(getContext(), 3.5f);
                } else if (itemPosition == 1) {
                    outRect.left = ResourceUtils.dip2px2(getContext(), 3.5f);
                    outRect.right = ResourceUtils.dip2px2(getContext(), 3.5f);
                } else {
                    outRect.left = ResourceUtils.dip2px2(getContext(), 3.5f);
                    outRect.right = ResourceUtils.dip2px2(getContext(), 12);
                }
            }
        });
        type_rcv.setAdapter(mCommAdapter);
        initWebView();
    }

    @Override
    public void doBusiness() {
        presenter.vipIntroBean = (VipIntroBean) getArguments().getSerializable("contentBean");
        if (presenter.vipIntroBean != null) {
            mCommAdapter.setData(presenter.vipIntroBean.year_price);
            if (!ToolUtils.isNull(presenter.vipIntroBean.btn_color)) {
                commit_tv.setBackgroundColor(Color.parseColor("#" + presenter.vipIntroBean.btn_color));
            }
            String htmltx = WebViewUtil.setWebViewContent(presenter.vipIntroBean.service_icon);
            String orgint = presenter.vipIntroBean.service_icon;
            webView.loadDataWithBaseURL("",
                    orgint,
                    "text/html", "UTF-8", "");
        }
    }

    @Override
    public void onWakeBussiness() {

    }


    private void initWebView() {
        webView = new WebView(getMContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        webView.getSettings().setJavaScriptEnabled(true);//
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            try {
                webView.stopLoading();
                webView.removeAllViews();
                webView = null;
            } catch (Exception e) {
            }
        }
    }

    @Override
    public Context getMContext() {
        return getContext();
    }
}
