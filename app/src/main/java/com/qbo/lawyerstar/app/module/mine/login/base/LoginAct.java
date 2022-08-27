package com.qbo.lawyerstar.app.module.mine.login.base;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.splash.SplashAct;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.CountDownMsgUtils;
import framework.mvp1.base.util.CountDownMsgUtils2;
import framework.mvp1.base.util.T;

public class LoginAct extends MvpAct<ILoginView, BaseModel, LoginPresenter> implements ILoginView {

    @BindView(R.id.pact_tv)
    TextView pact_tv;
    @BindView(R.id.tv_pact_text)
    TextView tv_pact_text;
    @BindView(R.id.getcode_tv)
    TextView getcode_tv;
    @BindView(R.id.tologin_tv)
    View tologin_tv;
    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.code_et)
    EditText code_et;

    CountDownMsgUtils countDownMsgUtils;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_login;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        countDownMsgUtils = CountDownMsgUtils.registerCountDownAction(getMContext(), "loginCode",
                60, getcode_tv, new CountDownMsgUtils.ICountDownPostCode() {
                    @Override
                    public void allowToRequestCode() {
                        presenter.getPhoneCode(phone_et.getText().toString().trim());
                    }
                });
        tologin_tv.setEnabled(false);
        phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phone_et.getText().length() > 0 && code_et.getText().length() > 0) {
                    tologin_tv.setEnabled(true);
                } else {
                    tologin_tv.setEnabled(false);
                }
            }
        });
        code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phone_et.getText().length() > 0 && code_et.getText().length() > 0) {
                    tologin_tv.setEnabled(true);
                } else {
                    tologin_tv.setEnabled(false);
                }
            }
        });
        tologin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pact_tv.isSelected()) {
                    T.showShort(getMContext(), getString(R.string.login_tx7));
                    return;
                }
                presenter.toLogin(phone_et.getText().toString().trim(), code_et.getText().toString().trim(), "1");
            }
        });
        SplashAct.initPactText(getMContext(), tv_pact_text);
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
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void getCodeResult(boolean b) {
        if (isDestroyed()) {
            return;
        }
        if (b && countDownMsgUtils != null) {
            countDownMsgUtils.requestSuccess();
        }
    }

    @Override
    public void loginResult(boolean b) {
        if (b) {

        }
    }
}
