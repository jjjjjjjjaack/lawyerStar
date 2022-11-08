package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.bean.FPayTypeBean;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;
import com.qbo.lawyerstar.app.module.mine.notice.bean.NoticeBean;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderListBean;
import com.qbo.lawyerstar.app.module.mine.order.bean.OrderTypeBean;
import com.qbo.lawyerstar.app.module.study.bean.ArticleBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import framework.mvp1.base.net.BaseResponse;
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
     * @param
     * @return
     * @description 获取律师列表
     * @author jiejack
     * @time 2022/9/10 11:05 下午
     */
    public static class GET_LAWYER_LIST_RES extends MPageResponse<LawyerBean> {
        @Override
        public Class<LawyerBean> getBeanType() {
            return LawyerBean.class;
        }
    }

    /**
     * @param
     * @return
     * @description 合同文库
     * @author jiejack
     * @time 2022/9/11 2:20 下午
     */
    public static class GET_CONTRACT_LIBRARY_LIST_RES extends MPageResponse<ContractLibBean> {
        @Override
        public Class<ContractLibBean> getBeanType() {
            return ContractLibBean.class;
        }
    }
    
    /**
     * @description
     * @param 
     * @return 
     * @author jieja
     * @time 2022/9/15 17:04
     */
    public static class GET_ORDER_LIST_RES extends MPageResponse<OrderListBean> {
        @Override
        public Class<OrderListBean> getBeanType() {
            return OrderListBean.class;
        }
    }


    /**
     * @description
     * @param
     * @return
     * @author jieja
     * @time 2022/9/15 17:04
     */
    public static class GET_ORDER_ALL_TYPE_RES extends MPageResponse<OrderTypeBean> {
        @Override
        public Class<OrderTypeBean> getBeanType() {
            return OrderTypeBean.class;
        }
    }
    /**
     * @description
     * @param
     * @return
     * @author jieja
     * @time 2022/9/15 17:04
     */
    public static class GET_PAYTYPE_LIST_RES extends MPageResponse<FPayTypeBean> {
        @Override
        public Class<FPayTypeBean> getBeanType() {
            return FPayTypeBean.class;
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


    public static class PostWechatLoginResponse extends BaseResponse {

        public String wx_openid;
        public String nickname;
        public String headimgurl;

        @Override
        public void fromJSON(String content) {
            super.fromJSON(content);
            fromJSONAuto(datas);
        }

        public String getWx_openid() {
            return wx_openid;
        }

        public void setWx_openid(String wx_openid) {
            this.wx_openid = wx_openid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }
    }


}
