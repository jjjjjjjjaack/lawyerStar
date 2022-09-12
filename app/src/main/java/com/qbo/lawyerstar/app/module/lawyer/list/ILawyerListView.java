package com.qbo.lawyerstar.app.module.lawyer.list;

import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface ILawyerListView extends BaseView {
    void loadDataResult(boolean isRefresh, List<LawyerBean> lawyerBeans);
}
