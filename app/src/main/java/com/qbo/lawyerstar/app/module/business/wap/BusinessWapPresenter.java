package com.qbo.lawyerstar.app.module.business.wap;

import com.alibaba.fastjson.JSONObject;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class BusinessWapPresenter extends BasePresent<IBusinessWapView, BaseModel> {

    public void getWapUrl(String urlKey) {
        GET_WAPPAGE_URL_REQ req = new GET_WAPPAGE_URL_REQ();
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, WapUrlBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public WapUrlBean doMap(BaseResponse baseResponse) {
                try {
                    WapUrlBean urlBean = WapUrlBean.fromJSONAuto(JSONObject.parseObject(baseResponse.datas)
                            .getString(urlKey), WapUrlBean.class);
                    return urlBean;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onSuccess(WapUrlBean bean) throws Exception {
    view().setWap(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
