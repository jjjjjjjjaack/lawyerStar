package com.qbo.lawyerstar.app.module.mine.auth.lawyer;

import android.content.Context;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class LawyerAuthAct extends MvpAct<ILawyerAuthView, BaseModel, LawyerAuthPresenter> implements ILawyerAuthView {


    @Override
    public LawyerAuthPresenter initPresenter() {
        return new LawyerAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_lawyer;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_lawyer_tx1);
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
