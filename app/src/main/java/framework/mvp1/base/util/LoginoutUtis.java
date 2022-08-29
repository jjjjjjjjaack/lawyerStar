package framework.mvp1.base.util;

import android.content.Context;
import android.os.Handler;

public class LoginoutUtis {

    private volatile static LoginoutUtis INSTANCE; //声明成 volatile

    private LoginoutUtis(){};

    public static LoginoutUtis getInstance() {
        if (INSTANCE == null) {
            synchronized (LoadingUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginoutUtis();
                }
            }
        }
        return INSTANCE;
    }

    public Boolean isLoginOut = false;

    public void doLogOut(Context context) {
        synchronized (isLoginOut) {
            if (isLoginOut) {
                return;
            }
            isLoginOut = true;
            FTokenUtils.doLogout(context);
            ToolUtils.reStartApp(context);
//            Intent closeIntent = new Intent(CLOSE_UN_TRAGETACT_ACTION);
//            closeIntent.putExtra(CLOSE_UN_TRAGETACT_KEY,)
//            context.sendBroadcast(closeIntent);
//            Intent in = new Intent(context, SplashAct.class);
//            context.startActivity(in);
//            ToolUtils.reStartApp(context);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isLoginOut = false;
                }
            }, 5000);
        }
    }

}
