package com.qbo.lawyerstar.app.module.pay.bean;

import framework.mvp1.base.bean.BaseBean;

public class PayResultBean extends BaseBean {

    public String status;
    public String status_text;
    public String tips_text;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getTips_text() {
        return tips_text;
    }

    public void setTips_text(String tips_text) {
        this.tips_text = tips_text;
    }
}
