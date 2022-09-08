package framework.mvp1.base.util;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;


import com.qbo.lawyerstar.app.MyApplication;
import com.qbo.lawyerstar.app.module.mine.login.base.LoginAct;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import framework.mvp1.base.bean.FToken;
import framework.mvp1.base.constant.ACEConstant;
import framework.mvp1.base.exception.NeedLoginException;

public class FTokenUtils {

    public static Boolean isJumpLogin = false;

    /**
     * 获取登录令牌
     *
     * @param context
     * @param flag
     */
    public static FToken getToken(Context context, boolean flag) throws NeedLoginException {
        FToken token = JnCache.getCache(context, ACEConstant.ACE_FTOKEN_KEY);
        if (token == null || ToolUtils.isNull(token.getToken())) {
            if (flag) {
                synchronized (isJumpLogin) {
                    if (!isJumpLogin) {
                        isJumpLogin = true;
                        context.startActivity(new Intent(context, LoginAct.class));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isJumpLogin = false;
                            }
                        }, 5000);
                    }
                }
            }
            throw new NeedLoginException();
        }
        return token;
    }

    /**
     * 存储登录令牌
     *
     * @return
     */
    public static void saveToken(FToken fToken) {
        JnCache.saveCache(MyApplication.getApp(), ACEConstant.ACE_FTOKEN_KEY, fToken);
    }

    /**
     * 清除登录令牌
     */
    public static void clearToken(Context context) {
        JnCache.removeCache(context, ACEConstant.ACE_FTOKEN_KEY);
    }

    /***
     * 注销账号
     * 清除所关联数据
     * @param context
     */
    public static void doLogout(Context context) {
        clearToken(context);
        FCacheUtils.clearUserInfo(context);
    }


}
