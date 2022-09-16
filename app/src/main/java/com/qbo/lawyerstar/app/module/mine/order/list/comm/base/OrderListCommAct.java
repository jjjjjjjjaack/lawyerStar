package com.qbo.lawyerstar.app.module.mine.order.list.comm.base;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.lawask.comm.base.ILawAskCommView;
import com.qbo.lawyerstar.app.module.mine.lawask.comm.base.LawAskCommPresenter;
import com.qbo.lawyerstar.app.module.mine.lawask.comm.frag.LawAskCommListFrag;
import com.qbo.lawyerstar.app.module.mine.order.list.comm.frag.OrderListCommListFrag;

import java.util.ArrayList;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ToolUtils;

public class OrderListCommAct extends MvpAct<IOrderListCommView, BaseModel, OrderListCommPresenter> implements IOrderListCommView {


    /**
     * @description
     * @param orderType
     * 合同文库 contract_documents
     * 代写文书 ghostwriting
     * 律师函 lawyer_letter
     * 法律咨询 legal_advice
     * 合同定制 contract_customization
     * 合同审核 contract_review
     * 非诉/催告 non_appeal
     * 仲裁/诉讼 arbitrate_litigate
     * @return
     * @author jieja
     * @time 2022/9/16 16:34
     */
    public static void openAct(Context context, String orderType) {
        Intent intent = new Intent(context, OrderListCommAct.class);
        intent.putExtra("orderType", orderType);
        context.startActivity(intent);
    }

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String[] titles;
    ArrayList<Fragment> fragList = new ArrayList<>();


    @Override
    public OrderListCommPresenter initPresenter() {
        return new OrderListCommPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_order_list_comm;
    }

    @Override
    public void viewInitialization() {
        String orderType = getIntent().getStringExtra("orderType");
        if (ToolUtils.isNull(orderType)) {
            finish();
            return;
        }
        setBackPress();
        titles = new String[]{getString(R.string.law_ask_comm_tx1), getString(R.string.law_ask_comm_tx2)};
        fragList.add(OrderListCommListFrag.newInstance(orderType, "1"));
        fragList.add(OrderListCommListFrag.newInstance(orderType, "2"));
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
