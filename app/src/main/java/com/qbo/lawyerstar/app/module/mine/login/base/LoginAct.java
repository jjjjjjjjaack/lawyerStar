package com.qbo.lawyerstar.app.module.mine.login.base;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.main.VpMainAct;
import com.qbo.lawyerstar.app.module.mine.login.selecttype.UserSelectTypeAct;
import com.qbo.lawyerstar.app.module.splash.SplashAct;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.CountDownMsgUtils;
import framework.mvp1.base.util.CountDownMsgUtils2;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.WeChatUtils;

public class LoginAct extends MvpAct<ILoginView, BaseModel, LoginPresenter> implements ILoginView {

    @BindView(R.id.pact_tv)
    View pact_tv;
    @BindView(R.id.tv_pact_text)
    TextView tv_pact_text;
    @BindView(R.id.getcode_tv)
    TextView getcode_tv;
    @BindView(R.id.tologin_tv)
    View tologin_tv;
    @BindView(R.id.jump_tv)
    View jump_tv;
    @BindView(R.id.wechatlogin_tv)
    View wechatlogin_tv;
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
        pact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pact_tv.setSelected(!pact_tv.isSelected());
            }
        });
        tologin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CrashReport.testJavaCrash();
                if (!pact_tv.isSelected()) {
                    T.showShort(getMContext(), getString(R.string.login_tx7));
                    return;
                }
                presenter.toLogin(phone_et.getText().toString().trim(), code_et.getText().toString().trim(), "0");
            }
        });
        jump_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VpMainAct.openMainAct(getMContext());
            }
        });
        SplashAct.initPactText(getMContext(), tv_pact_text);


        wechatlogin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pact_tv.isSelected()) {
                    T.showShort(getMContext(), getString(R.string.login_tx7));
                    return;
                }
                WeChatUtils.getInstance().loginWx(0);
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
        countDownMsgUtils.doRelease();
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
    public void loginResult(int type) {
        if (type == 0) {
            VpMainAct.openMainAct(getMContext());
        } else if (type == 1) {//????????????
            VpMainAct.openMainAct(getMContext());
//            gotoActivity(UserSelectTypeAct.class);
        }
    }
}
