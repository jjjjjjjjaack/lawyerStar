package framework.mvp1.base.util;


import android.app.Activity;

import java.util.Stack;

public class ActivityStackUtils {

    private volatile static ActivityStackUtils INSTANCE; //声明成 volatile

    private ActivityStackUtils() {
    }

    public static ActivityStackUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (ActivityStackUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ActivityStackUtils();
                }
            }
        }
        return INSTANCE;
    }

    private Stack<Activity> mActivityStack = new Stack();

    // 弹出当前activity并销毁
    public void popActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void clearAllActivity() {
        while (!mActivityStack.isEmpty()) {
            Activity activity = mActivityStack.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
