package com.qbo.lawyerstar.app.module.study.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.study.list.LawStudyListFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.StatusBarUtils;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class LawStudyAct extends MvpAct<ILawStudyView, BaseModel, LawStudyPresenter> implements ILawStudyView {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    private String[] titles;
    private List<Fragment> fragments;

    @Override
    public LawStudyPresenter initPresenter() {
        return new LawStudyPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_law_study;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle("普法文章");

        titles = new String[]{getString(R.string.law_study_frag_tx1),
                getString(R.string.law_study_frag_tx2),
                getString(R.string.law_study_frag_tx3),
                getString(R.string.law_study_frag_tx4)};
        fragments = new ArrayList<>();
        fragments.add(LawStudyListFrag.newInstance("0"));
        fragments.add(LawStudyListFrag.newInstance("5"));
        fragments.add(LawStudyListFrag.newInstance("6"));
        fragments.add(LawStudyListFrag.newInstance("7"));
        viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tablayout.setViewPager(viewpager, titles);
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
