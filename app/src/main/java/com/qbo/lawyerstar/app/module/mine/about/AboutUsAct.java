package com.qbo.lawyerstar.app.module.mine.about;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.MyApplication;
import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;

public class AboutUsAct extends MvpAct<IAboutUsView, BaseModel, AboutUsPresenter> implements IAboutUsView {

    @BindView(R.id.logo_iv)
    ImageView logo_iv;
    @BindView(R.id.intro_tv)
    TextView intro_tv;
    @BindView(R.id.contact_tv)
    TextView contact_tv;
    @BindView(R.id.name_tv)
    TextView name_tv;
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
        version_tv.setText("当前版本: v" + MyApplication.getApp().currentVersionName);
    }

    @Override
    public void doBusiness() {
        presenter.getInfo();
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
    public void showInfo(AboutUsBean bean) {
        if (bean != null) {
            GlideUtils.loadImageDefult(getMContext(),bean.android_logo,logo_iv);
            name_tv.setText(bean.android_name);
            intro_tv.setText(bean.android_intro);

            contact_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToolUtils.callPhone(getMContext(),bean.consumer_hotline);
                }
            });
        }
    }
}
