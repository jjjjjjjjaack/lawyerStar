package com.qbo.lawyerstar.app.module.contract.library.bean;

import framework.mvp1.base.bean.BaseBean;

public class ContractLibBean extends BaseBean {
    public String create_time;
    public String title;
    public String sub_title;
    public String price;
    public String type;
    public String industry;
    public String template;
    public String template_html;
    public String download_num;
    public String cover_img;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate_html() {
        return template_html;
    }

    public void setTemplate_html(String template_html) {
        this.template_html = template_html;
    }

    public String getDownload_num() {
        return download_num;
    }

    public void setDownload_num(String download_num) {
        this.download_num = download_num;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }
}
