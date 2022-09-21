package com.qbo.lawyerstar.app.module.mine.order.bean;

import framework.mvp1.base.bean.BaseBean;

public class OrderTypeBean extends BaseBean {
    /**
     * value : legal_advice
     * label : 法律咨询
     */

    public String value;
    public String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
