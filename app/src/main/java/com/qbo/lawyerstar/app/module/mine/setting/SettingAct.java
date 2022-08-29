package com.qbo.lawyerstar.app.module.mine.setting;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.LoginoutUtis;
import framework.mvp1.base.util.ToolUtils;

public class SettingAct extends MvpAct<ISettingView, BaseModel, SettingPresenter> implements ISettingView {

    @BindView(R.id.logout_tv)
    View logout_tv;

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

        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginoutUtis.getInstance().doLogOut(getMContext());
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
    public SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }
}
