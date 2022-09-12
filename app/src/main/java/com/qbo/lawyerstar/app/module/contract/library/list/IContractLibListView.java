package com.qbo.lawyerstar.app.module.contract.library.list;

import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;

import java.util.List;

import framework.mvp1.base.f.BaseView;

public interface IContractLibListView extends BaseView {
    void loadDataResult(boolean isRefresh, List<ContractLibBean> contractLibBeans);
}
