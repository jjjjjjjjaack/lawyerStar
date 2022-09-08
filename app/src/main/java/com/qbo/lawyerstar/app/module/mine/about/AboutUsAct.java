package com.qbo.lawyerstar.app.module.mine.about;

import android.content.Context;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.MyApplication;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class AboutUsAct extends MvpAct<IAboutUsView, BaseModel, AboutUsPresenter> implements IAboutUsView {

    @BindView(R.id.version_tv)
    TextView version_tv;

    @Override
    public AboutUsPresenter initPresenter() {
        return new AboutUsPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_about_us;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.about_us_tx1);
        version_tv.setText("当前版本: v"+ MyApplication.getApp().currentVersionName);
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
