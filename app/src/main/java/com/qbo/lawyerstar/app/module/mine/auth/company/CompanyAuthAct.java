package com.qbo.lawyerstar.app.module.mine.auth.company;

import android.content.Context;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class CompanyAuthAct extends MvpAct<ICompanyAuthView, BaseModel, CompanyAuthPresenter> implements ICompanyAuthView {
    @Override
    public CompanyAuthPresenter initPresenter() {
        return new CompanyAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_company;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_company_tx1);
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
