package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;
import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import framework.mvp1.base.net.MPageResponse;

public class RES_Factory {

    /**
     * @description 学法新闻列表
     * @param 
     * @return 
     * @author jieja
     * @time 2022/9/9 14:31
     */
    public static class GET_LAWSTUDY_LIST_DATA_RES extends MPageResponse<ArticleBean> {
        @Override
        public Class<ArticleBean> getBeanType() {
            return ArticleBean.class;
        }
    }

    /**
     * @description 消息列表
     * @author jieja
     * @time 2022/9/9 14:30
     */
    public static class GET_NOTICE_LIST_RES extends MPageResponse<NoticeBean> {
        @Override
        public Class<NoticeBean> getBeanType() {
            return NoticeBean.class;
        }
    }

    
    /**
     * @description 数据字典
     * @param 
     * @return 
     * @author jieja
     * @time 2022/9/9 14:30
     */
    public static class GET_INDEX_DICTIONARY_RES extends MPageResponse<IndexDictionaryUtils.ValueBean> {
        @Override
        public Class<IndexDictionaryUtils.ValueBean> getBeanType() {
            return IndexDictionaryUtils.ValueBean.class;
        }
    }

}
