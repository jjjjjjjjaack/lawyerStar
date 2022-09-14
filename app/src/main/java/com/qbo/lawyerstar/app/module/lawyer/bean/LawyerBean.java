package com.qbo.lawyerstar.app.module.lawyer.bean;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class LawyerBean extends BaseBean {
    public String create_time;
    public String avatar;
    public String real_name;
    public String address_info_text;
    public List<String> expertise;
    public String employment_year;
    public String intro;
    public String case_intro;
    public String id;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getAddress_info_text() {
        return address_info_text;
    }

    public void setAddress_info_text(String address_info_text) {
        this.address_info_text = address_info_text;
    }

    public List<String> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<String> expertise) {
        this.expertise = expertise;
    }

    public String getEmployment_year() {
        return employment_year;
    }

    public void setEmployment_year(String employment_year) {
        this.employment_year = employment_year;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCase_intro() {
        return case_intro;
    }

    public void setCase_intro(String case_intro) {
        this.case_intro = case_intro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
