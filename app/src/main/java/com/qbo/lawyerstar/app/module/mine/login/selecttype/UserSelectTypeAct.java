package com.qbo.lawyerstar.app.module.mine.login.selecttype;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.main.VpMainAct;
import com.qbo.lawyerstar.app.module.mine.auth.company.CompanyAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.LawyerAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.personal.PersonsalAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.personal.PersonsalAuthPresenter;
import com.qbo.lawyerstar.app.module.mine.info.base.UserInfoBaseAct;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;

public class UserSelectTypeAct extends MvpAct<IUserSelectTypeView, BaseModel, UserSelectTypePresenter> implements IUserSelectTypeView {

    @BindView(R.id.type1_rl)
    View type1_rl;
    @BindView(R.id.type2_rl)
    View type2_rl;
    @BindView(R.id.type3_rl)
    View type3_rl;
    @BindView(R.id.nextstep_tv)
    View nextstep_tv;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_select_usertype;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        reSetView();
        nextstep_tv.setEnabled(false);

        type1_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetView();
                type1_rl.setSelected(true);
                nextstep_tv.setEnabled(true);
                presenter.type = "0";
            }
        });
        type2_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetView();
                type2_rl.setSelected(true);
                nextstep_tv.setEnabled(true);
                presenter.type = "1";
            }
        });
        type3_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetView();
                type3_rl.setSelected(true);
                nextstep_tv.setEnabled(true);
                presenter.type = "2";
            }
        });

        nextstep_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.changeType();
                switch (presenter.type) {
                    case "0"://个人用户
                        gotoActivity(PersonsalAuthAct.class);
                        break;
                    case "1"://认证律师
                        gotoActivity(LawyerAuthAct.class);
                        break;
                    case "2"://企业用户
                        gotoActivity(CompanyAuthAct.class);
                        break;
                }
            }
        });
        FCacheUtils.getUserInfo(getMContext(), false, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                if(isDestroyed()){
                    return;
                }
                if ("1".equals(userInfoBean.user_type)) {
//                    userinfo_type_tx = "企业用户";
                    type3_rl.performClick();
                } else if ("2".equals(userInfoBean.user_type)) {
//                    userinfo_type_tx = "律师用户";
                    type2_rl.performClick();
                } else {
                    type1_rl.performClick();
//                    userinfo_type_tx = "个人用户";
                }
            }

            @Override
            public void fail() {
                if(isDestroyed()){
                    return;
                }
                type1_rl.performClick();
            }
        });

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public UserSelectTypePresenter initPresenter() {
        return new UserSelectTypePresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    public void reSetView() {
        type1_rl.setSelected(false);
        type2_rl.setSelected(false);
        type3_rl.setSelected(false);
    }

    @Override
    public void changeTypeSuccess() {
        VpMainAct.openMainAct(getMContext());
    }
}
