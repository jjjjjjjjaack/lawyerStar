package com.qbo.lawyerstar.app.module.mine.notice.bean;

import framework.mvp1.base.bean.BaseBean;

public class NoticeBean extends BaseBean {
    /**
     * update_time : 2022-09-22 08:49:03
     * create_time : 2022-09-21 22:10:37
     * user_id : 29
     * id : 38
     * title : 合同文书
     * body : 创建订单，购买合同文书
     * content : {"id":"1006","type":"trust","order_type":"contract_documents"}
     * status : 0
     */

    private String update_time;
    private String create_time;
    private String user_id;
    private String id;
    private String title;
    private String body;
    private ContentBean content;
    private String status;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ContentBean extends BaseBean{
        /**
         * id : 1006
         * type : trust
         * order_type : contract_documents
         */

        private String id;
        private String type;
        private String order_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }
    }
}
