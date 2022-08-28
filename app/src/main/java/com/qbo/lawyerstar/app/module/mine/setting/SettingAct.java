package com.qbo.lawyerstar.app.module.mine.setting;

import android.content.Context;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class SettingAct extends MvpAct<ISettingView, BaseModel, SettingPresenter> implements ISettingView {
    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_setting;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_setting_tx1);
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
    public SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }
}
