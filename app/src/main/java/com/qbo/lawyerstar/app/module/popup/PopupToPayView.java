package com.qbo.lawyerstar.app.module.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FPayTypeBean;
import com.qbo.lawyerstar.app.module.alipay.PayResult;
import com.qbo.lawyerstar.app.net.REQ_Factory;
import com.qbo.lawyerstar.app.net.RES_Factory;
import com.alipay.sdk.app.PayTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupToPayView extends PopupBaseView {

    private TextView commit_tv, price_tv;
    private RecyclerView rcy;
    private MCommAdapter mCommAdapter;

    private ToPayInterface toPayInterface;
    private FPayTypeBean selectBean;

    private int status = -1;

    public interface ToPayInterface {
        void alipayRequest();
    }

    public PopupToPayView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_to_pay_view;
    }

    @Override
    public void initPopupView() {
        rcy = popView.findViewById(R.id.rcy);
        commit_tv = popView.findViewById(R.id.commit_tv);
        price_tv = popView.findViewById(R.id.price_tv);

        rcy.setLayoutManager(new LinearLayoutManager(context));
        mCommAdapter = new MCommAdapter(context, new MCommVH.MCommVHInterface<FPayTypeBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_paytype_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, FPayTypeBean bean) {
                mCommVH.setImageResource(R.id.icon_iv, bean.iconRes);
                mCommVH.setText(R.id.typename_tv, bean.name);
                if (selectBean != null && selectBean.id.equals(bean.id)) {
                    mCommVH.setViewSelect(R.id.cb_iv, true);
                } else {
                    mCommVH.setViewSelect(R.id.cb_iv, false);
                }
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectBean = bean;
                        mCommVH.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        rcy.setAdapter(mCommAdapter);
        getData();
    }


    public void getData() {
        REQ_Factory.GET_PAYTYPE_LIST_REQ req = new REQ_Factory.GET_PAYTYPE_LIST_REQ();
        BasePresent.doStaticCommRequest(context, req, true, true,
                new BasePresent.DoCommRequestInterface<BaseResponse,
                        List<FPayTypeBean>>() {
                    @Override
                    public void doStart() {

                    }

                    @Override
                    public List<FPayTypeBean> doMap(BaseResponse baseResponse) {
                        List<FPayTypeBean> payTypeBeans = new ArrayList<>();
//                List<String> pstrs = JSONObject.parseArray(baseResponse.datas,String.class);
                        if (!ToolUtils.isNull(baseResponse.datas)) {
                            if (baseResponse.datas.contains("alipay")) {
                                payTypeBeans.add(new FPayTypeBean("alipay", R.mipmap.ic_alipay_1, "支付宝支付"));
                            }
                        }

                        return payTypeBeans;
                    }

                    @Override
                    public void onSuccess(List<FPayTypeBean> fPayTypeBeans) throws Exception {
                        if (fPayTypeBeans.size() > 0) {
                            status = 1;
                        } else {
                            status = 0;
                        }
                        if (mCommAdapter != null) {
                            mCommAdapter.setData(fPayTypeBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        status = 0;
                    }
                });
    }

    public void show(View parent, String price, ToPayInterface toPayInterface) {
        if (status == -1) {
            T.showShort(context, context.getString(R.string.popup_to_pay_tx5));
            return;
        }
        if (status == 0) {
            T.showShort(context, context.getString(R.string.popup_to_pay_tx4));
            return;
        }
        price_tv.setText(context.getString(R.string.law_ask_comm_tx4, price));
        commit_tv.setText(context.getString(R.string.popup_to_pay_tx3, price));
        this.toPayInterface = toPayInterface;
        super.showBottom(parent);
    }

    private static final int ALIPAY_SDK_PAY_FLAG = 1;
    public void payForAliay(Activity activity,String orderStr){
        if(ToolUtils.isNull(orderStr)){
            T.showShort(context,"");
            return;
        }
        final String orderInfo = orderStr;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map <String,String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = ALIPAY_SDK_PAY_FLAG;
                msg.obj = result;
                mAlipayHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler mAlipayHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ALIPAY_SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
