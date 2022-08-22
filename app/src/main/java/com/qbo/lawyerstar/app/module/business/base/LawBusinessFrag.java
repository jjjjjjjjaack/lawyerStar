package com.qbo.lawyerstar.app.module.business.base;

import android.content.Context;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.study.base.ILawStudyView;
import com.qbo.lawyerstar.app.module.study.base.LawStudyPresenter;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;

public class LawBusinessFrag extends MvpFrag<ILawBusinessView, BaseModel, LawBusinessPresenter> implements ILawBusinessView {
    @Override
    public LawBusinessPresenter initPresenter() {
        return new LawBusinessPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_law_business;
    }

    @Override
    public void viewInitialization() {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWakeBussiness() {

    }

    @Override
    public Context getMContext() {
        return getContext();
    }
}
