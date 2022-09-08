package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupTipSuccessView extends PopupBaseView {

    TextView title_tv;
    TextView content_tv;

    OnDismissInterface onDismissInterface;

    public interface OnDismissInterface {
        void onDismiss();
    }

    public PopupTipSuccessView(Context context) {
        super(context, ResourceUtils.dip2px2(context, 264), 0);
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_tip_success_1;
    }

    @Override
    public void initPopupView() {
        this.title_tv = popView.findViewById(R.id.title_tv);
        this.content_tv = popView.findViewById(R.id.content_tv);
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

    public void showCenter(View parent, String title, String content, OnDismissInterface onDismissInterface) {
        title_tv.setText(title);
        content_tv.setText(content);
        setOnDismissInterface(onDismissInterface);
        super.showCenter(parent);
    }

    public static PopupTipSuccessView showPopTipViewCenter(Context context, String title, String content, OnDismissInterface onDismissInterface, View v) {
        PopupTipSuccessView popupTipView = new PopupTipSuccessView(context);
        popupTipView.showCenter(v, title, content, onDismissInterface);
        return popupTipView;
    }


}
