package com.qbo.lawyerstar.app.module.mine.order.list.all;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderTypeBean;
import com.qbo.lawyerstar.app.module.mine.order.list.comm.frag.OrderListCommListFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.adapter.PanelViewPagerAdapter;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ResourceUtils;

public class AllOrderListAct extends MvpAct<IAllOrderListView, BaseModel, AllOrderListPresenter> implements IAllOrderListView {

    @BindView(R.id.ordertype_rcv)
    RecyclerView ordertype_rcv;
    private MCommAdapter orderTypeAdapter;

    public OrderTypeBean selectBean;

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String[] titles;
    ArrayList<Fragment> fragList = new ArrayList<>();

    @Override
    public AllOrderListPresenter initPresenter() {
        return new AllOrderListPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_order_list_all;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle("我的订单");
        ordertype_rcv.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.HORIZONTAL, false));
        orderTypeAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<OrderTypeBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_ordertype_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, OrderTypeBean bean) {
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mCommVH.itemView.getLayoutParams();
                if (position == mCommVH.adapter.getBeanList().size() - 1) {
                    lp.rightMargin = ResourceUtils.dip2px2(context, 24);
                    lp.leftMargin = ResourceUtils.dip2px2(context, 24);
                } else {
                    lp.rightMargin = 0;
                    lp.leftMargin = ResourceUtils.dip2px2(context, 24);
                }

                mCommVH.setText(R.id.name_tv, bean.label);
                if (selectBean != null && selectBean.value.equals(bean.value)) {
                    mCommVH.setViewSelect(R.id.name_tv, true);
                } else {
                    mCommVH.setViewSelect(R.id.name_tv, false);
                }
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectBean = bean;
                        initFragment();
                        mCommVH.adapter.notifyDataSetChanged();
                    }
                });

            }
        });
        ordertype_rcv.setAdapter(orderTypeAdapter);
    }

    @Override
    public void doBusiness() {
        presenter.getOrderType();
    }

    public void initFragment() {
        if (selectBean == null) {
            return;
        }
        titles = new String[]{getString(R.string.law_ask_comm_tx1), getString(R.string.law_ask_comm_tx2)};
        fragList.clear();
        fragList.add(OrderListCommListFrag.newInstance(selectBean.value, "1"));
        fragList.add(OrderListCommListFrag.newInstance(selectBean.value, "2"));
        viewpager.setAdapter(new PanelViewPagerAdapter(getSupportFragmentManager(),fragList));
        tablayout.setViewPager(viewpager, titles);
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
    public void getTypeResult(List<OrderTypeBean> orderTypeBeanList) {
        if (orderTypeBeanList != null && orderTypeBeanList.size() > 0) {
            selectBean = orderTypeBeanList.get(0);
            initFragment();
            orderTypeAdapter.setData(orderTypeBeanList);
        }
    }
}
