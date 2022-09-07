package com.qbo.lawyerstar.app.module.mine.auth.company.bean;

import com.qbo.lawyerstar.app.bean.ImagePathBean;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class CompanyAuthBean extends BaseBean {
    /**
     * avatar : [{"path":"头像半路径","url":"头像全路径"}]
     * company_name : 企业名称
     * address_info_text : 所在地
     * address : 详细地址
     * unified_code : 统一社会信用代码
     * business_license : [{"path":"营业执照半路径","url":"营业执照全路径"}]
     * legal_person : 法人
     * responsible_mobile : 负责人手机
     * industry_text : 所属行业
     * enterprise_size_text : 企业规模
     * established_date : 成立时间
     * status : pending---待审，pass---通过，refuse---拒绝
     * check_text : 审核提示语
     */

    private String company_name;
    private String address_info_text;
    private String address;
    private String unified_code;
    private String legal_person;
    private String responsible_mobile;
    private String industry_text;
    private String enterprise_size_text;
    private String established_date;
    private String status;
    private String check_text;
    private List<ImagePathBean> avatar;
    private List<ImagePathBean> business_license;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress_info_text() {
        return address_info_text;
    }

    public void setAddress_info_text(String address_info_text) {
        this.address_info_text = address_info_text;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUnified_code() {
        return unified_code;
    }

    public void setUnified_code(String unified_code) {
        this.unified_code = unified_code;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getResponsible_mobile() {
        return responsible_mobile;
    }

    public void setResponsible_mobile(String responsible_mobile) {
        this.responsible_mobile = responsible_mobile;
    }

    public String getIndustry_text() {
        return industry_text;
    }

    public void setIndustry_text(String industry_text) {
        this.industry_text = industry_text;
    }

    public String getEnterprise_size_text() {
        return enterprise_size_text;
    }

    public void setEnterprise_size_text(String enterprise_size_text) {
        this.enterprise_size_text = enterprise_size_text;
    }

    public String getEstablished_date() {
        return established_date;
    }

    public void setEstablished_date(String established_date) {
        this.established_date = established_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheck_text() {
        return check_text;
    }

    public void setCheck_text(String check_text) {
        this.check_text = check_text;
    }

    public List<ImagePathBean> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<ImagePathBean> avatar) {
        this.avatar = avatar;
    }

    public List<ImagePathBean> getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(List<ImagePathBean> business_license) {
        this.business_license = business_license;
    }
}

