package com.qbo.lawyerstar.app.module.mine.setting;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.about.AboutUsAct;
import com.qbo.lawyerstar.app.module.mine.account.cancle.CancelAccountAct;
import com.qbo.lawyerstar.app.module.mine.protocol.ProtocolAct;
import com.qbo.lawyerstar.app.module.popup.PopupTipWithBtnView;

import butterknife.BindView;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.LoginoutUtis;

public class SettingAct extends MvpAct<ISettingView, BaseModel, SettingPresenter> implements ISettingView {

    @BindView(R.id.logout_tv)
    View logout_tv;
    @BindView(R.id.setting_rl)
    View setting_rl;
    @BindView(R.id.deleteaccount_rl)
    View deleteaccount_rl;
    @BindView(R.id.userprocotol_rl)
    View userprocotol_rl;
    @BindView(R.id.privateprocotol_rl)
    View privateprocotol_rl;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_setting;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_setting_tx1);

        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupTipWithBtnView.showPopTipView(getMContext(), "退出账号", "确定退出账号吗？",
                        new PopupTipWithBtnView.PopupTipWithBtnInterface() {
                    @Override
                    public void okClick() {
                        LoginoutUtis.getInstance().doLogOut(getMContext());
                    }

                    @Override
                    public void cancleClick() {

                    }

                    @Override
                    public void onDisimss() {

                    }
                }, v);


            }
        });
        setting_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(AboutUsAct.class);
            }
        });
        deleteaccount_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CancelAccountAct.class);
            }
        });
        userprocotol_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProtocolAct.openAct(getMContext(), "user");
            }
        });
        privateprocotol_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProtocolAct.openAct(getMContext(), "privacy");
            }
        });
    }

    @Override
    public void doBusiness() {
        try {
            FTokenUtils.getToken(this, false);
            showLoginView();
        } catch (NeedLoginException e) {
            showUnLoginView();
        }
    }

    private void showUnLoginView() {
        deleteaccount_rl.setVisibility(View.GONE);
        logout_tv.setVisibility(View.GONE);
    }

    private void showLoginView() {
        deleteaccount_rl.setVisibility(View.VISIBLE);
        logout_tv.setVisibility(View.VISIBLE);
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public SettingPresenter initPresenter() {
        return new SettingPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }
}
