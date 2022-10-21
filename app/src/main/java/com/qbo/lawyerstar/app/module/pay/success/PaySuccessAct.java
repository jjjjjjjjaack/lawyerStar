package com.qbo.lawyerstar.app.module.pay.success;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.pay.bean.PayResultBean;
import com.qbo.lawyerstar.app.module.popup.PopupToPayView;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

import static framework.mvp1.base.constant.BROConstant.CLOSE_EXTRAACT_ACTION;

public class PaySuccessAct extends MvpAct<IPaySuccessView, BaseModel, PaySuccessPresenter> implements IPaySuccessView {

    public static void openAct(Context context, FOrderPayBean orderPayBean) {
        Intent intent = new Intent(context, PaySuccessAct.class);
        intent.putExtra("orderPayBean", orderPayBean);
        context.startActivity(intent);
    }

    @BindView(R.id.pay_iv)
    ImageView pay_iv;
    @BindView(R.id.backhome_tv)
    TextView backhome_tv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;

    PopupToPayView popupToPayView;

    @Override
    public PaySuccessPresenter initPresenter() {
        return new PaySuccessPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_pay_success;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
//
//        backhome_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CLOSE_EXTRAACT_ACTION);
//                intent.putExtra("CLOSE_EXARTACT_KEY", "VpMainAct");
//                sendBroadcast(intent);
//                finish();
//            }
//        });
    }

    @Override
    public void doBusiness() {
        presenter.orderPayBean = (FOrderPayBean) getIntent().getSerializableExtra("orderPayBean");
        if (presenter.orderPayBean == null) {
            finish();
        }
        presenter.checkOrderPayStatus();
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void showView(PayResultBean bean) {
        if (bean == null) {
            finish();
            return;
        }
        title_tv.setText(bean.status_text);
        content_tv.setText(bean.tips_text);
        if ("1".equals(bean.status)) {//支付成功
            pay_iv.setImageResource(R.mipmap.ic_commit_success_3);
            backhome_tv.setText("返回首页");
            backhome_tv.setTextColor(0xff02c4c3);
            backhome_tv.setBackgroundResource(R.drawable.shape_storke_02c4c3_r2);
            backhome_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CLOSE_EXTRAACT_ACTION);
                    intent.putExtra("CLOSE_EXARTACT_KEY", "VpMainAct");
                    sendBroadcast(intent);
                    finish();
                }
            });
        } else {//支付失败
            pay_iv.setImageResource(R.mipmap.ic_pay_fail_1);
            popupToPayView = new PopupToPayView(getMContext(), presenter.orderPayBean.getOrderType());
            backhome_tv.setText("重新支付");
            backhome_tv.setTextColor(0xffF83131);
            backhome_tv.setBackgroundResource(R.drawable.shape_storke_f83131_r2);
            backhome_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupToPayView.show(v, presenter.orderPayBean, new PopupToPayView.ToPayInterface() {
                        @Override
                        public void toPayFinish(FOrderPayBean fOrderPayBean) {
                            presenter.checkOrderPayStatus();
//                            PaySuccessAct.openAct(getMContext(),fOrderPayBean);
                        }
//                        @Override
//                        public void alipayRequest() {
//
//                        }
//
//                        @Override
//                        public void paySuccess() {
//                            presenter.checkOrderPayStatus();
//                        }
                    });
                }
            });
        }
//        else {
//            pay_iv.setImageResource(R.mipmap.ic_waitpay_1);
//
//        }
    }
}
