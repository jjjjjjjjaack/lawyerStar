package com.qbo.lawyerstar.app.module.mine.info.rename;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class UserReNameAct extends MvpAct<IUserReNameView, BaseModel, UserReNamePresenter> implements IUserReNameView {

    @BindView(R.id.name_et)
    EditText name_et;

    @BindView(R.id.commit_tv)
    View commit_tv;

    @Override
    public UserReNamePresenter initPresenter() {
        return new UserReNamePresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_user_rename;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_info_tx1);

        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editInfo(name_et.getText().toString());
            }
        });
    }

    @Override
    public void doBusiness() {
        FCacheUtils.getUserInfo(this, true, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                name_et.setText(userInfoBean.getNick_name());
            }

            @Override
            public void fail() {
                finish();
            }
        });
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void editResult(boolean b) {
        if (b) {
            finish();
        }
    }
}
