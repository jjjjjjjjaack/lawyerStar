package com.qbo.lawyerstar.app.module.study.bean;

import framework.mvp1.base.bean.BaseBean;

public class ArticleBean extends BaseBean {
    /**
     * create_time : 时间
     * article_clazz : 文章分类
     * title : 文章标题
     * image : 缩略图片
     * is_rec : 推荐显示
     * content : 文章内容
     * reading : 阅读量
     */

    public String create_time;
    public String article_clazz;
    public String title;
    public String image;
    public String is_rec;
    public String content;
    public String reading;
    public String id;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getArticle_clazz() {
        return article_clazz;
    }

    public void setArticle_clazz(String article_clazz) {
        this.article_clazz = article_clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_rec() {
        return is_rec;
    }

    public void setIs_rec(String is_rec) {
        this.is_rec = is_rec;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
