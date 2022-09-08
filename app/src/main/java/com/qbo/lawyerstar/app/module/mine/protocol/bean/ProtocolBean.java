package com.qbo.lawyerstar.app.module.mine.protocol.bean;

import framework.mvp1.base.bean.BaseBean;

public class ProtocolBean extends BaseBean {
    /**
     * article_clazz : 10
     * update_time : 2022-08-24 11:29:44
     * create_time : 2022-08-24 11:29:44
     * id : 1002
     * title : 注销协议
     * content : 注销协议
     */

    public String article_clazz;
    public String update_time;
    public String create_time;
    public String id;
    public String title;
    public String content;

    public String getArticle_clazz() {
        return article_clazz;
    }

    public void setArticle_clazz(String article_clazz) {
        this.article_clazz = article_clazz;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
