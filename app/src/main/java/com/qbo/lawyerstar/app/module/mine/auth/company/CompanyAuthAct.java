package com.qbo.lawyerstar.app.module.mine.auth.company;

import static framework.mvp1.base.constant.BROConstant.CLOSE_TRAGETACT_ACTION;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.module.mine.auth.company.bean.CompanyAuthBean;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.LawyerAuthAct;
import com.qbo.lawyerstar.app.module.popup.PopupCommitSuccessView;
import com.qbo.lawyerstar.app.module.popup.PopupIndexDictionaryView;
import com.qbo.lawyerstar.app.module.popup.PopupSelectCityView;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;
import com.qbo.lawyerstar.app.view.timeview.MineTimePickerBuilder;
import com.qbo.lawyerstar.app.view.timeview.MineTimePickerView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.constant.IETConstant;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.pics.GlideEngine;

public class CompanyAuthAct extends MvpAct<ICompanyAuthView, BaseModel, CompanyAuthPresenter> implements ICompanyAuthView {

    /**
     * @param
     * @return
     * @description 0?????? 1??????
     * @author jieja
     * @time 2022/9/7 15:07
     */
    public static void openAct(Context context, int type, boolean onlyRead) {
        Intent intent = new Intent(context, CompanyAuthAct.class);
        intent.putExtra("type", type);
        intent.putExtra("onlyRead", onlyRead);
        context.startActivity(intent);
    }


    public final static int CHOOSE_IMAGE_USERLOGO_REQUEST = 2188;
    public final static int CHOOSE_IMAGE_YYZZ_REQUEST = 2189;

    @BindView(R.id.select_zz_ll)
    View select_zz_ll;
    @BindView(R.id.select_tipview)
    View select_tipview;
    @BindView(R.id.logo_rl)
    View logo_rl;
    @BindView(R.id.userlogo_civ)
    ImageView userlogo_civ;
    @BindView(R.id.zz_iv)
    ImageView zz_iv;
    @BindView(R.id.name_et)
    EditText name_et;
    @BindView(R.id.xxcode_et)
    EditText xxcode_et;
    @BindView(R.id.address_rl)
    View address_rl;
    @BindView(R.id.addressinfo_tv)
    TextView addressinfo_tv;
    @BindView(R.id.addressinfo_et)
    EditText addressdetail_et;
    @BindView(R.id.leagal_et)
    EditText leagal_et;
    @BindView(R.id.phone_et)
    EditText phone_et;

    @BindView(R.id.industry_rl)
    View industry_rl;
    @BindView(R.id.industry_tv)
    TextView industry_tv;

    @BindView(R.id.enterpricesize_rl)
    View enterpricesize_rl;
    @BindView(R.id.enterpricesize_tv)
    TextView enterpricesize_tv;

    @BindView(R.id.bulidtime_rl)
    View bulidtime_rl;
    @BindView(R.id.bulidtime_tv)
    TextView bulidtime_tv;
    private MineTimePickerView ptimeView;

    @BindView(R.id.commit_tv)
    View commit_tv;

    @BindView(R.id.status_ll)
    View status_ll;
    @BindView(R.id.status_iv)
    ImageView status_iv;
    @BindView(R.id.status_reason_tv)
    TextView status_reason_tv;


    PopupSelectCityView popupSelectCityView;
    PopupCommitSuccessView commitSuccessView;
    PopupIndexDictionaryView industryPopView;//??????????????????
    PopupIndexDictionaryView enterpriceSizePopView;//??????????????????

    int type;//0?????? 1??????
    boolean onlyRead;

    @Override
    public CompanyAuthPresenter initPresenter() {
        return new CompanyAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_company;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_company_tx1);

        commitSuccessView = new PopupCommitSuccessView(getMContext());
        commitSuccessView.setOnDismissInterface(new PopupCommitSuccessView.OnDismissInterface() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(CLOSE_TRAGETACT_ACTION);
                intent.putExtra(IETConstant.CLOSE_TRAGETACT_KEY, "UserSelectTypeAct");
                sendBroadcast(intent);
                finish();
            }
        });

        popupSelectCityView = new PopupSelectCityView(this, true, new PopupSelectCityView.SelectCityInterface() {
            @Override
            public void onSelect(FCityBean prvoince, FCityBean city, FCityBean area) {
                presenter.selectPrvoince = prvoince;
                presenter.selectCity = city;
                presenter.selectArea = area;
                if (prvoince != null && city != null && area != null) {
                    addressinfo_tv.setText(prvoince.getLabel() + " "
                            + city.getLabel() + " " +
                            (area == null ? "" : area.getLabel()));
                }
            }
        });
        address_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelectCityView.showBottom(v);
            }
        });
        logo_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create((CompanyAuthAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// ?????? or ??????
                        .maxSelectNum(1)
                        .isCamera(true)// ????????????????????????
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(CHOOSE_IMAGE_USERLOGO_REQUEST);
            }
        });
        select_zz_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create((CompanyAuthAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// ?????? or ??????
                        .maxSelectNum(1)
                        .isCamera(true)// ????????????????????????
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(CHOOSE_IMAGE_YYZZ_REQUEST);
            }
        });
        //????????????
        industry_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                industryPopView.showBottom(view);
            }
        });
        industryPopView = new PopupIndexDictionaryView(getMContext(), "Industry",
                new PopupIndexDictionaryView.SelectResultInterface() {
                    @Override
                    public void onSelect(IndexDictionaryUtils.ValueBean valueBean) {
                        presenter.industryBean = valueBean;
                        industry_tv.setText(valueBean.label);
                    }
                });
        //????????????
        enterpricesize_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterpriceSizePopView.showBottom(view);
            }
        });
        enterpriceSizePopView = new PopupIndexDictionaryView(getMContext(), "EnterpriceSize",
                new PopupIndexDictionaryView.SelectResultInterface() {
                    @Override
                    public void onSelect(IndexDictionaryUtils.ValueBean valueBean) {
                        presenter.enterpricesizeBean = valueBean;
                        enterpricesize_tv.setText(valueBean.label);
                    }
                });

        bulidtime_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                bulidPickTime();
                if (ptimeView == null) {
                    bulidPickTime();
                }
                ptimeView.show();
            }
        });

        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCompanyAuth(name_et.getText().toString().trim(),
                        addressdetail_et.getText().toString().trim(),
                        xxcode_et.getText().toString().trim(),
                        leagal_et.getText().toString().trim(),
                        phone_et.getText().toString().trim());
            }
        });
    }

    @Override
    public void doBusiness() {
        type = getIntent().getIntExtra("type", 0);
        onlyRead = getIntent().getBooleanExtra("onlyRead", false);
        if (type == 1) {
            presenter.getInfoDetail();
        }
        if (onlyRead) {
            setOnlyRead();
        }
    }

    public void setOnlyRead() {
        onlyRead = true;
        select_zz_ll.setEnabled(false);
        logo_rl.setEnabled(false);
        name_et.setEnabled(false);
        xxcode_et.setEnabled(false);
        address_rl.setEnabled(false);
        addressdetail_et.setEnabled(false);
        leagal_et.setEnabled(false);
        phone_et.setEnabled(false);
        industry_rl.setEnabled(false);
        enterpricesize_rl.setEnabled(false);
        bulidtime_rl.setEnabled(false);
        commit_tv.setVisibility(View.GONE);
    }

    @Override
    public void showInfo(CompanyAuthBean bean) {
        if (bean == null) {
            return;
        }

        if (bean.getBusiness_license() != null && bean.getBusiness_license().size() > 0) {
            zz_iv.setVisibility(View.VISIBLE);
            GlideUtils.loadImageDefult(getMContext(), bean.getBusiness_license().get(0).url, zz_iv);
        }
        if (bean.getAvatar() != null && bean.getAvatar().size() > 0) {
            GlideUtils.loadImageUserLogoDefult(getMContext(), bean.getAvatar().get(0).url, userlogo_civ);
        }
        name_et.setText(bean.getCompany_name());
        xxcode_et.setText(bean.getUnified_code());
        addressinfo_tv.setText(bean.getAddress_info_text());
        addressdetail_et.setText(bean.getAddress());
        leagal_et.setText(bean.getLegal_person());
        phone_et.setText(bean.getResponsible_mobile());
        industry_tv.setText(bean.getIndustry_text());
        enterpricesize_tv.setText(bean.getEnterprise_size_text());
        bulidtime_tv.setText(bean.getEstablished_date());


        if ("pending".equals(bean.getStatus())) {
            status_ll.setVisibility(View.VISIBLE);
            status_ll.setBackgroundResource(R.drawable.shape_1af3a239_r4);
            status_iv.setImageResource(R.mipmap.ic_auth_shenhe_top_iv_1);
            status_reason_tv.setText(bean.getCheck_text());
            status_reason_tv.setTextColor(0xffF3A239);
            setOnlyRead();
        } else if ("refuse".equals(bean.getStatus())) {
            status_ll.setVisibility(View.VISIBLE);
            status_ll.setBackgroundResource(R.drawable.shape_1adf686b_r4);
            status_iv.setImageResource(R.mipmap.ic_auth_refues_top_iv_1);
            status_reason_tv.setText(bean.getCheck_text());
            status_reason_tv.setTextColor(0xffDF686B);
        } else {
            status_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }


    public void bulidPickTime() {
        Calendar mincalendar = Calendar.getInstance();
        Calendar nowcalendar = Calendar.getInstance();
        mincalendar.set(nowcalendar.get(Calendar.YEAR) - 1000, 12, 31);

        ptimeView = new MineTimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                bulidtime_tv.setText(ToolUtils.timestamp2String(date.getTime(), "yyyy-MM-dd"));
                presenter.established_date = bulidtime_tv.getText().toString().trim();
                ptimeView.dismiss();
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// ??????????????????
//                .setCancelText(getString(R.string.back_cancel))//??????????????????
//                .setSubmitText(getString(R.string.comfirm_step))//??????????????????
                .setContentTextSize(16)//??????????????????
//                .setTitleSize(20)//??????????????????
                .setTitleText("???????????????")//????????????
                .setTextColorCenter(0xffE0B03C)
                .setOutSideCancelable(false)//???????????????????????????????????????????????????????????????
                .isCyclic(false)//??????????????????
                .setTitleColor(Color.BLACK)//??????????????????
                .setSubmitColor(0xffE0B03C)//????????????????????????
                .setCancelColor(Color.BLACK)//????????????????????????
                .setRangDate(mincalendar, nowcalendar)
                .setDate(nowcalendar)
                .setLabel("???", "???", "???", "???", "???", "???")//?????????????????????????????????
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .isDialog(true)//??????????????????????????????
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {

                    }
                })
                .build();
        Window window = ptimeView.getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.2f;
        // ????????????
        window.setAttributes(lp);
        // ??? DecorView ????????????????????????????????????????????? Dialog ???????????????????????????????????????????????? padding????????????????????????
        //window.getDecorView().setBackgroundResource(R.drawable.bg_white_top_radius);
        //????????????????????????????????????????????????????????????????????????
        ptimeView.getDialog().getWindow().setWindowAnimations(R.style.BottomAnimStyle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHOOSE_IMAGE_USERLOGO_REQUEST) {
            try {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                presenter.logoFile = new File(selectList.get(0).getRealPath());
                GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.logoFile.getPath(), userlogo_civ);
            }catch (Exception e){
            }
        }

        if (requestCode == CHOOSE_IMAGE_YYZZ_REQUEST) {
            try {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                presenter.yyzzFile = new File(selectList.get(0).getRealPath());
                zz_iv.setVisibility(View.VISIBLE);
                select_tipview.setVisibility(View.GONE);
                GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.yyzzFile.getPath(), zz_iv);
            }catch (Exception e){
            }
        }
    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void authResult(boolean b) {
        if (b) {
            presenter.getInfoDetail();
            commitSuccessView.showCenter(userlogo_civ);
        }
    }


    /**
     * @description ???????????????????????????????????????
     * @param
     * @return
     * @author jieja
     * @time 2022/9/13 10:25
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}
