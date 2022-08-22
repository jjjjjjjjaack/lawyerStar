package com.qbo.lawyerstar.app.view.scrolltextview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.haohaohu.autoscrolltextview.BaseScrollTextView;
import com.haohaohu.autoscrolltextview.IMarqueeListener;
import com.haohaohu.autoscrolltextview.MarqueeSwitcher;
import com.haohaohu.autoscrolltextview.MarqueeTextView;
import com.qbo.lawyerstar.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public abstract class MBaseScrollTextView  extends MarqueeSwitcher
        implements ViewSwitcher.ViewFactory {

    public static final int FLAG_START_AUTO_SCROLL = 1000;
    public static final int FLAG_AUTO_SCROLL = 1001;
    public static final int FLAG_STOP_AUTO_SCROLL = 1002;

    private BaseScrollTextView.OnItemClickListener itemClickListener;
    /**
     * 当前显示Item的ID
     */
    private int currentId = -1;
    private ArrayList<String> textList;

    private Handler handler;

    private volatile static boolean isStop = false;

    public MBaseScrollTextView(Context context) {
        this(context, null);
    }

    public MBaseScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textList = new ArrayList<>();
        handler = new MBaseScrollTextView.MyHandler(this);
        setFactory(this);

        setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_in));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_up_out));
    }

    /**
     * 设置数据源
     */
    public void setTextList(List<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    /**
     * 开始轮播
     */
    public void startAutoScroll() {
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }

    /**
     * 停止轮播
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
        stopText();
    }

    /**
     * 设置点击事件监听
     */
    public void setOnItemClickListener(BaseScrollTextView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public View makeView() {
        // FIXME: 2017/9/21 添加这层RelativeLayout是解决动画默认回到句首的问题
        RelativeLayout layout = new RelativeLayout(getContext());
        MarqueeTextView textView = makeTextView();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(currentId % textList.size());
                }
            }
        });
        textView.setMarqueeListener(new IMarqueeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                if (isStop) {
                    return;
                }
                handler.sendMessageDelayed(Message.obtain(handler, FLAG_AUTO_SCROLL), 10*1000);
            }
        });
        layout.addView(textView);
        return layout;
    }

    private static class MyHandler extends Handler {
        WeakReference<MBaseScrollTextView> textViewWeakReference;

        private MyHandler(MBaseScrollTextView autoScrollTextView) {
            textViewWeakReference = new WeakReference<>(autoScrollTextView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != textViewWeakReference) {
                MBaseScrollTextView autoScrollTextView = textViewWeakReference.get();
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        isStop = false;
                        if (autoScrollTextView.textList.size() > 0) {
                            autoScrollTextView.currentId++;
                            autoScrollTextView.setText(autoScrollTextView.textList.get(
                                    autoScrollTextView.currentId
                                            % autoScrollTextView.textList.size()));
                        }
                        break;
                    case FLAG_AUTO_SCROLL:
                        if (isStop) {
                            return;
                        }
                        if (autoScrollTextView.textList.size() > 0) {
                            autoScrollTextView.currentId++;
                            autoScrollTextView.setText(autoScrollTextView.textList.get(
                                    autoScrollTextView.currentId
                                            % autoScrollTextView.textList.size()));
                        }
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        isStop = true;
                        autoScrollTextView.handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        autoScrollTextView.handler.removeMessages(FLAG_AUTO_SCROLL);

                        break;
                    default:
                        break;
                }
            }
        }
    }
    /**
     * 创建一个内部可横向滚动的textview
     */
    public abstract MarqueeTextView makeTextView();
}
