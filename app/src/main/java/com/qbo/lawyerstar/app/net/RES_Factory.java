package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import framework.mvp1.base.net.MPageResponse;

public class RES_Factory {

    public static class GET_LAWSTUDY_LIST_DATA_RES extends MPageResponse<ArticleBean> {
        @Override
        public Class<ArticleBean> getBeanType() {
            return ArticleBean.class;
        }
    }

    public static class GET_INDEX_DICTIONARY_RES extends MPageResponse<IndexDictionaryUtils.ValueBean> {
        @Override
        public Class<IndexDictionaryUtils.ValueBean> getBeanType() {
            return IndexDictionaryUtils.ValueBean.class;
        }
    }

}
