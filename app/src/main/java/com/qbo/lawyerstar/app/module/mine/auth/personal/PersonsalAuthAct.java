package com.qbo.lawyerstar.app.module.mine.auth.personal;

import static framework.mvp1.base.constant.BROConstant.CLOSE_TRAGETACT_ACTION;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.module.main.VpMainAct;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.LawyerAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean.LawyerAuthBean;
import com.qbo.lawyerstar.app.module.mine.auth.personal.bean.PersonalAuthBean;
import com.qbo.lawyerstar.app.module.mine.suggest.SuggestUploadAct;
import com.qbo.lawyerstar.app.module.popup.PopupSelectCityView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.constant.IETConstant;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.pics.GlideEngine;

public class PersonsalAuthAct extends MvpAct<IPersonsalAuthView, BaseModel, PersonsalAuthPresenter> implements IPersonsalAuthView {

    /**
     * @param
     * @return
     * @description 0创建 1编辑
     * @author jieja
     * @time 2022/9/7 15:07
     */
    public static void openAct(Context context, int type, boolean onlyRead) {
        Intent intent = new Intent(context, PersonsalAuthAct.class);
        intent.putExtra("type", type);
        intent.putExtra("onlyRead", onlyRead);
        context.startActivity(intent);
    }


    @BindView(R.id.logo_rl)
    View logo_rl;
    @BindView(R.id.userlogo_civ)
    ImageView userlogo_civ;
    @BindView(R.id.name_et)
    EditText name_et;
    @BindView(R.id.address_rl)
    View address_rl;
    @BindView(R.id.man_ll)
    View man_ll;
    @BindView(R.id.woman_ll)
    View woman_ll;
    @BindView(R.id.addressinfo_tv)
    TextView addressinfo_tv;
    @BindView(R.id.commit_tv)
    View commit_tv;

    PopupSelectCityView popupSelectCityView;

    int type;//0创建 1编辑
    boolean onlyRead;


    @Override
    public PersonsalAuthPresenter initPresenter() {
        return new PersonsalAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_personal;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_personal_tx1);

        popupSelectCityView = new PopupSelectCityView(this, false, new PopupSelectCityView.SelectCityInterface() {
            @Override
            public void onSelect(FCityBean prvoince, FCityBean city, FCityBean area) {
                presenter.selectPrvoince = prvoince;
                presenter.selectCity = city;
                if (prvoince != null && city != null) {
                    addressinfo_tv.setText(prvoince.getLabel() + " " + city.getLabel());
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
                PictureSelector.create((PersonsalAuthAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .maxSelectNum(1)
                        .isCamera(true)// 是否显示拍照按钮
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        man_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                man_ll.setSelected(true);
                woman_ll.setSelected(false);
            }
        });
        woman_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                woman_ll.setSelected(true);
                man_ll.setSelected(false);
            }
        });
        man_ll.setSelected(true);
        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doPersonalAuth(name_et.getText().toString(), woman_ll.isSelected() ? "女" : "男");
            }
        });
    }

    @Override
    public void doBusiness() {
        type = getIntent().getIntExtra("type", 0);
        onlyRead = getIntent().getBooleanExtra("onlyRead", false);
//        if (type == 1) {
        presenter.getInfoDetail();
//        }
        if (onlyRead) {
            logo_rl.setEnabled(false);
            name_et.setEnabled(false);
            man_ll.setEnabled(false);
            woman_ll.setEnabled(false);
            address_rl.setEnabled(false);
            commit_tv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showInfo(PersonalAuthBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.getAvatar() != null && bean.getAvatar().size() > 0) {
            GlideUtils.loadImageUserLogoDefult(getMContext(), bean.getAvatar().get(0).getUrl(), userlogo_civ);
            presenter.logoNetPath = bean.getAvatar().get(0);
        }
        name_et.setText(bean.getReal_name());
        if ("女".equals(bean.getSex_text())) {
            woman_ll.setSelected(true);
            man_ll.setSelected(false);
        } else {
            woman_ll.setSelected(false);
            man_ll.setSelected(true);
        }

        if (!ToolUtils.isNull(bean.getProvince_id()) && !ToolUtils.isNull(bean.getCity_id())) {
            presenter.selectPrvoince = new FCityBean();
            presenter.selectPrvoince.setId(bean.getProvince_id());
            presenter.selectCity = new FCityBean();
            presenter.selectCity.setId(bean.getCity_id());
            addressinfo_tv.setText(bean.getAddress_info_text());
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    presenter.logoFile = new File(selectList.get(0).getRealPath());
                    presenter.logoNetPath = null;
                    GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.logoFile.getPath(), userlogo_civ);
                } catch (Exception e) {
                }
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
            Intent intent = new Intent(CLOSE_TRAGETACT_ACTION);
            intent.putExtra(IETConstant.CLOSE_TRAGETACT_KEY, "UserSelectTypeAct");
            sendBroadcast(intent);
            finish();
//            VpMainAct.openMainAct(getMContext());
        }
    }

    /**
     * @param
     * @return
     * @description 设置点击其他地方隐藏输入框
     * @author jieja
     * @time 2022/9/13 10:25
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
