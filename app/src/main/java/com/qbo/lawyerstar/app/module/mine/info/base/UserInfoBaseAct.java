package com.qbo.lawyerstar.app.module.mine.info.base;

import android.content.Context;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.utils.FCacheUtils;
import com.qbo.lawyerstar.app.view.ChangeGasStationImageView2;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class UserInfoBaseAct extends MvpAct<IUserInfoBaseView, BaseModel, UserInfoBasePresenter> implements IUserInfoBaseView {

    @BindView(R.id.userlogo_civ)
    ChangeGasStationImageView2 userlogo_civ;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.phone_tv)
    TextView phone_tv;
    @BindView(R.id.usertype_tv)
    TextView usertype_tv;
    @BindView(R.id.addredd_tv)
    TextView addredd_tv;
    @BindView(R.id.companysuinum_tv)
    TextView companysuinum_tv;

    @Override
    public UserInfoBasePresenter initPresenter() {
        return new UserInfoBasePresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_userinfo;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_info_tx1);
    }

    @Override
    public void doBusiness() {
        FCacheUtils.getUserInfo(this, false, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                name_tv.setText(userInfoBean.nick_name);
                phone_tv.setText(userInfoBean.mobile);
                usertype_tv.setText(userInfoBean.user_type_tx);
                addredd_tv.setText(userInfoBean.city_name);
                companysuinum_tv.setText(userInfoBean.company_tax);
            }

            @Override
            public void fail() {

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
}
