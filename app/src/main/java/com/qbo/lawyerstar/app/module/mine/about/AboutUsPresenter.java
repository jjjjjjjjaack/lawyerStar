package com.qbo.lawyerstar.app.module.mine.about;

import com.qbo.lawyerstar.app.module.mine.about.bean.AboutUsBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class AboutUsPresenter extends BasePresent<IAboutUsView, BaseModel> {

    public void getInfo() {
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
                view().showInfo(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
