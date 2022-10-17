package com.qbo.lawyerstar.app.module.business.bean;

import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.util.ToolUtils;

public class H5URLBean extends BaseBean {
    public String url;
    public String params;

    public H5URLBean(String url, String params) {
        this.url = url;
        this.params = params;
    }

    @Override
    public String toString() {
        if (!ToolUtils.isNull(params)) {
            return "{url:'" + url + "',params:" + params + "}";
        } else if (!ToolUtils.isNull(url)) {
            return "{url:'" + url + "'}";
        } else {
            return "";
        }
    }
}