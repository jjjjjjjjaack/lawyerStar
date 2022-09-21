package com.qbo.lawyerstar.app.module.pay.success;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.qbo.lawyerstar.R;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

import static framework.mvp1.base.constant.BROConstant.CLOSE_EXTRAACT_ACTION;

public class PaySuccessAct extends MvpAct<IPaySuccessView, BaseModel, PaySuccessPresenter> implements IPaySuccessView {

    @BindView(R.id.backhome_tv)
    TextView backhome_tv;

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
        backhome_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CLOSE_EXTRAACT_ACTION);
                intent.putExtra("CLOSE_EXARTACT_KEY", "VpMainAct");
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    public void doBusiness() {

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
}
