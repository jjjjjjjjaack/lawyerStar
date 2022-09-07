package com.qbo.lawyerstar.app.module.mine.auth.personal.bean;

import com.qbo.lawyerstar.app.bean.ImagePathBean;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class PersonalAuthBean extends BaseBean {
    /**
     * avatar : [{"path":"头像半路径","url":"头像全路径"}]
     * real_name : 姓名
     * address_info_text : 所在地
     * status : pending---待审，pass---通过，refuse---拒绝
     */

    private String real_name;
    private String address_info_text;
    private String status;
    private String sex_text;
    private List<ImagePathBean> avatar;

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

    public String getSex_text() {
        return sex_text;
    }

    public void setSex_text(String sex_text) {
        this.sex_text = sex_text;
    }
}
