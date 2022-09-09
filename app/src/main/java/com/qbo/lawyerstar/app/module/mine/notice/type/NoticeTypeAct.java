package com.qbo.lawyerstar.app.module.mine.notice.type;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.notice.list.NoticeListAct;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ToolUtils;
import q.rorbin.badgeview.QBadgeView;

public class NoticeTypeAct extends MvpAct<INoticeTypeView, BaseModel, NoticeTypePresenter> implements INoticeTypeView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.sys_rl)
    View sys_rl;
    @BindView(R.id.wtuo_rl)
    View wtuo_rl;
    @BindView(R.id.sysnum_v)
    View sysnum_v;
    @BindView(R.id.wtnum_v)
    View wtnum_v;

    @BindView(R.id.syscontent_tv)
    TextView syscontent_tv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.wtcontent_tv)
    TextView wtcontent_tv;
    @BindView(R.id.wtdate_tv)
    TextView wtdate_tv;

    QBadgeView sysNumQv;
    QBadgeView wtNumQv;

    @Override
    public NoticeTypePresenter initPresenter() {
        return new NoticeTypePresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_notice_type;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.notice_list_tx1);
        sys_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeListAct.openAct(getMContext(), "0");
            }
        });
        wtuo_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeListAct.openAct(getMContext(), "1");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getCount();
            }
        });
        refreshLayout.setEnableLoadMore(false);

        sysNumQv = new QBadgeView(this);
        sysNumQv.bindTarget(sysnum_v);
        wtNumQv = new QBadgeView(this);
        wtNumQv.setBadgeGravity(Gravity.CENTER | Gravity.TOP);
        wtNumQv.bindTarget(wtnum_v);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void doWakeUpBusiness() {
        presenter.getCount();
    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void getDataResult(boolean b) {
        refreshLayout.finishRefresh();
        if (b && presenter.typeBean != null) {
            syscontent_tv.setText(ToolUtils.isNull(presenter.typeBean.last_sys) ? "" : presenter.typeBean.last_sys);
            date_tv.setText(ToolUtils.isNull(presenter.typeBean.time_sys) ? "" : presenter.typeBean.time_sys);
            sysNumQv.setBadgeNumber(ToolUtils.String2Int(presenter.typeBean.sys_num));

            wtcontent_tv.setText(ToolUtils.isNull(presenter.typeBean.last_trust) ? "" : presenter.typeBean.last_trust);
            wtdate_tv.setText(ToolUtils.isNull(presenter.typeBean.time_trust) ? "" : presenter.typeBean.time_trust);
            wtNumQv.setBadgeNumber(ToolUtils.String2Int(presenter.typeBean.trust_num));
        }
    }
}
