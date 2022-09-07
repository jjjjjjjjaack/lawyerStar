package com.qbo.lawyerstar.app.module.mine.suggest;

import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.ImagePathBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.mvp1.base.bean.SPathBean;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseMulitRequest;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ToolUtils;

public class SuggestUploadPresenter extends BasePresent<ISuggestUploadView, BaseModel> {

    public Map<String, IndexDictionaryUtils.ValueBean> valueBeanMap = new HashMap<>();
    public List<LocalMedia> selectionData = new ArrayList<>();
    public String content;

    public List<LocalMedia> buildShowList() {
        List<LocalMedia> paths = new ArrayList<>();
        for (LocalMedia localMedia : selectionData) {
            if (paths.size() < 3) {
                paths.add(localMedia);
            }
        }
        if (paths.size() <= 2) {
            paths.add(null);
        }
        return paths;
    }


    public void uploadImages() {
        POST_UPLOAD_FILE_REQ fileReq = new POST_UPLOAD_FILE_REQ();
        fileReq.theme = "image";
        fileReq.path = "law";
        fileReq.baseMulitRequests = new ArrayList<>();
        for (int i = 0; i < selectionData.size(); i++) {
            if (selectionData.get(i) == null) {

            } else {
                File file = new File(selectionData.get(i).getRealPath());
                if (file.exists()) {
                    fileReq.baseMulitRequests.add(new BaseMulitRequest(("file[]"), file, "image/jpeg", "image", "law"));
                } else {
                    selectionData.remove(i);
                    i--;
                }
            }
        }
        doCommRequest(fileReq, true, false, new DoCommRequestInterface<BaseResponse, List<SPathBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<SPathBean> doMap(BaseResponse baseResponse) {
                List<SPathBean> sPathBeans = (List<SPathBean>) SPathBean.fromJSONListAuto(baseResponse.datas, SPathBean.class);
                return sPathBeans;
            }

            @Override
            public void onSuccess(List<SPathBean> s) throws Exception {
                doCreate(s);
//                List<String> housingImage = new ArrayList<>();
//                for (SPathBean sPathBean : s) {
//                    housingImage.add(sPathBean.getPath());
//                }
//                create_collect_house_req.images = housingImage;
//                houseclew_transform_house_req.housingImage = housingImage;
//                if (type.equals("add")) {
//                    postClewTransformHouse();
//                } else {
//                    createCollectHouse();
//                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void doCreate(List<SPathBean> pathBeans) {
        if (valueBeanMap.size() == 0) {
            T(R.string.suggest_upload_tx2_1);
            return;
        }
        if (ToolUtils.isNull(content)) {
            T(R.string.suggest_upload_tx3_1);
            return;
        }
        POST_CREATE_SUGGEST_REQ req = new POST_CREATE_SUGGEST_REQ();
        for (Map.Entry<String, IndexDictionaryUtils.ValueBean> entry : valueBeanMap.entrySet()) {
            req.category_labels = entry.getValue().value;
        }
        req.content = content;
        for (SPathBean bean : pathBeans) {
            req.image.add(new ImagePathBean(bean.path, bean.url));
        }
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
                view().uploadResult(true);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    public void commitClick() {
        if (valueBeanMap.size() == 0) {
            T(R.string.suggest_upload_tx2_1);
            return;
        }
        if (ToolUtils.isNull(content)) {
            T(R.string.suggest_upload_tx3_1);
            return;
        }
        for (int i = 0; i < selectionData.size(); i++) {
            if (selectionData.get(i) == null) {
                selectionData.remove(i);
                i--;
            }
        }
        if (selectionData.size() > 0) {
            uploadImages();
        } else {
            doCreate(new ArrayList<>());
        }
    }

}
