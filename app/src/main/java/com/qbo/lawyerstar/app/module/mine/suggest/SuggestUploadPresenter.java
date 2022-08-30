package com.qbo.lawyerstar.app.module.mine.suggest;

import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.BasePresent;

public class SuggestUploadPresenter extends BasePresent<ISuggestUploadView, BaseModel> {

    public Map<String, IndexDictionaryUtils.ValueBean> valueBeanMap = new HashMap<>();
    public List<LocalMedia> selectionData = new ArrayList<>();

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

}
