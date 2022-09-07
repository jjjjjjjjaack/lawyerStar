package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupCommitSuccessView extends PopupBaseView {

    OnDismissInterface onDismissInterface;

    public interface OnDismissInterface {
        void onDismiss();
    }

    public PopupCommitSuccessView(Context context) {
        super(context, ResourceUtils.dip2px2(context, 264), 0);
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_commit_success_1;
    }

    @Override
    public void initPopupView() {
        this.popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onPopupDismiss() {
        super.onPopupDismiss();
        if (onDismissInterface != null) {
            onDismissInterface.onDismiss();
        }
    }

    public void setOnDismissInterface(OnDismissInterface onDismissInterface) {
        this.onDismissInterface = onDismissInterface;
    }
}
