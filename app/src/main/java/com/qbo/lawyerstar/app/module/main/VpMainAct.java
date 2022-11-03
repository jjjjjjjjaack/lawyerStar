package com.qbo.lawyerstar.app.module.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.business.LawBusinessUtils;
import com.qbo.lawyerstar.app.module.business.base.LawBusinessFrag;
import com.qbo.lawyerstar.app.module.home.base.HomeFrag;
import com.qbo.lawyerstar.app.module.mine.base.MineFrag;
import com.qbo.lawyerstar.app.module.mine.login.selecttype.UserSelectTypeAct;
import com.qbo.lawyerstar.app.module.study.base.LawStudyFrag;
import com.qbo.lawyerstar.app.utils.CEventUtils;
import com.qbo.lawyerstar.app.utils.FCacheUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BaseFrag;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ActivityStackUtils;
import framework.mvp1.base.util.CheckVersionUtils;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.StatusBarUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.views.pop.PopupVersionSelectView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static framework.mvp1.base.constant.BROConstant.EXIT_APP_ACTION;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VpMainAct extends MvpAct<IMainView, BaseModel, MainPresenter> implements IMainView {

    public static void openMainAct(Context context) {
//        Intent closeIntent = new Intent(EXIT_APP_ACTION);
//        context.sendBroadcast(closeIntent);
        ActivityStackUtils.getInstance().clearAllActivity();
        context.startActivity(new Intent(context, VpMainAct.class));
    }


    // 所需的全部权限
    public String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    @BindViews({R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4})
    List<View> tabs;
    @BindView(R.id.fragment_container)
    View fragment_container;

    private BaseFrag tempFragment;
    private HomeFrag familyFrag;
    private LawStudyFrag studyFrag;
    private LawBusinessFrag businessFrag;
    private MineFrag mineFrag;

    private List<BaseFrag> fragMap = new ArrayList();
    private Long exitTime = 0L;
    private boolean isJumpRz;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(this, "再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public void baseInitialization() {
        StatusBarUtils.setImmersiveStatusBar(true, Color.BLACK, this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckVersionUtils.getInstance().doVersionCheck(this, fragment_container,
                new PopupVersionSelectView.ISelectUpdate() {
                    @Override
                    public void erro() {

                    }

                    @Override
                    public void doNotUpdate() {
                    }

                    @Override
                    public void agreeUpdate() {
                        CheckVersionUtils.getInstance().readyDownload(VpMainAct.this);
                    }

                    @Override
                    public void disagreeUpdate() {

                    }
                }, true);
        FCacheUtils.getUserInfo(getMContext(), false, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                if (!ToolUtils.String2Boolean(userInfoBean.is_rz) && !isJumpRz) {
                    gotoActivity(UserSelectTypeAct.class);
                    isJumpRz = true;
                }
            }

            @Override
            public void fail() {

            }
        });
        if (tempFragment != null) {
            if (tempFragment instanceof HomeFrag) {
            }
            if (tempFragment instanceof LawBusinessFrag) {
                ((LawBusinessFrag) tempFragment).refresh();
            }
            if (tempFragment instanceof MineFrag) {
                ((MineFrag) tempFragment).refresh();
            }
        }
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_vp_main2;
    }

    @Override
    public void viewInitialization() {
//        clearFragment();
        initBottomTabView();
        tabs.get(0).performClick();
    }

    /**
     * 初始化底部导航栏数据
     */
    private void initBottomTabView() {
        setBottomTabImageAndTx(tabs.get(0), R.drawable.selector_tab_main_1, R.string.one_tab);
        setBottomTabImageAndTx(tabs.get(1), R.drawable.selector_tab_main_2, R.string.two_tab);
        setBottomTabImageAndTx(tabs.get(2), R.drawable.selector_tab_main_3, R.string.three_tab);
        setBottomTabImageAndTx(tabs.get(3), R.drawable.selector_tab_main_4, R.string.four_tab);
        tabs.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBootomTabView(view);
                onFragmentChangeSelected(R.id.tab1);
            }
        });
        tabs.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBootomTabView(view);
                onFragmentChangeSelected(R.id.tab2);
            }
        });
        tabs.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkLogin()) {
                    return;
                }
                if (!LawBusinessUtils.checkIsRz(getMContext(), true)) {
                    return;
                }
                if (!LawBusinessUtils.checkIsVip(getMContext())) {
//                    T.showShort(getMContext(), "请先开通VIP");
                    LawBusinessUtils.showVipTipView(getMContext(),fragment_container);
                    return;
                }
                clickBootomTabView(view);
                onFragmentChangeSelected(R.id.tab3);
            }
        });
        tabs.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBootomTabView(view);
                onFragmentChangeSelected(R.id.tab4);
            }
        });

//        iv_center_tab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickBootomTabView(view);
//                onFragmentChangeSelected(R.id.iv_center_tab);
//            }
//        });
//
    }

    private boolean checkLogin() {
        try {
            FTokenUtils.getToken(this, true);
            return true;
        } catch (NeedLoginException e) {
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 设置目标选择，其余不选中
     *
     * @param nowView
     */
    private void clickBootomTabView(View nowView) {
        for (View tabView : tabs) {
            tabView.setSelected(false);
        }
        nowView.setSelected(true);
    }

    /**
     * 设置底部导航栏的图片与文字
     *
     * @param bottomTab
     * @param imgDrawabel
     * @param texrRes
     */
    private void setBottomTabImageAndTx(View bottomTab, int imgDrawabel, int texrRes) {
        ImageView tab_iv = bottomTab.findViewById(R.id.tab_iv);
        TextView tab_tv = bottomTab.findViewById(R.id.tab_tv);
        tab_iv.setImageResource(imgDrawabel);
        tab_tv.setText(texrRes);
    }


    @Override
    public void doBusiness() {
//        Log.i(MyApplication.JTAG,"1234567890 -> "+ToolUtils.shareQRCodeEncryption("1234567890"));
//        Log.i(MyApplication.JTAG,"1234567890 -> "+new String(ToolUtils.encrypt("2107654;:3".getBytes())));
//        Log.i(MyApplication.JTAG, "1234567890 -> " + new String(ToolUtils.encrypt("1234567890".getBytes())));
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

    /**
     * 同步锁，防止快速点击报错
     */
    private synchronized void onFragmentChangeSelected(int checkId) {
        switch (checkId) {
            case R.id.tab1:
                if (familyFrag == null) {
                    familyFrag = new HomeFrag();
                } else {
                }
                doFragmentChange(familyFrag);
                break;
            case R.id.tab2:
                if (studyFrag == null) {
                    studyFrag = new LawStudyFrag();
                } else {
                }
                doFragmentChange(studyFrag);
                break;
            case R.id.tab3:
                if (businessFrag == null) {
                    businessFrag = new LawBusinessFrag();
                } else {
                    businessFrag.refresh();
                }
                doFragmentChange(businessFrag);
                break;
            case R.id.tab4:
                if (mineFrag == null) {
                    mineFrag = new MineFrag();
                }else{
                    mineFrag.refresh();
                }
                doFragmentChange(mineFrag);
                break;
        }
    }


    /**
     * @param aimFrage
     * @param <T>
     */
    public <T extends BaseFrag> void doFragmentChange(T aimFrage) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        Log.i("aaa", homePageFrag.toString());
        if (aimFrage.isAdded()) {
            if (tempFragment != null) {
                transaction.hide(tempFragment).show(aimFrage).commit();
            } else {
                hideAllShowAim(transaction, aimFrage);
            }
        } else {
            if (tempFragment == null) {
                tempFragment = aimFrage;
                transaction.add(R.id.fragment_container, aimFrage).commit();
            } else {
                transaction.hide(tempFragment).add(R.id.fragment_container, aimFrage).commit();
            }
            fragmentManager.executePendingTransactions();//因为add是异步，需要通知ui快速处理该事件，否则会出现isalready的错误
        }
        tempFragment = aimFrage;
    }

    /**
     * 为了防止重新进入act后，tempFragment是空的情况。需要全部执行隐藏，然后重新显示
     *
     * @param transaction
     * @param aimF
     */
    public void hideAllShowAim(FragmentTransaction transaction, BaseFrag aimF) {
        for (BaseFrag frag : fragMap) {
            try {
                if (frag != null && frag.isAdded()) {
                    transaction.hide(frag);
                }
            } catch (Exception e) {
                fragMap.remove(frag);
            }
        }
        transaction.show(aimF);
        transaction.commit();
        tempFragment = aimF;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        Runtime.getRuntime().gc();
//        Process.killProcess(Process.myPid());
    }

    /**
     * @param
     * @return
     * @description
     * @author jiejack
     * @time 2022/9/14 9:12 下午
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainTabChangePositionEvent(CEventUtils.MainTabChangePositionEvent event) {
        try {
            if (event.pos >= 0 && event.pos < 4) {
                tabs.get(event.pos).performClick();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
