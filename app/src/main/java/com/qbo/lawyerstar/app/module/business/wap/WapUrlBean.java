package com.qbo.lawyerstar.app.module.business.wap;

import framework.mvp1.base.bean.BaseBean;

public class WapUrlBean extends BaseBean {
    public String label;
    public String page;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
