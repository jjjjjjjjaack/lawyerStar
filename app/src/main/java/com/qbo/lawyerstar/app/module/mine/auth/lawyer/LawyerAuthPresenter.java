package com.qbo.lawyerstar.app.module.mine.auth.lawyer;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import framework.mvp1.base.bean.SPathBean;
import framework.mvp1.base.exception.ViewnullException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class LawyerAuthPresenter extends BasePresent<ILawyerAuthView, BaseModel> {

    File logoFile;//头像
    File lszzFile;//律师执照
    FCityBean selectPrvoince, selectCity, selectArea;

    public void doLawyerAuth(String name, String expertise,
                             String employment_year, String intro, String sex) {
        if (ToolUtils.isNull(name)) {
            T(R.string.user_info_tx3_1);
            return;
        }
        if (ToolUtils.isNull(expertise)) {
            T(R.string.user_auth_lawyer_tx2_1);
            return;
        }
        if (ToolUtils.isNull(employment_year)) {
            T(R.string.user_auth_lawyer_tx3_1);
            return;
        }
        if (ToolUtils.isNull(intro)) {
            T(R.string.user_auth_lawyer_tx4_1);
            return;
        }
        if (logoFile == null) {
            T(R.string.user_info_tx2_1);
            return;
        }
        if (lszzFile == null) {
            T(R.string.user_auth_lawyer_tx5_1);
            return;
        }
        if (selectPrvoince == null || selectCity == null || selectArea == null) {
            T(R.string.user_info_tx6_1);
            return;
        }
        try {
            ToolUtils.uploadImages(context(), Arrays.asList(logoFile, lszzFile), new ToolUtils.UploadFileInterface() {
                @Override
                public void uploadSuccess(List<SPathBean> sPathBeans) {
                    if (sPathBeans != null && sPathBeans.size() == 2) {
                        POST_AUTH_LAWYER_REQ req = new POST_AUTH_LAWYER_REQ();
                        req.real_name = name;
                        req.avatar = Arrays.asList(new ImagePath(sPathBeans.get(0).path, sPathBeans.get(0).url));
                        req.lawyer_license =  Arrays.asList(new ImagePath(sPathBeans.get(1).path, sPathBeans.get(1).url));
                        req.address_info = Arrays.asList(selectPrvoince.getId(), selectCity.getId(), selectArea.getId());
                        req.sex = sex;
                        req.expertise = expertise;
                        req.employment_year = employment_year;
                        req.intro = intro;
                        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, BaseResponse>() {
                            @Override
                            public void doStart() {

                            }

                            @Override
                            public BaseResponse doMap(BaseResponse baseResponse) {
                                return baseResponse;
                            }

                            @Override
                            public void onSuccess(BaseResponse baseResponse) throws Exception {
                                T(baseResponse.msg);
                                view().authResult(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view().authResult(false);
                            }
                        });
                    } else {
                        T("图片上传失败");
                    }
                }

                @Override
                public void uploadFails() {

                }
            });
        } catch (ViewnullException e) {

        }


    }
}
