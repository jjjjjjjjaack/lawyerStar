package com.qbo.lawyerstar.app.module.mine.auth.lawyer;

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
import com.qbo.lawyerstar.app.module.mine.auth.personal.PersonsalAuthAct;
import com.qbo.lawyerstar.app.module.popup.PopupSelectCityView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.constant.IETConstant;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.pics.GlideEngine;

public class LawyerAuthAct extends MvpAct<ILawyerAuthView, BaseModel, LawyerAuthPresenter> implements ILawyerAuthView {


    public final static int CHOOSE_IMAGE_USERLOGO_REQUEST = 2188;
    public final static int CHOOSE_IMAGE_LSZZ_REQUEST = 2189;

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
    @BindView(R.id.select_zz_ll)
    View select_zz_ll;
    @BindView(R.id.select_tipview)
    View select_tipview;
    @BindView(R.id.zz_iv)
    ImageView zz_iv;
    @BindView(R.id.scly_et)
    EditText scly_et;
    @BindView(R.id.cyns_et)
    EditText cyns_et;
    @BindView(R.id.gejx_et)
    EditText gejx_et;
    @BindView(R.id.pliocy_iv)
    View pliocy_iv;
    @BindView(R.id.commit_tv)
    View commit_tv;

    PopupSelectCityView popupSelectCityView;

    @Override
    public LawyerAuthPresenter initPresenter() {
        return new LawyerAuthPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_auth_lawyer;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_auth_lawyer_tx1);

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
                PictureSelector.create((LawyerAuthAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .maxSelectNum(1)
                        .isCamera(true)// 是否显示拍照按钮
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(CHOOSE_IMAGE_USERLOGO_REQUEST);
            }
        });
        select_zz_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create((LawyerAuthAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .maxSelectNum(1)
                        .isCamera(true)// 是否显示拍照按钮
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(CHOOSE_IMAGE_LSZZ_REQUEST);
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

        pliocy_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pliocy_iv.setSelected(!pliocy_iv.isSelected());
            }
        });

        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pliocy_iv.isSelected()){
                    T.showShort(getMContext(),"请先仔细阅读管理办法并勾选同意");
                    return;
                }
                presenter.doLawyerAuth(name_et.getText().toString(),
                        scly_et.getText().toString().trim(),
                        cyns_et.getText().toString().trim(),
                        gejx_et.getText().toString().trim(),
                        woman_ll.isSelected() ? "女" : "男");
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
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CHOOSE_IMAGE_USERLOGO_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            presenter.logoFile = new File(selectList.get(0).getRealPath());
            GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.logoFile.getPath(), userlogo_civ);
        }

        if (requestCode == CHOOSE_IMAGE_LSZZ_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            presenter.lszzFile = new File(selectList.get(0).getRealPath());
            select_tipview.setVisibility(View.GONE);
            zz_iv.setVisibility(View.VISIBLE);
            GlideUtils.loadImageUserLogoDefult(getMContext(), "file://" + presenter.lszzFile.getPath(), zz_iv);
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
        }
    }
}
