package com.qbo.lawyerstar.app.module.mine.notice.list;

import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface INoticeListView extends BaseView {
    void loadDataResult(boolean isRefresh, List<NoticeBean> articleBeans);
}
