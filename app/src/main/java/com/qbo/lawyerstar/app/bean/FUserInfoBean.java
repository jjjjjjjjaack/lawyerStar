package com.qbo.lawyerstar.app.bean;

import com.alibaba.fastjson.JSONObject;

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

    public boolean isVip(){
        return !ToolUtils.isNull(rank_id);
    }
}
