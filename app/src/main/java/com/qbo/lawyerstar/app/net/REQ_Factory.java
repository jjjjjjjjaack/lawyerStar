package com.qbo.lawyerstar.app.net;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.ImagePathBean;

import java.util.ArrayList;
import java.util.List;

import framework.mvp1.base.annotation.AnnBaseReq;
import framework.mvp1.base.annotation.AnnReqPara;
import framework.mvp1.base.bean.BaseBean;
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
    @AnnBaseReq(API_METHOD = "index/dictionary", RESPONSE_CLASS = RES_Factory.GET_INDEX_DICTIONARY_RES.class)
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
    @AnnBaseReq(API_METHOD = "upload/index", RXExecuteType = BaseRXNetApi.RXExecuteType.MUTLI_POST)
    public static class POST_UPLOAD_FILE_REQ extends BaseRequest {
        public String path;
        public String theme;
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
     * @return
     * @description
     * @author jiejack
     * @time 2022/9/14 9:25 下午
     */
    @AnnBaseReq(API_METHOD = "index/wapPage")
    public static class GET_WAPPAGE_URL_REQ extends BaseRequest {

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
        //        @AnnReqPara(isCheckNull = true, nullRTip = R.string.login_tx3)
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
     * @param
     * @author jieja
     * @description 切换身份
     * @return
     * @time 2022/8/29 10:53
     */
    @AnnBaseReq(API_METHOD = "user/changeUserType")
    public static class POST_CHANGEUSER_TYPE_REQ extends BaseRequest {
        public String type;//"type": "0 个人 1 企业 2 律师"
    }

    /**
     * @param
     * @author jieja
     * @description 编辑用户信息
     * @return
     * @time 2022/9/8 16:13
     */
    @AnnBaseReq(API_METHOD = "user/edit")
    public static class POST_EDIT_USERINFO_REQ extends BaseRequest {
        public String nick_name;//
    }


    /**
     * @param
     * @author jieja
     * @description 更换头像
     * @return
     * @time 2022/9/8 16:33
     */
    @AnnBaseReq(API_METHOD = "user/setAvatar", RXExecuteType = BaseRXNetApi.RXExecuteType.MUTLI_POST)
    public static class POST_CHANGE_USERAVATAR_REQ extends BaseRequest {
        public String path = "avatar";
    }

    /**
     * @param
     * @author jieja
     * @description 修改开票信息
     * @return
     * @time 2022/9/9 11:42
     */
    @AnnBaseReq(API_METHOD = "user/modifyBillInfo")
    public static class POST_CHANGE_USER_BILLINFO_REQ extends BaseRequest {
        @AnnReqPara(isCheckNull = true, nullRTip = R.string.billinfo_tx2_1)
        public String name;
        @AnnReqPara(isCheckNull = true, nullRTip = R.string.billinfo_tx3_1)
        public String tax_num;
    }


    /**
     * @param
     * @author jieja
     * @description 获取文章
     * @return
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


    /**
     * @param
     * @author jiejack
     * @return
     * @description
     * @time 2022/9/11 8:38 下午
     */
    @AnnBaseReq(API_METHOD = "lawyer/article/info")
    public static class GET_LAWSTUDY_ARTICLE_DETAIL_REQ extends BaseRequest {
        public String id;
    }


    @AnnBaseReq(API_METHOD = "suggestions/create")
    public static class POST_CREATE_SUGGEST_REQ extends BaseRequest {
        public String category_labels;
        public String content;
        public List<ImagePathBean> image = new ArrayList<>();


    }

    /**
     * @param
     * @author jiejack
     * @return
     * @description 创建个人认证
     * @time 2022/9/4 3:21 下午
     */
    @AnnBaseReq(API_METHOD = "user/auth/create")
    public static class POST_AUTH_PERSONAL_REQ extends BaseRequest {
        public String sex;
        public String real_name;
        public List<ImagePathBean> avatar = new ArrayList<>();
        public List<String> address_info;
    }


    /**
     * @param
     * @author jieja
     * @description 个人认证详情
     * @return
     * @time 2022/9/7 16:10
     */
    @AnnBaseReq(API_METHOD = "user/auth/info")
    public static class GET_AUTH_PERSONAL_DETAILINFO_REQ extends BaseRequest {

    }


    /**
     * @param
     * @author jiejack
     * @return
     * @description 创建律师认证
     * @time 2022/9/4 3:22 下午
     */
    @AnnBaseReq(API_METHOD = "user/attorney/auth/create")
    public static class POST_AUTH_LAWYER_REQ extends BaseRequest {
        public String sex;
        public String real_name;
        public String expertise;//擅长领域
        public String employment_year;//从年年数
        public String intro;//个人介绍
        public List<ImagePathBean> avatar = new ArrayList<>();
        public List<ImagePathBean> lawyer_license = new ArrayList<>();
        public List<String> address_info;
    }

    /**
     * @param
     * @author jieja
     * @description 律师认证详情
     * @return
     * @time 2022/9/7 15:11
     */
    @AnnBaseReq(API_METHOD = "user/attorney/auth/info")
    public static class GET_AUTH_LAWYER_DETAILINFO_REQ extends BaseRequest {

    }

    /**
     * @param
     * @author jieja
     * @description 律师认证编辑
     * @return
     * @time 2022/9/7 15:38
     */
    @AnnBaseReq(API_METHOD = "user/attorney/auth/edit")
    public static class POST_AUTH_LAWYER_EDIT_REQ extends BaseRequest {

    }


    /**
     * @param
     * @author jiejack
     * @return
     * @description
     * @time 2022/9/4 9:08 下午
     */
    @AnnBaseReq(API_METHOD = "user/company/auth/create")
    public static class POST_AUTH_COMPANY_REQ extends BaseRequest {
        public String company_name;
        public String address;
        public String unified_code;
        public String legal_person;
        public String responsible_mobile;
        public String industry;
        public String enterprise_size;
        public String established_date;
        public List<ImagePathBean> avatar = new ArrayList<>();
        public List<ImagePathBean> business_license = new ArrayList<>();
        public List<String> address_info;
    }


    /**
     * @param
     * @author jieja
     * @description 企业认证详情
     * @return
     * @time 2022/9/7 15:11
     */
    @AnnBaseReq(API_METHOD = "user/company/auth/info")
    public static class GET_AUTH_COMPANY_DETAILINFO_REQ extends BaseRequest {

    }

    /**
     * @param
     * @author jieja
     * @description
     * @return
     * @time 2022/9/8 14:48
     */
    @AnnBaseReq(API_METHOD = "user/cancel/create")
    public static class POST_DO_CACNEL_ACCOUNT_REQ extends BaseRequest {
        public String code;

    }

    /**
     * @param
     * @author jieja
     * @description 省市区数据
     * @return
     * @time 2022/9/8 10:41
     */
    @AnnBaseReq(API_METHOD = "city/page")
    public static class GET_CITY_DATA_REQ extends BaseRequest {

    }


    /**
     * @param
     * @author jieja
     * @description 协议文本
     * @return
     * @time 2022/9/8 10:41
     */
    @AnnBaseReq(API_METHOD = "lawyer/article/protocol")
    public static class GET_PROTOCOL_INFO_REQ extends BaseRequest {
        public String type;//类型 privacy 隐私协议 user 用户协议 cancel 注销协议 lawyer 律师协议
    }


    /**
     * @param
     * @author jieja
     * @description 消息列表
     * @return
     * @time 2022/9/9 13:49
     */
    @AnnBaseReq(API_METHOD = "message/user/page", RESPONSE_CLASS = RES_Factory.GET_NOTICE_LIST_RES.class)
    public static class GET_NOTICE_LIST_REQ extends BaseRequest {
        public int pageNo = 1;
        public int pageSize = 10;
        public String type;//"消息类型 委托消息 1 系统消息 0"
    }

    /**
     * @param
     * @author jieja
     * @description 消息数量
     * @return
     * @time 2022/9/9 14:20
     */
    @AnnBaseReq(API_METHOD = "message/user/messageCount")
    public static class GET_NOTICE_MSGCOUNT_REQ extends BaseRequest {
    }


    /**
     * @param
     * @author jiejack
     * @return
     * @description 获取律师列表
     * @time 2022/9/10 11:04 下午
     */
    @AnnBaseReq(API_METHOD = "lawyer/manage/page", RESPONSE_CLASS = RES_Factory.GET_LAWYER_LIST_RES.class)
    public static class GET_LAWYER_LIST_REQ extends BaseRequest {
        public int pageNo = 1;
        public int pageSize = 10;
        public String search = "";
        public Filter filter = new Filter();

        public static class Filter {
            public List<Integer> address_info = new ArrayList<>();
            public String employment_year;//"执业年限 字典--LawyerYears"
        }
    }

    /**
     * @param
     * @author jiejack
     * @return
     * @description 获取律师详情
     * @time 2022/9/14 8:46 下午
     */
    @AnnBaseReq(API_METHOD = "lawyer/manage/info")
    public static class GET_LAWYER_DETAIL_INFO_REQ extends BaseRequest {
        public String id;
    }


    @AnnBaseReq(API_METHOD = "contract/library/page", RESPONSE_CLASS = RES_Factory.GET_CONTRACT_LIBRARY_LIST_RES.class)
    public static class GET_CONTRACT_LIBRARY_LIST_REQ extends BaseRequest {
        public int pageNo = 1;
        public int pageSize = 10;
        public String search = "";
        public Filter filter = new Filter();

        public static class Filter {
            public String type;//合同类别 字典--ContractLibraryType",
            public String industry;//"行业 字典--Industry"
        }
    }

    /**
     * @param
     * @author jiejack
     * @return
     * @description 关于我们
     * @time 2022/9/13 7:58 下午
     */
    @AnnBaseReq(API_METHOD = "index/getAppInfo")
    public static class GET_ABOUT_US_INFO_REQ extends BaseRequest {
    }


//    public static class ImagePath extends BaseBean {
//        public String path;
//        public String url;
//
//        public ImagePath(String path, String url) {
//            this.path = path;
//            this.url = url;
//        }
//    }


}
