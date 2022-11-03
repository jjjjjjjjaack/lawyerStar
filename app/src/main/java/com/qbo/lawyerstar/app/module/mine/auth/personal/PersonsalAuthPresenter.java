package com.qbo.lawyerstar.app.module.mine.auth.personal;

import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.bean.ImagePathBean;
import com.qbo.lawyerstar.app.module.mine.auth.lawyer.bean.LawyerAuthBean;
import com.qbo.lawyerstar.app.module.mine.auth.personal.bean.PersonalAuthBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.mvp1.base.bean.SPathBean;
import framework.mvp1.base.exception.ViewnullException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseMulitRequest;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class PersonsalAuthPresenter extends BasePresent<IPersonsalAuthView, BaseModel> {

    File logoFile;
    ImagePathBean logoNetPath;
    FCityBean selectPrvoince, selectCity;


    public void getInfoDetail() {
        GET_AUTH_PERSONAL_DETAILINFO_REQ req = new GET_AUTH_PERSONAL_DETAILINFO_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, PersonalAuthBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public PersonalAuthBean doMap(BaseResponse baseResponse) {
                PersonalAuthBean bean = LawyerAuthBean.fromJSONAuto(baseResponse.datas, PersonalAuthBean.class);
                return bean;
            }

            @Override
            public void onSuccess(PersonalAuthBean bean) throws Exception {
                view().showInfo(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * @param
     * @return
     * @description
     * @author jiejack
     * @time 2022/9/4 3:15 下午
     */
    public void doPersonalAuth(String name, String sex) {
        if (ToolUtils.isNull(name)) {
            T(R.string.user_info_tx3_1);
            return;

        }
        if (logoFile == null && logoNetPath == null) {
            T(R.string.user_info_tx2_1);
            return;
        }
        if (selectPrvoince == null || selectCity == null) {
            T(R.string.user_info_tx6_1);
            return;
        }
        try {
            if (logoFile != null) {
                ToolUtils.uploadImages(context(), logoFile, new ToolUtils.UploadFileInterface() {
                    @Override
                    public void uploadSuccess(List<SPathBean> sPathBeans) {
                        if (sPathBeans != null && sPathBeans.size() > 0) {
                            logoNetPath = new ImagePathBean(sPathBeans.get(0).path, sPathBeans.get(0).url);
                            commit(name, sex);
                        } else {
                            T("图片上传失败");
                        }

                    }

                    @Override
                    public void uploadFails() {
                    }
                });
            } else {
                commit(name, sex);
            }
        } catch (ViewnullException e) {

        }
    }

    public void commit(String name, String sex) {
        if (logoNetPath == null) {
            T(R.string.user_info_tx2_1);
            return;
        }
        POST_AUTH_PERSONAL_REQ req = new POST_AUTH_PERSONAL_REQ();
        req.real_name = name;
//        req.avatar = Arrays.asList(new ImagePathBean(sPathBeans.get(0).path, sPathBeans.get(0).url));
        req.avatar = Arrays.asList(logoNetPath);
        req.address_info = Arrays.asList(selectPrvoince.getId(), selectCity.getId());
        req.sex = sex;
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
    }

}
