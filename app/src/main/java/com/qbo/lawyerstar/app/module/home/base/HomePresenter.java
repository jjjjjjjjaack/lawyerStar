package com.qbo.lawyerstar.app.module.home.base;

import com.qbo.lawyerstar.app.module.home.bean.HomeDataBean;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class HomePresenter extends BasePresent<IHomeView, BaseModel> {


    public void getData() {
        doCommRequest(new GET_HOMEPAGE_DATA_REQ(), false, true, new DoCommRequestInterface<BaseResponse, HomeDataBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public HomeDataBean doMap(BaseResponse baseResponse) {
                return HomeDataBean.fromJSONAuto(baseResponse.datas, HomeDataBean.class);
            }

            @Override
            public void onSuccess(HomeDataBean homeDataBean) throws Exception {
                view().getDataResult(homeDataBean);
            }

            @Override
            public void onError(Throwable e) {
                view().getDataResult(null);
            }
        });
    }

}
