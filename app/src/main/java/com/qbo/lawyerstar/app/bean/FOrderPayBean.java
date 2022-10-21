package com.qbo.lawyerstar.app.bean;

import framework.mvp1.base.bean.BaseBean;

public class FOrderPayBean extends BaseBean {
    public String sn;
    public String price;
    public String orderType;
    public String payType;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
