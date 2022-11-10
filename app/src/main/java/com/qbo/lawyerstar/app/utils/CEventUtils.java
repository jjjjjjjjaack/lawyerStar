package com.qbo.lawyerstar.app.utils;

public class CEventUtils {

    public static class MainTabChangePositionEvent {
        public int pos;

        public MainTabChangePositionEvent(int pos) {
            this.pos = pos;
        }
    }

    public static class H5Event {
        public int what;// -3 Toast -2 quit ,-1 onpageload , 0 onNavigationBarButtonTap,1 setNavbar ，2 navigateTo
        public String object;

        public H5Event(int what, String object) {
            this.what = what;
            this.object = object;
        }
    }

    /**
     * H5刷新
     */
    public static class H5QuitRefreshEvent {
    }


    /**
     * @param
     * @author jieja
     * @description 删除订单成功
     * @return
     * @time 2022/9/23 10:36
     */
    public static class ContractPaySuccessEvent {
        public String contractid;

        public ContractPaySuccessEvent(String contractid) {
            this.contractid = contractid;
        }
    }

    /**
     * @param
     * @author jieja
     * @description 删除订单成功
     * @return
     * @time 2022/9/23 10:36
     */
    public static class ContractDownLoadSuccess {
        public String contractid;
        public String nowDownLoadNum;

        public ContractDownLoadSuccess(String contractid, String nowDownLoadNum) {
            this.contractid = contractid;
            this.nowDownLoadNum = nowDownLoadNum;
        }
    }

    /**
     * @param
     * @author jieja
     * @description 删除订单成功
     * @return
     * @time 2022/9/23 10:36
     */
    public static class CancleOrderEvent {
        public String orderid;
        public String ordertype;

        public CancleOrderEvent(String orderid, String ordertype) {
            this.orderid = orderid;
            this.ordertype = ordertype;
        }
    }

    public static class WechatPayEevent{
        public int code;

        public WechatPayEevent(int code) {
            this.code = code;
        }
    }

}
