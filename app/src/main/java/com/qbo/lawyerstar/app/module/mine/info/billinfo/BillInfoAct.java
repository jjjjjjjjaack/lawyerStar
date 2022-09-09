package com.qbo.lawyerstar.app.module.mine.info.billinfo;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class BillInfoAct extends MvpAct<IBillInfoView, BaseModel, BillInfoPresenter> implements IBillInfoView {

    @BindView(R.id.companyname_et)
    EditText companyname_et;
    @BindView(R.id.companynum_et)
    EditText companynum_et;
    @BindView(R.id.commit_tv)
    View commit_tv;

    @Override
    public BillInfoPresenter initPresenter() {
        return new BillInfoPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_bill_info;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.billinfo_tx1);

        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.doChangeBillInfo(companyname_et.getText().toString().trim(), companynum_et.getText().toString().trim());
            }
        });
    }

    @Override
    public void doBusiness() {
        FCacheUtils.getUserInfo(this, true, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                companyname_et.setText(userInfoBean.company_name);
                companynum_et.setText(userInfoBean.company_tax);
            }

            @Override
            public void fail() {
                finish();
            }
        });
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
    public void changeResult(boolean b) {
        if (b) {
            finish();
        }
    }
}
