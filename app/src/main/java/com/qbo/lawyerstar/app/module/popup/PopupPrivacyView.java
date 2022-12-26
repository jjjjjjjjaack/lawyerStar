package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.mine.protocol.ProtocolAct;

import framework.mvp1.base.util.ActivityStackUtils;
import framework.mvp1.base.util.JnCache;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.SpanManager;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupPrivacyView extends PopupBaseView {

    TextView connect_tv;
    View agree_tv;
    View disagree_tv;

    private PopupPrivacyInterface popupPrivacyInterface;

    public PopupPrivacyView(Context context, PopupPrivacyInterface popupPrivacyInterface) {
        super(context, ResourceUtils.dip2px2(context, 264), 0);
        this.popupPrivacyInterface = popupPrivacyInterface;
        setOutSideTouchAbale(false);
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_privacy_view;
    }

    public interface PopupPrivacyInterface {
        void agree();
    }

    @Override
    public void initPopupView() {

        connect_tv = popView.findViewById(R.id.connect_tv);
        String content = "欢迎使用法天平\n";
        content += "在使用我们的产品和服务之前，请您先阅读并了解《";
        int pos1 = content.length();
        content += "用户协议";
        int pos2 = content.length();
        content += "》和《";
        int pos3 = content.length();
        content += "隐私协议";
        int pos4 = content.length();
        content += "》我们将严格按照上述协议为您提供服务，保护您的信息安全，点击“同意”即表示您已阅读并同意全部条款，可以继续使用我们的产品和服务。";
        SpanManager spanManager = SpanManager.getInstance(content.toString());
        spanManager.setClickableSpan(pos1, pos2, connect_tv, new SpanManager.OnTextClickedListener() {
            @Override
            public void onTextClicked(View view) {
                ProtocolAct.openAct(context, "user");
            }
        }).setForegroundColorSpan(pos1, pos2, context.getResources().getColor(R.color.c_02c4c3));
        spanManager.setClickableSpan(pos3, pos4, connect_tv, new SpanManager.OnTextClickedListener() {
            @Override
            public void onTextClicked(View view) {
                ProtocolAct.openAct(context, "privacy");
            }
        }).setForegroundColorSpan(pos3, pos4, context.getResources().getColor(R.color.c_02c4c3));
        connect_tv.setText(spanManager.toBuild());
        disagree_tv = popView.findViewById(R.id.disagree_tv);
        disagree_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStackUtils.getInstance().clearAllActivity();
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        agree_tv = popView.findViewById(R.id.agree_tv);
        agree_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JnCache.saveCache("agree_privacy", "1");
                dismiss();
                if (popupPrivacyInterface != null) {
                    popupPrivacyInterface.agree();
                }
            }
        });
    }

}
