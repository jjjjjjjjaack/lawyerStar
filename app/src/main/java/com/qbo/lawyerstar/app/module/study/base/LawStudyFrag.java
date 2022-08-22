package com.qbo.lawyerstar.app.module.study.base;

import android.content.Context;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;

public class LawStudyFrag extends MvpFrag<ILawStudyView, BaseModel, LawStudyPresenter> implements ILawStudyView {
    @Override
    public LawStudyPresenter initPresenter() {
        return new LawStudyPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_law_study;
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
