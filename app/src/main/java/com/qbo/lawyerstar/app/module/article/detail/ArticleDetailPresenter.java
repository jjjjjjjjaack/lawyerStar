package com.qbo.lawyerstar.app.module.article.detail;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class ArticleDetailPresenter extends BasePresent<IArticleDetailView, BaseModel> {
    String article_id;

    public void getDetail(String article_id) {
        GET_LAWSTUDY_ARTICLE_DETAIL_REQ req = new GET_LAWSTUDY_ARTICLE_DETAIL_REQ();
        req.id = article_id;
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, ArticleBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public ArticleBean doMap(BaseResponse baseResponse) {
                ArticleBean bean = ArticleBean.fromJSONAuto(baseResponse.datas, ArticleBean.class);
                return bean;
            }

            @Override
            public void onSuccess(ArticleBean bean) throws Exception {
                view().showDetail(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
