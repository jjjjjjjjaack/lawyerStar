package com.qbo.lawyerstar.app.module.inpopview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.util.ToolUtils;

public abstract class InPopBaseView {

    public Context mContext;
    public View popView;
    public View ll_mainview;
    public View clickView;

    public PopFilterBaseInterface popFilterBaseInterface;


    public interface PopFilterBaseInterface {
        void isShow();

        void isDismiss();
    }

    public abstract int getLayout();//返回layoutID

    public abstract void setPopData();//

    public InPopBaseView(Context mContext, PopFilterBaseInterface popFilterBaseInterface) {
        this.mContext = mContext;
        this.popFilterBaseInterface = popFilterBaseInterface;
        popView = LayoutInflater.from(mContext).inflate(getLayout(), null);
        ll_mainview = popView.findViewById(R.id.ll_mainview);

        try {
            popView.findViewById(R.id.view_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss(false);
                }
            });
        } catch (Exception e) {

        }
        viewInit();
        setPopData();
    }

    public abstract void viewInit();

    /**
     * @param clickView
     */
    public void showPopView(View clickView, boolean noanim) {

        ViewGroup parentview = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
        this.clickView = clickView;
        int[] aimviewp = new int[2];
        this.clickView.getLocationOnScreen(aimviewp);
        boolean isNeedAdd;
        if (this.popView.getParent() != null) {
            if (this.popView.getParent() == parentview) {
                popView.setVisibility(View.VISIBLE);
                isNeedAdd = false;
            } else {
                ((ViewGroup) this.popView.getParent()).removeView(this.popView);
                isNeedAdd = true;
            }
        } else {
            isNeedAdd = true;
        }
        if (isNeedAdd) {
            ViewGroup viewGroup = (ViewGroup) ((Activity) mContext).getWindow().getDecorView();
            if (viewGroup instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                lp.topMargin = aimviewp[1] + this.clickView.getHeight();

                if (ToolUtils.hasNavigationBar(mContext)) {
                    lp.bottomMargin = ToolUtils.getNavHeight(mContext);
                }


                viewGroup.addView(this.popView, lp);
            } else {
                viewGroup.addView(this.popView);
            }
//            if (parentview instanceof RelativeLayout) {
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//                lp.addRule(RelativeLayout.BELOW, belowView.getId());
//                parentview.addView(this.popView, lp);
//                popView.setVisibility(View.VISIBLE);
//            }
        }
        if (!noanim) {
            TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, -(popView.getHeight() == 0 ? 1000 : popView.getHeight()), 0);
            translateAnimation1.setDuration(200);
            translateAnimation1.setInterpolator(new DecelerateInterpolator());
            translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    setPopData();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ll_mainview.startAnimation(translateAnimation1);
        } else {
//            setPopData();
        }

        if (popFilterBaseInterface != null) {
            popFilterBaseInterface.isShow();
        }
    }

    /**
     * @return
     */
    public boolean isShowing() {
        if (popView != null && popView.getParent() != null) {
            return popView.getVisibility() == View.VISIBLE;
        } else {
            return false;
        }
    }

    private OnDisMiss onDisMiss;

    public void setOnDisMiss(OnDisMiss onDisMiss) {
        this.onDisMiss = onDisMiss;
    }

    public interface OnDisMiss {
        void setOnDisMiss();
    }

    /**
     *
     */
    public void dismiss(boolean noanim) {
        if (popView != null) {
            if (!noanim) {
                TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, 0, -popView.getHeight());
                translateAnimation1.setDuration(200);
                translateAnimation1.setInterpolator(new DecelerateInterpolator());
                translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        popView.setVisibility(View.GONE);
                        if (popFilterBaseInterface != null) {
                            popFilterBaseInterface.isDismiss();
                            if (onDisMiss != null) {
                                onDisMiss.setOnDisMiss();
                            }

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ll_mainview.startAnimation(translateAnimation1);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popView.setVisibility(View.GONE);
                        if (popFilterBaseInterface != null) {
                            popFilterBaseInterface.isDismiss();
                            if (onDisMiss != null) {
                                onDisMiss.setOnDisMiss();
                            }
                        }
                    }
                }, 100);
            }
        }
//        if (popView != null && popView.getParent() != null) {
//            ((ViewGroup) this.popView.getParent()).removeView(this.popView);
//        }
    }

    /**
     * @param x
     * @param y
     */
    public void isTouchClose(int x, int y) {
        if (!ToolUtils.isTouchPointInView(popView, x, y) && !(clickView != null && ToolUtils.isTouchPointInView(clickView, x, y))) {
            if (isShowing()) {
                dismiss(true);
            }
        }
    }
}
