package com.qbo.lawyerstar.app.bean;

import com.alibaba.fastjson.JSONObject;

import framework.mvp1.base.bean.BaseBean;

public class FUserInfoBean extends BaseBean {
    public String avatar;
    public String nick_name;
    public String user_name;
    public String mobile;
    public String user_type;//会员身份 0 个人 1 企业 2 律师
    public String user_type_tx;//会员身份 0 个人 1 企业 2 律师
    public String is_rz;//是否认证
    public String city_name;
    public String company_name;
    public String company_tax;

    @Override
    public void fromJSONAuto(JSONObject json) {
        super.fromJSONAuto(json);
        if("1".equals(user_type)){
            user_type_tx = "企业用户";
        }else if("2".equals(user_type)){
            user_type_tx = "律师用户";
        }else{
            user_type_tx = "个人用户";
        }
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
}
