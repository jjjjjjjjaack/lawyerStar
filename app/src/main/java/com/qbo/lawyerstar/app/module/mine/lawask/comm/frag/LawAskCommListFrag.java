package com.qbo.lawyerstar.app.module.mine.lawask.comm.frag;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpFrag;

public class LawAskCommListFrag extends MvpFrag<ILawAskCommListView, BaseModel, LawAskCommListPresenter> implements ILawAskCommListView {

    public static LawAskCommListFrag newInstance(String type) {
        LawAskCommListFrag frag = new LawAskCommListFrag();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        frag.setArguments(bundle);
        return frag;
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;

    @Override
    public LawAskCommListPresenter initPresenter() {
        return new LawAskCommListPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_law_ask_commlist;
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
