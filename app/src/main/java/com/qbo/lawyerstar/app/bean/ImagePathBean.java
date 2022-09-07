package com.qbo.lawyerstar.app.bean;

import framework.mvp1.base.bean.BaseBean;

public class ImagePathBean extends BaseBean {
    public String path;
    public String url;

    public ImagePathBean(String path, String url) {
        this.path = path;
        this.url = url;
    }

    public ImagePathBean() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
