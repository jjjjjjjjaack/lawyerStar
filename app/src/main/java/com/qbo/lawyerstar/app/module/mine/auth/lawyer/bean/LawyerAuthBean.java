package com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean;

import com.qbo.lawyerstar.app.bean.ImagePathBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class LawyerAuthBean extends BaseBean {
    /**
     * avatar : [{"path":"头像半路径","url":"头像全路径"}]
     * real_name : 姓名
     * address_info_text : 所在地
     * expertise : 擅长领域
     * employment_year : 从业年数
     * intro : 从业年数
     * sex_text : 性别
     * status : pending---待审，pass---通过，refuse---拒绝
     * lawyer_license : [{"path":"律师执照半路径","url":"律师执照全路径"}]
     */

    private String real_name;
    private String address_info_text;
    private String expertise;
    private String employment_year;
    private String intro;
    private String sex_text;
    private String check_text;
    private String status;
    private List<ImagePathBean> avatar;
    private List<ImagePathBean> lawyer_license;

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

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
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

    public String getSex_text() {
        return sex_text;
    }

    public void setSex_text(String sex_text) {
        this.sex_text = sex_text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ImagePathBean> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<ImagePathBean> avatar) {
        this.avatar = avatar;
    }

    public List<ImagePathBean> getLawyer_license() {
        return lawyer_license;
    }

    public void setLawyer_license(List<ImagePathBean> lawyer_license) {
        this.lawyer_license = lawyer_license;
    }

    public String getCheck_text() {
        return check_text;
    }

    public void setCheck_text(String check_text) {
        this.check_text = check_text;
    }
}
