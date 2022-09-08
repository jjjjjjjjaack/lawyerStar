package com.qbo.lawyerstar.app.module.mine.info.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FUserInfoBean;
import com.qbo.lawyerstar.app.module.mine.auth.company.CompanyAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.LawyerAuthAct;
import com.qbo.lawyerstar.app.module.mine.auth.personal.PersonsalAuthAct;
import com.qbo.lawyerstar.app.module.mine.info.rename.UserReNameAct;
import com.qbo.lawyerstar.app.module.mine.login.selecttype.UserSelectTypeAct;
import com.qbo.lawyerstar.app.utils.FCacheUtils;
import com.qbo.lawyerstar.app.view.ChangeGasStationImageView2;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ToolUtils;
import framework.mvp1.pics.GlideEngine;

public class UserInfoBaseAct extends MvpAct<IUserInfoBaseView, BaseModel, UserInfoBasePresenter> implements IUserInfoBaseView {

    @BindView(R.id.userlogo_civ)
    ChangeGasStationImageView2 userlogo_civ;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.phone_tv)
    TextView phone_tv;
    @BindView(R.id.usertype_tv)
    TextView usertype_tv;
    @BindView(R.id.addredd_tv)
    TextView addredd_tv;
    @BindView(R.id.companysuinum_tv)
    TextView companysuinum_tv;
    @BindView(R.id.arrow1_iv)
    View arrow1_iv;
    @BindView(R.id.auth_rl)
    View auth_rl;
    @BindView(R.id.rename_rl)
    View rename_rl;

    @Override
    public UserInfoBasePresenter initPresenter() {
        return new UserInfoBasePresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_userinfo;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.user_info_tx1);
        arrow1_iv.setVisibility(View.VISIBLE);
        rename_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(UserReNameAct.class);
            }
        });
        userlogo_civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create((UserInfoBaseAct.this))
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .maxSelectNum(1)
                        .isCamera(true)// 是否显示拍照按钮
                        .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void doWakeUpBusiness() {
        FCacheUtils.getUserInfo(this, false, new FCacheUtils.GetUserInfoInterface() {
            @Override
            public void reslut(boolean isNet, FUserInfoBean userInfoBean) {
                GlideUtils.loadImageUserLogoDefult(getMContext(), userInfoBean.avatar, userlogo_civ);
                name_tv.setText(userInfoBean.nick_name);
                phone_tv.setText(userInfoBean.mobile);
//                usertype_tv.setText(userInfoBean.user_type_tx);
                addredd_tv.setText(userInfoBean.city_name);
                companysuinum_tv.setText(userInfoBean.company_tax);

                if (ToolUtils.isNull(userInfoBean.check_user_type)) {
                    usertype_tv.setText("个人用户");
                    usertype_tv.setTextColor(0xff333333);
                    auth_rl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoActivity(UserSelectTypeAct.class);
                        }
                    });

                } else {
                    if ("-1".equals(userInfoBean.audis_status)) {
                        usertype_tv.setText("重新提交");
                        usertype_tv.setTextColor(0xfff83131);

                        auth_rl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals(userInfoBean.check_user_type)) {//企业
                                    CompanyAuthAct.openAct(getMContext(), 1, false);
                                } else if ("2".equals(userInfoBean.check_user_type)) {
                                    LawyerAuthAct.openAct(getMContext(), 1, false);
                                } else {
                                    gotoActivity(UserSelectTypeAct.class);
                                }
                            }
                        });

                    } else if ("0".equals(userInfoBean.audis_status)) {
                        usertype_tv.setText("审核中");
                        usertype_tv.setTextColor(0xffff8d07);
                        auth_rl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals(userInfoBean.check_user_type)) {//企业
                                    CompanyAuthAct.openAct(getMContext(), 1, true);
                                } else if ("2".equals(userInfoBean.check_user_type)) {//律师
                                    LawyerAuthAct.openAct(getMContext(), 1, true);
                                }
                            }
                        });

                    } else if ("1".equals(userInfoBean.audis_status)) {
                        usertype_tv.setText("已认证");
                        usertype_tv.setTextColor(0xff1BA6FF);
                        arrow1_iv.setVisibility(View.GONE);
                        auth_rl.setOnClickListener(null);
                    } else {
                        usertype_tv.setText("");
                    }
                }
            }

            @Override
            public void fail() {
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                try {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    presenter.changeUserAvatar(new File(selectList.get(0).getRealPath()));
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
    public void changeAvatarResut(String url) {
        if (!ToolUtils.isNull(url)) {
            GlideUtils.loadImageUserLogoDefult(getMContext(), url, userlogo_civ);
        }
    }
}
