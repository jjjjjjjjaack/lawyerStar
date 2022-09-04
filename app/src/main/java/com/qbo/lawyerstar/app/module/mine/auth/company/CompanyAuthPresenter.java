package com.qbo.lawyerstar.app.module.mine.auth.company;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import framework.mvp1.base.bean.SPathBean;
import framework.mvp1.base.exception.ViewnullException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class CompanyAuthPresenter extends BasePresent<ICompanyAuthView, BaseModel> {

    File logoFile;//头像
    File yyzzFile;//营业执照
    FCityBean selectPrvoince, selectCity, selectArea;
    IndexDictionaryUtils.ValueBean industryBean;
    IndexDictionaryUtils.ValueBean enterpricesizeBean;
    public String established_date;

    public void doCompanyAuth(String company_name,String address,String unified_code
    ,String legal_person,String responsible_mobile) {
        if (ToolUtils.isNull(company_name)) {
            T(R.string.user_auth_company_tx3_1);
            return;
        }
        if (ToolUtils.isNull(address)) {
            T(R.string.user_auth_company_tx6_1);
            return;
        }
        if (ToolUtils.isNull(unified_code)) {
            T(R.string.user_auth_company_tx4_1);
            return;
        }
        if (ToolUtils.isNull(legal_person)) {
            T(R.string.user_auth_company_tx7_1);
            return;
        }
        if (ToolUtils.isNull(responsible_mobile)) {
            T(R.string.user_auth_company_tx8_1);
            return;
        }
        if (ToolUtils.isNull(established_date)) {
            T(R.string.user_auth_company_tx11_1);
            return;
        }
        if (industryBean == null) {
            T(R.string.user_auth_company_tx9_1);
            return;
        }
        if (enterpricesizeBean == null) {
            T(R.string.user_auth_company_tx10_1);
            return;
        }
        if (logoFile == null) {
            T(R.string.user_info_tx2_1);
            return;
        }
        if (yyzzFile == null) {
            T(R.string.user_auth_company_tx2_1);
            return;
        }
        if (selectPrvoince == null || selectCity == null || selectArea == null) {
            T(R.string.user_info_tx6_1);
            return;
        }
        try {
            ToolUtils.uploadImages(context(), Arrays.asList(logoFile, yyzzFile), new ToolUtils.UploadFileInterface() {
                @Override
                public void uploadSuccess(List<SPathBean> sPathBeans) {
                    if (sPathBeans != null && sPathBeans.size() == 2) {
                        POST_AUTH_COMPANY_REQ req = new POST_AUTH_COMPANY_REQ();
                        req.company_name = company_name;
                        req.address = address;
                        req.unified_code = unified_code;
                        req.legal_person = legal_person;
                        req.responsible_mobile = responsible_mobile;
                        req.industry = industryBean.value;
                        req.enterprise_size = enterpricesizeBean.value;
                        req.established_date = established_date;
                        req.avatar = new ImagePath(sPathBeans.get(0).path, sPathBeans.get(0).url);
                        req.business_license = new ImagePath(sPathBeans.get(1).path, sPathBeans.get(1).url);
                        req.address_info = Arrays.asList(selectPrvoince.getId(), selectCity.getId(), selectArea.getId());
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
