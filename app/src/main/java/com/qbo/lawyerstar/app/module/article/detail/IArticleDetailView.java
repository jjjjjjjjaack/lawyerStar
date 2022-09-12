package com.qbo.lawyerstar.app.module.article.detail;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;

import framework.mvp1.base.f.BaseView;

public interface IArticleDetailView extends BaseView {
    void showDetail(ArticleBean bean);
}
