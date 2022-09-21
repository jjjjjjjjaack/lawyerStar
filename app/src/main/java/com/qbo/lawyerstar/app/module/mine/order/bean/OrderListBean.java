package com.qbo.lawyerstar.app.module.mine.order.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class OrderListBean extends BaseBean {
    /**
     * create_time : 2022-09-16 08:59:18
     * is_vip : 0
     * type_text : 非诉讼文书
     * mobile : 13123456789
     * email_text : Jugs3juHJ+pYa0103Ef2gQ== rc0FFlAFcMH1Sn1F+q4p/Q== jrkuelJwmPldBUd/AJmtpA==
     * type : ghostwriting
     * title : 代写文书
     * content : Ggggg
     * update_time : 2022-09-16 08:59:18
     * lawyerDetail : {"score_star":0,"real_name":"","id":0,"avatar":"","expertise":[]}
     * user_id : 27
     * price : 120.00
     * id : 1036
     * sn : OG220916789754
     * mobile_text : NjWFjjxjeWV5d1B/ZBE/1w== yLjSBTrHPv4hYzq7ifrupw== llWGfxH1kx6kmqWTLsGlNQ== wAUCMG8n+knQvH4E34eUwg== IbbsvjxqizsskEOSKLuZhg== kE/xVN1gkmddNzespQmxew== IuBbUknAB4Sq3eDmXsy/VA== cAdHvCWW1107vtT5lAML2w==
     * order_confirm : 0
     * status_text : 待确认
     * user : {"nick_name":"手机用户736986","id":27}
     * email : S@n.mw
     * status : -10
     */

    private String create_time;
    private String is_vip;
    private String type_text;
    private String mobile;
    private String email_text;
    private String type;
    private String title;
    private String content;
    private String update_time;
    private LawyerDetailBean lawyerDetail;
    private String user_id;
    private String price;
    private String id;
    private String sn;
    private String mobile_text;
    private String order_confirm;
    private String status_text;
    private UserBean user;
    private String email;
    private String status;
    public ContractDetailBean contractDetail;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail_text() {
        return email_text;
    }

    public void setEmail_text(String email_text) {
        this.email_text = email_text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public LawyerDetailBean getLawyerDetail() {
        return lawyerDetail;
    }

    public void setLawyerDetail(LawyerDetailBean lawyerDetail) {
        this.lawyerDetail = lawyerDetail;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMobile_text() {
        return mobile_text;
    }

    public void setMobile_text(String mobile_text) {
        this.mobile_text = mobile_text;
    }

    public String getOrder_confirm() {
        return order_confirm;
    }

    public void setOrder_confirm(String order_confirm) {
        this.order_confirm = order_confirm;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContractDetailBean getContractDetail() {
        return contractDetail;
    }

    public void setContractDetail(ContractDetailBean contractDetail) {
        this.contractDetail = contractDetail;
    }

    public static class LawyerDetailBean extends BaseBean {
        /**
         * score_star : 0
         * real_name :
         * id : 0
         * avatar :
         * expertise : []
         */

        private String score_star;
        private String real_name;
        private String id;
        private String avatar;
        public String expertiseString;
        private List<String> expertise;

        @Override
        public void fromJSONAuto(JSONObject json) {
            super.fromJSONAuto(json);
            expertiseString = "";
            for (int i = 0; i < expertise.size(); i++) {
                if (i == expertise.size() - 1) {
                    expertiseString += (expertise.get(i));
                } else {
                    expertiseString += (expertise.get(i) + ",");
                }
            }
        }

        public String getScore_star() {
            return score_star;
        }

        public void setScore_star(String score_star) {
            this.score_star = score_star;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public List<String> getExpertise() {
            return expertise;
        }

        public void setExpertise(List<String> expertise) {
            this.expertise = expertise;
        }
    }

    public static class UserBean extends BaseBean {
        /**
         * nick_name : 手机用户736986
         * id : 27
         */

        private String nick_name;
        private String id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ContractDetailBean extends BaseBean{

        public String id;
        public String create_time;
        public String update_time;
        public String title;
        public String sub_title;
        public String price;
        public String type;
        public String industry;
        public String download_num;
        public String sn;
        public List<TemplateDTO> template;
        public String template_html;
        public String keys;
        public String cover_img;
        public String is_pay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getDownload_num() {
            return download_num;
        }

        public void setDownload_num(String download_num) {
            this.download_num = download_num;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public List<TemplateDTO> getTemplate() {
            return template;
        }

        public void setTemplate(List<TemplateDTO> template) {
            this.template = template;
        }

        public String getTemplate_html() {
            return template_html;
        }

        public void setTemplate_html(String template_html) {
            this.template_html = template_html;
        }

        public String getKeys() {
            return keys;
        }

        public void setKeys(String keys) {
            this.keys = keys;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public static class TemplateDTO extends BaseBean {
            public String path;
            public String url;

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
    }
}
