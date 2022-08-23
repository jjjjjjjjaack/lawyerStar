package com.qbo.lawyerstar.app.module.study.list;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface ILawStudyListView extends BaseView {
    void loadDataResult(boolean isRefresh, List<ArticleBean> articleBeans);
}
