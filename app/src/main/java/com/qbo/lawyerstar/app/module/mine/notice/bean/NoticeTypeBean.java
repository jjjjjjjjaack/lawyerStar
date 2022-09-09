package com.qbo.lawyerstar.app.module.mine.notice.bean;

import framework.mvp1.base.bean.BaseBean;

public class NoticeTypeBean extends BaseBean {
    /**
     * all_num : 17
     * trust_num : 17
     * sys_num : 0
     * last_trust : 创建订单，购买仲裁/诉讼
     * last_sys : null
     * time_trust : 2022-08-27 16:29:12
     * time_sys : null
     */

    public String all_num;
    public String trust_num;
    public String sys_num;
    public String last_trust;
    public String last_sys;
    public String time_trust;
    public String time_sys;

    public String getAll_num() {
        return all_num;
    }

    public void setAll_num(String all_num) {
        this.all_num = all_num;
    }

    public String getTrust_num() {
        return trust_num;
    }

    public void setTrust_num(String trust_num) {
        this.trust_num = trust_num;
    }

    public String getSys_num() {
        return sys_num;
    }

    public void setSys_num(String sys_num) {
        this.sys_num = sys_num;
    }

    public String getLast_trust() {
        return last_trust;
    }

    public void setLast_trust(String last_trust) {
        this.last_trust = last_trust;
    }

    public String getLast_sys() {
        return last_sys;
    }

    public void setLast_sys(String last_sys) {
        this.last_sys = last_sys;
    }

    public String getTime_trust() {
        return time_trust;
    }

    public void setTime_trust(String time_trust) {
        this.time_trust = time_trust;
    }

    public String getTime_sys() {
        return time_sys;
    }

    public void setTime_sys(String time_sys) {
        this.time_sys = time_sys;
    }
}
