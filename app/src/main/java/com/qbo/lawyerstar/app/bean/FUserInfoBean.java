package com.qbo.lawyerstar.app.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.util.ToolUtils;

public class FUserInfoBean extends BaseBean {
    public String avatar;
    public String nick_name;
    public String user_name;
    public String mobile;
    public String user_type;//会员身份 0 个人 1 企业 2 律师
    public String userinfo_type_tx;//会员身份 0 个人 1 企业 2 律师
    public String is_rz;//是否认证
    public String city_name;
    public String company_name;
    public String company_tax;
    public String check_user_type; // 1企业 2律师
    public String audis_status;
    public String rank_id;
    public String rank_date;
    public String shop_balance;
    public RankInfo rank;
    public String userid;
    public String rank_img;

    @Override
    public void fromJSONAuto(JSONObject json) {
        super.fromJSONAuto(json);
        if("1".equals(user_type)){
            userinfo_type_tx = "企业用户";
        }else if("2".equals(user_type)){
            userinfo_type_tx = "律师用户";
        }else{
            userinfo_type_tx = "个人用户";
        }
//        if (ToolUtils.isNull(check_user_type)) {
//            userinfo_type_tx = "个人用户";
//        } else {
//            if ("-1".equals(audis_status)) {
//                userinfo_type_tx = ""
//            }
//
//
//        }

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRank_img() {
        return rank_img;
    }

    public void setRank_img(String rank_img) {
        this.rank_img = rank_img;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getIs_rz() {
        return is_rz;
    }

    public void setIs_rz(String is_rz) {
        this.is_rz = is_rz;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_tax() {
        return company_tax;
    }

    public void setCompany_tax(String company_tax) {
        this.company_tax = company_tax;
    }

    public String getCheck_user_type() {
        return check_user_type;
    }

    public void setCheck_user_type(String check_user_type) {
        this.check_user_type = check_user_type;
    }

    public String getAudis_status() {
        return audis_status;
    }

    public void setAudis_status(String audis_status) {
        this.audis_status = audis_status;
    }

    public String getUserinfo_type_tx() {
        return userinfo_type_tx;
    }

    public void setUserinfo_type_tx(String userinfo_type_tx) {
        this.userinfo_type_tx = userinfo_type_tx;
    }

    public String getRank_id() {
        return rank_id;
    }

    public void setRank_id(String rank_id) {
        this.rank_id = rank_id;
    }

    public RankInfo getRank() {
        return rank;
    }

    public void setRank(RankInfo rank) {
        this.rank = rank;
    }

    public String getRank_date() {
        return rank_date;
    }

    public void setRank_date(String rank_date) {
        this.rank_date = rank_date;
    }

    public String getShop_balance() {
        return shop_balance;
    }

    public void setShop_balance(String shop_balance) {
        this.shop_balance = shop_balance;
    }

    public static class RankInfo extends BaseBean{

        /**
         * update_time : 2022-08-27 11:53:14
         * valid_date : 12
         * create_time : 2022-08-27 11:53:14
         * price : 10
         * is_valid : 0
         * intro : &lt;table style="width:100%;" cellpadding="2" cellspacing="0" border="1"&gt;
         &lt;tbody&gt;
         &lt;tr&gt;
         &lt;td style="background-color:#337FE5;"&gt;
         &lt;span style="color:#FFFFFF;"&gt;项目明细&lt;/span&gt;&lt;span style="display:none;"&gt;&lt;/span&gt;&lt;span style="color:#FFFFFF;"&gt; &lt;/span&gt;&lt;span style="display:none;"&gt;&lt;span style="color:#FFFFFF;"&gt;&lt;/span&gt;&lt;span style="color:#FFFFFF;"&gt;&lt;/span&gt;&lt;/span&gt;
         &lt;/td&gt;
         &lt;td style="background-color:#337FE5;"&gt;
         &lt;span style="color:#FFFFFF;"&gt;服务明细&lt;/span&gt;&lt;span style="color:#FFFFFF;"&gt;    &lt;/span&gt;
         &lt;/td&gt;
         &lt;td style="background-color:#337FE5;"&gt;
         &lt;span style="color:#FFFFFF;"&gt;服务时效&lt;/span&gt;
         &lt;/td&gt;
         &lt;td style="background-color:#337FE5;"&gt;
         * name : 会员
         * id : 3
         * pic : [{"path":"/static/upload/image/20220827/fe62b14f9c07684a9f93a7dc2e9d4244.png","url":"http://211.149.225.244:9010//static/upload/image/20220827/fe62b14f9c07684a9f93a7dc2e9d4244.png"}]
         * detail : [{"":"","service_time":"","service_num":"","memo":"","id":1}]
         */

        private String update_time;
        private String valid_date;
        private String create_time;
        private String price;
        private String is_valid;
        private String intro;
        private String name;
        private String id;
        private List<PicBean> pic;
        private List<DetailBean> detail;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getValid_date() {
            return valid_date;
        }

        public void setValid_date(String valid_date) {
            this.valid_date = valid_date;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIs_valid() {
            return is_valid;
        }

        public void setIs_valid(String is_valid) {
            this.is_valid = is_valid;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }



        public static class PicBean extends BaseBean {
            /**
             * path : /static/upload/image/20220827/fe62b14f9c07684a9f93a7dc2e9d4244.png
             * url : http://211.149.225.244:9010//static/upload/image/20220827/fe62b14f9c07684a9f93a7dc2e9d4244.png
             */

            private String path;
            private String url;

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class DetailBean extends BaseBean {
            /**
             *  :
             * service_time :
             * service_num :
             * memo :
             * id : 1
             */

            private String service_time;
            private String service_num;
            private String memo;
            private String id;

            public String getService_time() {
                return service_time;
            }

            public void setService_time(String service_time) {
                this.service_time = service_time;
            }

            public String getService_num() {
                return service_num;
            }

            public void setService_num(String service_num) {
                this.service_num = service_num;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

    public boolean isVip(){
        return !ToolUtils.isNull(rank_id);
    }
}
