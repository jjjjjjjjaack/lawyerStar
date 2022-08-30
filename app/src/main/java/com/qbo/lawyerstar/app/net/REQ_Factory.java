package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.annotation.AnnBaseReq;
import framework.mvp1.base.annotation.AnnReqPara;
import framework.mvp1.base.net.BaseRXNetApi;
import framework.mvp1.base.net.BaseRequest;

/**
 * 请求接口基类
 */
public class REQ_Factory {

    @AnnBaseReq(API_METHOD = "http://api.bq04.com/apps/latest/" + "630c2d34f945482ab429a2c3", RXExecuteType = BaseRXNetApi.RXExecuteType.GET, needHandleResponse = false)
    public static class POST_CHECK_APPVERSION_REQ extends BaseRequest {
        public POST_CHECK_APPVERSION_REQ(String api_token) {
            this.api_token = api_token;
        }

        public String api_token;
    }

    /**
     * @param
     * @author jieja
     * @description 获取数据字典
     * @return
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "index/dictionary",RESPONSE_CLASS = RES_Factory.GET_INDEX_DICTIONARY_RES.class)
    public static class GET_INDEX_DICTIONARY_REQ extends BaseRequest {
       public String key;


        public GET_INDEX_DICTIONARY_REQ(String key) {
            this.key = key;
        }
    }
    /**
     * @param
     * @author jieja
     * @description 获取首页数据
     * @return
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "index/getIndex")
    public static class GET_HOMEPAGE_DATA_REQ extends BaseRequest {

    }

    /**
     * @param
     * @author jieja
     * @description 发送验证码
     * @return
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "index/sendCode")
    public static class POST_SEND_CODE_REQ extends BaseRequest {
        public String type;//"grant:发放 reg:注册\/登录 bind: 绑定"
        @AnnReqPara(isCheckNull = true, nullRTip = R.string.login_tx3)
        public String mobile;//
    }

    /**
     * @param
     * @author jieja
     * @description 登录
     * @return
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "index/login")
    public static class POST_LOGIN_REQ extends BaseRequest {
        public String account;//
        @AnnReqPara(isCheckNull = true, nullRTip = R.string.login_tx4)
        public String code;//验证码
        public String type;//类型 手机登录时:0；微信:1；苹果登录:2
        public String wechatCode;
    }

    /**
     * @param
     * @author jieja
     * @description 获取用户信息
     * @return
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "index/memberInfo")
    public static class GET_USERINFO_REQ extends BaseRequest {

    }
    
    /**
     * @description 切换身份
     * @param 
     * @return 
     * @author jieja
     * @time 2022/8/29 10:53
     */
    @AnnBaseReq(API_METHOD = "user/changeUserType")
    public static class POST_CHANGEUSER_TYPE_REQ extends BaseRequest {
       public String type;//"type": "0 个人 1 企业 2 律师"
    }

    
    /**
     * @description 获取文章
     * @param 
     * @return 
     * @author jieja
     * @time 2022/8/29 10:52
     */
    @AnnBaseReq(API_METHOD = "lawyer/article/page", RESPONSE_CLASS = RES_Factory.GET_LAWSTUDY_LIST_DATA_RES.class)
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
