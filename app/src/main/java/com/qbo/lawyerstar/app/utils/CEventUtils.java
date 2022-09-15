package com.qbo.lawyerstar.app.utils;

public class CEventUtils {

    public static class MainTabChangePositionEvent{
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
}
