package com.qbo.lawyerstar.app.bean;

import framework.mvp1.base.bean.BaseBean;

public class FPayTypeBean extends BaseBean {
    public String id;
    public String name;
    public int iconRes;

    public FPayTypeBean() {
    }

    public FPayTypeBean(String id, int iconRes, String name) {
        this.id = id;
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
