package com.qbo.lawyerstar.app.module.mine.info.base;

import android.content.Context;

import com.qbo.lawyerstar.app.bean.ImagePathBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.mvp1.base.bean.SPathBean;
import framework.mvp1.base.exception.ViewnullException;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.net.BaseMulitRequest;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class UserInfoBasePresenter extends BasePresent<IUserInfoBaseView, BaseModel> {

    public void changeUserAvatar(File logoFile) {
        if (logoFile == null || !logoFile.exists()) {
            return;
        }
        REQ_Factory.POST_CHANGE_USERAVATAR_REQ fileReq = new REQ_Factory.POST_CHANGE_USERAVATAR_REQ();
        fileReq.baseMulitRequests = new ArrayList<>();
        fileReq.baseMulitRequests.add(new BaseMulitRequest(("file[]"),
                logoFile, "image/*", "image", "law"));
        
//        doCommRequest(fileReq, true, true, new DoCommRequestInterface<Object, Object>() {
//        });
    }
}
