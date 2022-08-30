package com.qbo.lawyerstar.app.utils;

import android.content.Context;

import com.qbo.lawyerstar.app.net.REQ_Factory;
import com.qbo.lawyerstar.app.net.RES_Factory;

import java.util.List;

import framework.mvp1.base.bean.BaseBean;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class IndexDictionaryUtils {

    public static class ValueBean extends BaseBean {
        public String label;
        public String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public interface GetDictionaryResult {
        void reslut(List<ValueBean> beanList);
    }

    public static void getIndexDictionary(Context mCtxt, String key, GetDictionaryResult getDictionaryResult) {
        BasePresent.doStaticCommRequest(mCtxt, new REQ_Factory.GET_INDEX_DICTIONARY_REQ(key), false,
                true, new BasePresent.DoCommRequestInterface<RES_Factory.GET_INDEX_DICTIONARY_RES, List<ValueBean>>() {
                    @Override
                    public void doStart() {

                    }

                    @Override
                    public List<ValueBean> doMap(RES_Factory.GET_INDEX_DICTIONARY_RES res) {
                        return res.rows;
                    }

                    @Override
                    public void onSuccess(List<ValueBean> valueBeans) throws Exception {
                        if (getDictionaryResult != null) {
                            getDictionaryResult.reslut(valueBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
