package com.qbo.lawyerstar.app.module.mine.vip.bean;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;

public class VipIntroBean extends BaseBean {
    public String id;
    public String create_time;
    public String update_time;
    public String name;
    public String price;
    public String pic;
    public List<DetailDTO> detail;
    public String is_valid;
    public String valid_date;
    public String intro;


    public static class DetailDTO extends BaseBean{
        public String id;
        public String memo;
        public String service_num;
        public String service_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getService_num() {
            return service_num;
        }

        public void setService_num(String service_num) {
            this.service_num = service_num;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<DetailDTO> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailDTO> detail) {
        this.detail = detail;
    }

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
