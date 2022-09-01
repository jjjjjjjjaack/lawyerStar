package com.qbo.lawyerstar.app.module.mine.auth.personal;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.popup.PopupSelectCityView;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;

public class PersonsalAuthAct extends MvpAct<IPersonsalAuthView, BaseModel, PersonsalAuthPresenter> implements IPersonsalAuthView {



    @BindView(R.id.address_rl)
    View address_rl;

    PopupSelectCityView popupSelectCityView;

    @Override
    public PersonsalAuthPresenter initPresenter() {
        return new PersonsalAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_personal;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_personal_tx1);

        popupSelectCityView = new PopupSelectCityView(this);

        address_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelectCityView.showBottom(v);
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
    public Context getMContext() {
        return this;
    }
}
