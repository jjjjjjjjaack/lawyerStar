package com.qbo.lawyerstar.app.module.contract.library.detail;

import com.qbo.lawyerstar.app.bean.FOrderPayBean;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.list.IContractLibListView;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class ContractLibDetailPresenter extends BasePresent<IContractLibDetailView, BaseModel> {

    ContractLibBean bean;


    public void createOrder() {
        POST_ORDER_CONTRACT_DOC_CREATE_REQ req = new POST_ORDER_CONTRACT_DOC_CREATE_REQ();
        req.contract_id = bean.id;
        doCommRequest(req, true, true, new DoCommRequestInterface<BaseResponse, FOrderPayBean>() {
            @Override
            public void doStart() {

            }

            @Override
            public FOrderPayBean doMap(BaseResponse baseResponse) {
                FOrderPayBean bean = FOrderPayBean.fromJSONAuto(baseResponse.datas, FOrderPayBean.class);
                return bean;
            }

            @Override
            public void onSuccess(FOrderPayBean bean) throws Exception {
//                T(baseResponse.msg);
                view().createSuccess(bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     * @param
     * @return
     * @description 确认下载
     * @author jieja
     * @time 2022/9/23 11:26
     */
    public void confirmDownload() {
        POST_CONFIRM_CONTRACT_DOWNLOAD_REQ req = new POST_CONFIRM_CONTRACT_DOWNLOAD_REQ();
        req.id = bean.id;
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
                bean.download_num = (ToolUtils.String2Int(bean.download_num) + 1)+"";
                view().confirmDownLoad();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
