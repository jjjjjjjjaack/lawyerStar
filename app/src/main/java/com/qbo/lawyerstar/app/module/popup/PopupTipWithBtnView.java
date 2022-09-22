package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.views.pop.PopupBaseView;


public class PopupTipWithBtnView extends PopupBaseView {

    private TextView title_tv, content_tv, ok_tv, cancle_tv;
    private PopupTipWithBtnInterface popupTipInterface;

    public PopupTipWithBtnView(Context context) {
        super(context, ResourceUtils.dip2px2(context, 270),0);
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_tip_view_withbtn;
    }

    @Override
    public void initPopupView() {
        title_tv = popView.findViewById(R.id.title_tv);
        content_tv = popView.findViewById(R.id.content_tv);
        ok_tv = popView.findViewById(R.id.ok_tv);
        cancle_tv = popView.findViewById(R.id.cancle_tv);

        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupTipInterface != null) {
                    popupTipInterface.okClick();
                }
                dismiss();
            }
        });
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupTipInterface != null) {
                    popupTipInterface.cancleClick();
                }
                dismiss();
            }
        });
    }

    @Override
    public void bulidPopupWindow(int width, int height) {
        super.bulidPopupWindow(width, height);
    }

    public interface PopupTipWithBtnInterface {
        void okClick();

        void cancleClick();

        void onDisimss();
    }

    public void setPopupTipInterface(PopupTipWithBtnInterface popupTipInterface) {
        this.popupTipInterface = popupTipInterface;
    }

    public void showCenter(View parent, String title, String content, String oktx, String cancletx, PopupTipWithBtnInterface popupTipInterface) {
        title_tv.setText(title);
        content_tv.setText(content);
        ok_tv.setText(oktx);
        cancle_tv.setText(cancletx);
        this.popupTipInterface = popupTipInterface;
        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                if (popupTipInterface != null) {
                    popupTipInterface.onDisimss();
                }
            }
        });

        super.showCenter(parent);
    }

    public static PopupTipWithBtnView showPopTipView(Context context, String title, String content, String oktx, String cancletx, PopupTipWithBtnInterface popupTipInterface, View v) {
        PopupTipWithBtnView popupTipView = new PopupTipWithBtnView(context);
        popupTipView.showCenter(v, title, content, oktx, cancletx ,popupTipInterface);
        return popupTipView;
    }

    public static PopupTipWithBtnView showPopTipView(Context context, String title, String content, PopupTipWithBtnInterface popupTipInterface, View v) {
        return PopupTipWithBtnView.showPopTipView(context, title, content, "确定", "取消", popupTipInterface, v);
    }
}
