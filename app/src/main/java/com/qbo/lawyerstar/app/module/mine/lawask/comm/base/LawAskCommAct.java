package com.qbo.lawyerstar.app.module.mine.lawask.comm.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.lawask.comm.frag.LawAskCommListFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class LawAskCommAct extends MvpAct<ILawAskCommView, BaseModel, LawAskCommPresenter> implements ILawAskCommView {

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String[] titles;
    ArrayList<Fragment> fragList = new ArrayList<>();


    @Override
    public LawAskCommPresenter initPresenter() {
        return new LawAskCommPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_law_ask_comm;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        titles = new String[]{getString(R.string.law_ask_comm_tx1), getString(R.string.law_ask_comm_tx2)};
        fragList.add(LawAskCommListFrag.newInstance("0"));
        fragList.add(LawAskCommListFrag.newInstance("1"));
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragList.get(position);
            }

            @Override
            public int getCount() {
                return fragList.size();
            }
        });
        tablayout.setViewPager(viewpager,titles);
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
