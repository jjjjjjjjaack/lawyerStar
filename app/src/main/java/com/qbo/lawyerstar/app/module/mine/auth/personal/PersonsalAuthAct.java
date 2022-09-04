package com.qbo.lawyerstar.app.module.mine.auth.personal;

import static framework.mvp1.base.constant.BROConstant.CLOSE_TRAGETACT_ACTION;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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
import com.qbo.lawyerstar.app.module.mine.suggest.SuggestUploadAct;
import com.qbo.lawyerstar.app.module.popup.PopupSelectCityView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.constant.IETConstant;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.pics.GlideEngine;

public class PersonsalAuthAct extends MvpAct<IPersonsalAuthView, BaseModel, PersonsalAuthPresenter> implements IPersonsalAuthView {


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

    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                presenter.logoFile = new File(selectList.get(0).getRealPath());
                GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.logoFile.getPath(), userlogo_civ);
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
}
