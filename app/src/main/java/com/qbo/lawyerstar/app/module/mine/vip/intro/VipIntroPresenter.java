package com.qbo.lawyerstar.app.module.mine.vip.intro;

import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;
import com.qbo.lawyerstar.app.module.mine.vip.bean.VipIntroBean;

import java.util.ArrayList;
import java.util.List;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class VipIntroPresenter extends BasePresent<IVipIntroView, BaseModel> {


    List<VipIntroBean> vipIntroBeans  = new ArrayList<>();
    public void getInfo() {
        GET_VIP_INTRO_REQ req = new GET_VIP_INTRO_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, List<VipIntroBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<VipIntroBean> doMap(BaseResponse baseResponse) {
                List<VipIntroBean> vipIntroBeans = (List<VipIntroBean>) VipIntroBean.fromJSONListAuto(baseResponse.datas, VipIntroBean.class);
                return vipIntroBeans;
            }

            @Override
            public void onSuccess(List<VipIntroBean> beanList) throws Exception {
                view().showInfo(beanList);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void getAppInfo() {
        GET_ABOUT_US_INFO_REQ req = new GET_ABOUT_US_INFO_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, AboutUsBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public AboutUsBean doMap(BaseResponse baseResponse) {
                AboutUsBean usBean = AboutUsBean.fromJSONAuto(baseResponse.datas, AboutUsBean.class);
                return usBean;
            }

            @Override
            public void onSuccess(AboutUsBean bean) throws Exception {
                view().getAppInfoResult(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
