package com.qbo.lawyerstar.app.net;

import framework.mvp1.base.annotation.AnnBaseReq;
import framework.mvp1.base.net.BaseRXNetApi;
import framework.mvp1.base.net.BaseRequest;

/**
 * 请求接口基类
 */
public class REQ_Factory {

    @AnnBaseReq(API_METHOD = "http://api.bq04.com/apps/latest/" + "", RXExecuteType = BaseRXNetApi.RXExecuteType.GET, needHandleResponse = false)
    public static class POST_CHECK_APPVERSION_REQ extends BaseRequest {
        public POST_CHECK_APPVERSION_REQ(String api_token) {
            this.api_token = api_token;
        }

        public String api_token;
    }


    @AnnBaseReq(API_METHOD = "index/getIndex", RXExecuteType = BaseRXNetApi.RXExecuteType.POST)
    public static class GET_HOMEPAGE_DATA_REQ extends BaseRequest {
    }


    @AnnBaseReq(API_METHOD = "lawyer/article/page", RXExecuteType = BaseRXNetApi.RXExecuteType.POST, RESPONSE_CLASS = RES_Factory.GET_LAWSTUDY_LIST_DATA_RES.class)
    public static class GET_LAWSTUDY_LIST_DATA_REQ extends BaseRequest {
        public int pageNo = 1;
        public int pageSize = 10;
        public Filter filter = new Filter();

        public static class Filter {
            public String is_rec;// "推荐 1",
            public String article_clazz;//"文章分类 5:法律头条    6:经典案例    7:法律释疑"
        }

    }

}
