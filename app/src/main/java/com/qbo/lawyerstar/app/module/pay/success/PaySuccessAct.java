package com.qbo.lawyerstar.app.module.pay.success;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.pay.bean.PayResultBean;
import com.qbo.lawyerstar.app.module.popup.PopupToPayView;
import com.qbo.lawyerstar.app.utils.CEventUtils;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

import static framework.mvp1.base.constant.BROConstant.CLOSE_EXTRAACT_ACTION;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    Handler checkHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (isDestroyed()) {
                return true;
            }
            if (message.what == 102) {
                presenter.checkOrderPayStatus();
                checkHandler.sendEmptyMessageDelayed(102, 2000);
            }
            return true;
        }
    });
    Handler waitTvHanhle = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (isDestroyed() || content_tv == null) {
                return true;
            }
            if (message.what == 1 || message.what == 2 || message.what == 3) {
                String sp = "";
                for (int i = 0; i < message.what; i++) {
                    sp += (".");
                }
                try {
                    content_tv.setText("正在支付，请稍等" + sp);
                    int nextwhat = message.what + 1;
                    if (message.what > 3) {
                        nextwhat = 1;
                    }
                    waitTvHanhle.sendEmptyMessageDelayed(nextwhat, 200);
                } catch (Exception e) {
                }
            }
            return true;
        }
    });

    @Override
    public PaySuccessPresenter initPresenter() {
        return new PaySuccessPresenter();
    }

    @Override
    public void baseInitialization() {
        EventBus.getDefault().register(this);
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
            return;
        }
//        presenter.checkOrderPayStatus();
        setWaitPayView();
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatPayEevent(CEventUtils.WechatPayEevent event) {
        try {
            if (event.code == -1 || event.code == -2) {
                waitTvHanhle.removeMessages(1);
                waitTvHanhle.removeMessages(2);
                waitTvHanhle.removeMessages(3);
                checkHandler.removeMessages(102);

                PayResultBean bean = new PayResultBean();
                bean.status = "-1";
                bean.status_text = "支付失败";
                bean.tips_text = (event.code == -1 ? "取消付款" : "支付参数错误");
                showView(bean);
            }
        } catch (Exception e) {
        }
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
            waitTvHanhle.removeMessages(1);
            waitTvHanhle.removeMessages(2);
            waitTvHanhle.removeMessages(3);
            pay_iv.setImageResource(R.mipmap.ic_commit_success_3);
            backhome_tv.setVisibility(View.VISIBLE);
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
        } else if ("-1".equals(bean.status)) {//支付失败
            waitTvHanhle.removeMessages(1);
            waitTvHanhle.removeMessages(2);
            waitTvHanhle.removeMessages(3);
            pay_iv.setImageResource(R.mipmap.ic_pay_fail_1);
            popupToPayView = new PopupToPayView(getMContext(), presenter.orderPayBean.getOrderType());
            backhome_tv.setVisibility(View.VISIBLE);
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
        } else {
//            pay_iv.setImageResource(R.mipmap.ic_waitpay_1);
//            backhome_tv.setVisibility(View.GONE);
            setWaitPayView();
        }
    }

    public void setWaitPayView() {
        pay_iv.setImageResource(R.mipmap.ic_waitpay_1);
        title_tv.setText("支付中");
        waitTvHanhle.removeMessages(1);
        waitTvHanhle.removeMessages(2);
        waitTvHanhle.removeMessages(3);
        waitTvHanhle.sendEmptyMessage(1);
//        content_tv.setText("正在支付，请稍等...");
        backhome_tv.setVisibility(View.GONE);
        checkHandler.removeMessages(102);
        checkHandler.sendEmptyMessageDelayed(102, 2000);
    }


}
