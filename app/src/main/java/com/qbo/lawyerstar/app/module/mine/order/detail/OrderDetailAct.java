package com.qbo.lawyerstar.app.module.mine.order.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.business.wap.BusinessWapAct;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;
import com.qbo.lawyerstar.app.module.pay.success.PaySuccessAct;
import com.qbo.lawyerstar.app.module.popup.PopupTipWithBtnView;
import com.qbo.lawyerstar.app.module.popup.PopupToPayView;
import com.qbo.lawyerstar.app.utils.CEventUtils;
import com.qbo.lawyerstar.app.view.ChangeGasStationImageView2;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.willy.ratingbar.ScaleRatingBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;

public class OrderDetailAct extends MvpAct<IOrderDetailView, BaseModel, OrderDetailPresenter> implements IOrderDetailView {

    public static void openAct(Context context, String orderId, String orderType) {
        Intent intent = new Intent(context, OrderDetailAct.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.userlogo_civ)
    ChangeGasStationImageView2 userlogo_civ;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.tag_tv)
    TextView tag_tv;
    @BindView(R.id.status_tv)
    TextView status_tv;
    @BindView(R.id.content_title_tv)
    TextView content_title_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.content_detail_tv)
    TextView content_detail_tv;
    @BindView(R.id.reply_rcy)
    RecyclerView reply_rcy;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;

    @BindView(R.id.bottom_ll)
    View bottom_ll;
    @BindView(R.id.tocancle_tv)
    View tocancle_tv;
    @BindView(R.id.topay_tv)
    View topay_tv;
    @BindView(R.id.noresponser_ll)
    View noresponser_ll;
    @BindView(R.id.responser_ll)
    View responser_ll;

    PopupToPayView popupToPayView;

    public MCommAdapter mCommAdapter;

    @Override
    public OrderDetailPresenter initPresenter() {
        return new OrderDetailPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_order_detail;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.order_detail_tx1);

        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getInfo();
            }
        });

        reply_rcy.setLayoutManager(new LinearLayoutManager(getMContext()));
        mCommAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<OrderListBean.ReplayBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_orderdetail_replay_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, OrderListBean.ReplayBean replayBean) {
                mCommVH.setText(R.id.time_tv, replayBean.getResponse_time());
                mCommVH.setText(R.id.replaycontent_tv, replayBean.getReply());
            }
        });
        reply_rcy.setAdapter(mCommAdapter);
    }

    @Override
    public void doBusiness() {
        presenter.req.id = getIntent().getStringExtra("orderId");
        presenter.req.type = getIntent().getStringExtra("orderType");
        if (ToolUtils.isNull(presenter.req.id) || ToolUtils.isNull(presenter.req.type)) {
            finish();
            return;
        }

    }


    @Override
    public void doWakeUpBusiness() {
        presenter.getInfo();
    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void setInfo(OrderListBean orderDetailBean) {
        refreshLayout.finishRefresh();
        if (orderDetailBean != null) {
            presenter.orderDetailBean = orderDetailBean;
            try {
                if (ToolUtils.isNull(orderDetailBean.getResponder())) {
                    noresponser_ll.setVisibility(View.VISIBLE);
                    responser_ll.setVisibility(View.GONE);
                } else {
                    noresponser_ll.setVisibility(View.GONE);
                    responser_ll.setVisibility(View.VISIBLE);
                }

                GlideUtils.loadImageUserLogoDefult(getMContext(), orderDetailBean.getLawyerDetail().getAvatar(), userlogo_civ);
                name_tv.setText(orderDetailBean.getLawyerDetail().getReal_name());
                tag_tv.setText(orderDetailBean.getLawyerDetail().expertiseString);
                status_tv.setText(orderDetailBean.getStatus_text());
                content_title_tv.setText(orderDetailBean.getType_text());
                content_detail_tv.setText(orderDetailBean.getContent());
                if ("0".equals(orderDetailBean.getStatus())) {
                    bottom_ll.setVisibility(View.VISIBLE);
                } else {
                    bottom_ll.setVisibility(View.GONE);
                }
                popupToPayView = new PopupToPayView(getMContext(), presenter.orderDetailBean.getType());

                mCommAdapter.setData(orderDetailBean.replayList);

                tocancle_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupTipWithBtnView.showPopTipView(getMContext(), "取消订单", "确定取消该订单吗？",
                                new PopupTipWithBtnView.PopupTipWithBtnInterface() {
                                    @Override
                                    public void okClick() {
                                        presenter.doCancleOrder();
                                    }

                                    @Override
                                    public void cancleClick() {

                                    }

                                    @Override
                                    public void onDisimss() {

                                    }
                                }, v);
                    }
                });
                topay_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FOrderPayBean payBean = new FOrderPayBean();
                        payBean.sn = presenter.orderDetailBean.getSn();
                        payBean.price = presenter.orderDetailBean.getPrice();
//                        BusinessWapAct.openActForPay(getMContext(),payBean.sn,presenter.orderDetailBean.getType());
                        popupToPayView.show(v, payBean, new PopupToPayView.ToPayInterface() {
                            @Override
                            public void toPayFinish(FOrderPayBean fOrderPayBean) {
                                PaySuccessAct.openAct(getMContext(), fOrderPayBean);
                            }
//                            @Override
//                            public void alipayRequest() {
//
//                            }
//
//                            @Override
//                            public void paySuccess() {
//                                gotoActivity(PaySuccessAct.class);
//                            }
                        });
                    }
                });

            } catch (Exception e) {
            }
        }
    }

    @Override
    public void cancleResult(boolean b) {
        if (b) {
            EventBus.getDefault().post(new CEventUtils.CancleOrderEvent(presenter.orderDetailBean.getId(), presenter.orderDetailBean.getType()));
            finish();
        }
    }
}
