package com.qbo.lawyerstar.app.utils;

import android.content.Context;

import com.qbo.lawyerstar.app.bean.FUserInfoBean;

import framework.mvp1.base.util.JnCache;

public class FCacheUtils {

    public final static String CACHE_USERINFO_KEY = "CACHE_USERINFO_KEY";

    /**
     * @param
     * @return
     * @description 获取用户信息
     * @author jiejack
     * @time 2022/3/4 11:59 下午
     */
    public static FUserInfoBean getUserInfo(Context context) {
        return JnCache.getCache(context, CACHE_USERINFO_KEY);
    }

    /**
     * @param
     * @return
     * @description 缓存用户信息
     * @author jiejack
     * @time 2022/3/4 11:59 下午
     */
    public static void saveUserInfo(Context context, FUserInfoBean userInfoBean) {
        JnCache.saveCache(context, CACHE_USERINFO_KEY, userInfoBean);
    }

    /**
     * @param
     * @return
     * @description 清除用户信息
     * @author jiejack
     * @time 2022/3/4 11:59 下午
     */
    public static void clearUserInfo(Context context) {
        JnCache.removeCache(context, CACHE_USERINFO_KEY);
    }


}
