package com.qbo.lawyerstar.app.utils;

import android.content.Context;

import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.JnCache;
import framework.mvp1.base.util.ToolUtils;

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


    /**
     * @param useCache 不使用缓存数据
     * @return
     * @description
     * @author jieja
     * @time 2022/4/14 14:28
     */
    public static void getUserInfo(Context mCxt, boolean useCache, GetUserInfoInterface getUserInfoInterface) {
        try {
            FTokenUtils.getToken(mCxt, false);
        } catch (NeedLoginException e) {
            return;
        }
        if (getUserInfoInterface == null) {
            return;
        }
        FUserInfoBean cacheBean = getUserInfo(mCxt);
        if (cacheBean != null && useCache) {
            getUserInfoInterface.reslut(false, cacheBean);
            return;
        }
        REQ_Factory.GET_USERINFO_REQ req = new REQ_Factory.GET_USERINFO_REQ();
        BasePresent.doStaticCommRequest(mCxt, req, false, true, new BasePresent.DoCommRequestInterface<BaseResponse, FUserInfoBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public FUserInfoBean doMap(BaseResponse baseResponse) {
                return FUserInfoBean.fromJSONAuto(baseResponse.datas, FUserInfoBean.class);
            }

            @Override
            public void onSuccess(FUserInfoBean fUserInfoBean) throws Exception {
                if (fUserInfoBean == null) {
                    if (getUserInfoInterface != null) {
                        getUserInfoInterface.fail();
                    }
                } else {
                    FCacheUtils.saveUserInfo(mCxt, fUserInfoBean);
                    if (getUserInfoInterface != null) {
                        getUserInfoInterface.reslut(true, fUserInfoBean);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                getUserInfoInterface.fail();
            }
        });
    }

    public interface GetUserInfoInterface {
        void reslut(boolean isNet, FUserInfoBean userInfoBean);

        void fail();
    }

}
