package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;

import framework.mvp1.base.net.MPageResponse;

public class RES_Factory {

    public static class GET_LAWSTUDY_LIST_DATA_RES extends MPageResponse<ArticleBean> {
        @Override
        public Class<ArticleBean> getBeanType() {
            return ArticleBean.class;
        }
    }

}
