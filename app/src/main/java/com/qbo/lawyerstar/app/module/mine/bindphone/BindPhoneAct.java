package com.qbo.lawyerstar.app.module.mine.bindphone;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qbo.lawyerstar.R;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.CountDownMsgUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;

public class BindPhoneAct extends MvpAct<IBindPhoneView, BaseModel, BindPhonePresenter> implements IBindPhoneView {

    public static void openAct(Context context, String wxCode) {
        Intent intent = new Intent(context, BindPhoneAct.class);
        intent.putExtra("wxCode", wxCode);
        context.startActivity(intent);
    }

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
    public BindPhonePresenter initPresenter() {
        return new BindPhonePresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_bind_phone;
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
                presenter.toBindPhone(phone_et.getText().toString().trim(),code_et.getText().toString().trim());
            }
        });
    }

    @Override
    public void doBusiness() {
        presenter.wxCode = getIntent().getStringExtra("wxCode");
        if (ToolUtils.isNull(presenter.wxCode)) {
            finish();
            return;
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

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
    public Context getMContext() {
        return this;
    }
}
