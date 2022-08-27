package com.qbo.lawyerstar.app.module.mine.login.selecttype;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

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
            }
        });
        type2_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetView();
                type2_rl.setSelected(true);
                nextstep_tv.setEnabled(true);
            }
        });
        type3_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSetView();
                type3_rl.setSelected(true);
                nextstep_tv.setEnabled(true);
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

    public void reSetView(){
        type1_rl.setSelected(false);
        type2_rl.setSelected(false);
        type3_rl.setSelected(false);
    }

}
