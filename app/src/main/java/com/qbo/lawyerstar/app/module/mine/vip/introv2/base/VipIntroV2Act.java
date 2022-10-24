package com.qbo.lawyerstar.app.module.mine.vip.introv2.base;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.order.list.comm.frag.OrderListCommListFrag;
import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;
import com.qbo.lawyerstar.app.module.mine.vip.introv2.content.VipIntroV2ContentFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.PanelViewPagerAdapter;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class VipIntroV2Act extends MvpAct<IVipIntroV2View, BaseModel, VipIntroV2Presenter> implements IVipIntroV2View {

    public static void openAct(Context context, int pos) {
        Intent intent = new Intent(context, VipIntroV2Act.class);
        intent.putExtra("pos", pos);
        context.startActivity(intent);
    }

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String[] titles;
    ArrayList<Fragment> fragList = new ArrayList<>();

    int selectPos = 0;

    @Override
    public VipIntroV2Presenter initPresenter() {
        return new VipIntroV2Presenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_vip_intro_v2;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(getString(R.string.vip_intro_tx1_1));
    }

    @Override
    public void doBusiness() {
        selectPos = getIntent().getIntExtra("pos", 0);
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
    public void showInfo(List<VipIntroBean> beanList) {
        if (beanList != null && beanList.size() > 0) {
            titles = new String[beanList.size()];
            fragList.clear();
            for (int i = 0; i < beanList.size(); i++) {
                VipIntroBean b1 = beanList.get(i);
                titles[i] = b1.name;
                fragList.add(VipIntroV2ContentFrag.newInstance(b1));
            }
            viewpager.setAdapter(new PanelViewPagerAdapter(getSupportFragmentManager(), fragList));
            tablayout.setViewPager(viewpager, titles);
            if (selectPos < beanList.size()) {
                tablayout.setCurrentTab(selectPos, true);
            }
        }
    }
}
