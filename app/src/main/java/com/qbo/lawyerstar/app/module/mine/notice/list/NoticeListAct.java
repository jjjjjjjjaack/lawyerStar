package com.qbo.lawyerstar.app.module.mine.notice.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ToolUtils;

public class NoticeListAct extends MvpAct<INoticeListView, BaseModel, NoticeListPresenter> implements INoticeListView {

    public static void openAct(Context context, String type) {
        Intent intent = new Intent(context, NoticeListAct.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;

    public MCommAdapter mCommAdapter;

    @Override
    public NoticeListPresenter initPresenter() {
        return new NoticeListPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_notice_list;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.notice_list_tx1);

        rcy.setLayoutManager(new LinearLayoutManager(getMContext()));
        mCommAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<NoticeBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_notice_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, NoticeBean bean) {

            }
        });
        rcy.setAdapter(mCommAdapter);
    }

    @Override
    public void doBusiness() {
        String type = getIntent().getStringExtra("type");
        if (ToolUtils.isNull(type)) {
            finish();
            return;
        }
        presenter.req.type = type;
        presenter.getDataList(true);
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
    public void loadDataResult(boolean isRefresh, List<NoticeBean> noticeBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (isRefresh) {
            if (noticeBeans != null) {
                mCommAdapter.setData(noticeBeans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (noticeBeans != null) {
                mCommAdapter.addData(noticeBeans);
            }
        }
    }
}
