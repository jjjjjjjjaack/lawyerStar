package com.qbo.lawyerstar.app.module.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.main.VpMainAct;
import com.qbo.lawyerstar.app.module.mine.login.base.LoginAct;
import com.qbo.lawyerstar.app.module.mine.protocol.ProtocolAct;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.exception.NeedLoginException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.FTokenUtils;
import framework.mvp1.base.util.JnCache;
import framework.mvp1.base.util.PermissionUtils;
import framework.mvp1.base.util.SpanManager;
import framework.mvp1.base.util.StatusBarUtils;
import framework.mvp1.views.other.IndicatorView;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class SplashAct extends MvpAct<ISplashView, BaseModel, SplashPresenter> implements ISplashView {
    // 所需的全部权限
    public String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @BindView(R.id.splash_layout)
    RelativeLayout splash_layout;
    @BindView(R.id.countDown)
    TextView countDown;
    @BindView(R.id.guide_fl)
    View guide_fl;
    @BindView(R.id.guide_vp)
    ViewPager2 guide_vp;
    @BindView(R.id.indicator)
    IndicatorView indicator;

    @BindView(R.id.pact_tv)
    View pact_tv;
    @BindView(R.id.splash_rl)
    View splash_rl;
    @BindView(R.id.login_ll)
    View login_ll;
    @BindView(R.id.accountlogin_tv)
    View accountlogin_tv;
    @BindView(R.id.tv_pact_text)
    TextView tv_pact_text;

    boolean isjump;

    private int countDownTime = 1;
    private Handler countDownHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (countDownTime >= 1) {
                if (countDown == null)
                    return false;
                countDown.setText(countDownTime + "");
                countDownTime--;
                countDownHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                String first = JnCache.getCache(SplashAct.this, "first_insert");
                if (!"1".equals(first)) {
//                    intentMainAct();
                    showGuidePage();
                } else {
                    intentMainAct();
                }

            }
            return false;
        }
    });

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void baseInitialization() {
        StatusBarUtils.setImmersiveStatusBar(true, Color.BLACK, this);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_splash;
    }

    @Override
    public void viewInitialization() {
        accountlogin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(LoginAct.class);
            }
        });
        splash_rl.setVisibility(View.VISIBLE);
        login_ll.setVisibility(View.GONE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                SplashAct.this.doNotUpdate();
                break;
        }
    }

    private void doNotUpdate() {
        doAnimStart();
    }

    public void doAnimStart() {
        try {
            // 渐变展示启动屏
            AlphaAnimation aa = new AlphaAnimation(1.0f, 1.0f);
            aa.setDuration(1000);
            splash_layout.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation arg0) {
//                    countDown.setVisibility(View.VISIBLE);
                    countDownHandler.sendEmptyMessage(0);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void doBusiness() {
        //重新加载启动页的时候判断是否已经加载过一次，
        //防止部分机型每次返回home再返回就重新启动
        if (!isTaskRoot() && getIntent() != null) {
            String action = getIntent().getAction();
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        if (PermissionUtils.lacksPermissions(this, PERMISSIONS)) {//要求所有权限
            ActivityCompat.requestPermissions(SplashAct.this, PERMISSIONS, 1);
        } else {
            SplashAct.this.doNotUpdate();
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }

    /**
     * 进入主界面
     */
    private void intentMainAct() {

        try {
            FTokenUtils.getToken(this, false);
            VpMainAct.openMainAct(getMContext());
        } catch (NeedLoginException e) {
            showLoginView();
        }
    }

    /**
     * @param
     * @return
     * @description
     * @author jiejack
     * @time 2022/8/27 10:21 上午
     */
    private void showLoginView() {
        splash_rl.setVisibility(View.GONE);
        login_ll.setVisibility(View.VISIBLE);
        initPactText(getMContext(), tv_pact_text);
        pact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pact_tv.setSelected(!pact_tv.isSelected());
            }
        });
    }


    /**
     * 初始化协议文本颜色
     */
    public static void initPactText(Context context, TextView tv_pact_text) {
        String pact0 = "《用户协议》";
        String pact1 = "《隐私协议》";
        StringBuffer buffer = new StringBuffer();
        buffer.append("我已阅读并同意");
        int pactStart0 = buffer.length();
        buffer.append(pact0);
        int pactEnd0 = buffer.length();
        buffer.append(" ");
        int pactStart1 = buffer.length();
        buffer.append(pact1);
        int pactEnd1 = buffer.length();
        SpanManager spanManager = SpanManager.getInstance(buffer.toString());
        spanManager.setClickableSpan(pactStart0, pactEnd0, tv_pact_text, new SpanManager.OnTextClickedListener() {
            @Override
            public void onTextClicked(View view) {
                ProtocolAct.openAct(context, "user");
            }
        }).setForegroundColorSpan(pactStart0, pactEnd0, context.getResources().getColor(R.color.c_02c4c3));
        spanManager.setClickableSpan(pactStart1, pactEnd1, tv_pact_text, new SpanManager.OnTextClickedListener() {
            @Override
            public void onTextClicked(View view) {
                ProtocolAct.openAct(context, "privacy");
            }
        }).setForegroundColorSpan(pactStart1, pactEnd1, context.getResources().getColor(R.color.c_02c4c3));
        tv_pact_text.setText(spanManager.toBuild());
    }

    private void showGuidePage() {
        guide_fl.setVisibility(View.VISIBLE);
        indicator.setTotalIndex(4);
        indicator.setCurrentIndex(0);
        guide_vp.setOffscreenPageLimit(3);
        guide_vp.setAdapter(new ViewPagerAdapter(this));
        guide_vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicator.setCurrentIndex(position);
            }
        });
    }


    public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

        public Context context;
        public LayoutInflater mLayoutInflater;
        public List<Integer> titletx = Arrays.asList(R.mipmap.bg_guide_0 , R.mipmap.bg_guide_0_1, R.mipmap.bg_guide_1);

        public ViewPagerAdapter(Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public ViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.view_welcome_guide, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewPagerAdapter.ViewHolder holder, int position) {
            holder.bindViews(titletx.get(position), position);
        }

        @Override
        public int getItemCount() {
            return titletx.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView bg_iv;
            public View click_v;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                bg_iv = itemView.findViewById(R.id.bg_iv);
                click_v = itemView.findViewById(R.id.click_v);
            }

            public void bindViews(int res, int postiton) {
                bg_iv.setImageResource(res);
                if (postiton != titletx.size() - 1) {
                    click_v.setVisibility(View.GONE);
                } else {
                    click_v.setVisibility(View.VISIBLE);
                    click_v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            JnCache.saveCache(SplashAct.this, "first_insert", "1");
                            intentMainAct();
                        }
                    });
                }
            }
        }

    }

    @Override
    public void doReleaseSomething() {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
