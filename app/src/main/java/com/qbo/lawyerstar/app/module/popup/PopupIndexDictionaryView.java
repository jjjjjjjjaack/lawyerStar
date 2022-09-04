package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;

import com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.util.List;

import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupIndexDictionaryView extends PopupBaseView {


    private WheelView rotate_1;
    private String indexKey;
    private View sure_tv, close_tv;
    private SelectResultInterface selectResultInterface;

    public interface SelectResultInterface {
        void onSelect(IndexDictionaryUtils.ValueBean valueBean);
    }

    public PopupIndexDictionaryView(Context context,String indexKey, SelectResultInterface selectResultInterface) {
        super(context, 0, ResourceUtils.getScreenHeight(context) / 3);
        this.indexKey = indexKey;
        this.selectResultInterface = selectResultInterface;
        getDataList();
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_one_wheel_view;
    }

    @Override
    public void initPopupView() {
        sure_tv = popView.findViewById(R.id.sure_tv);
        close_tv = popView.findViewById(R.id.close_tv);
        rotate_1 = popView.findViewById(R.id.rotate_1);

        rotate_1.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {

            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {

            }

            @Override
            public void onWheelLoopFinished(WheelView view) {

            }
        });

        sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rotate_1.getData() == null || rotate_1.getData().size() == 0) {
                    T.showShort(context, "数据获取中,请稍后");
                    return;
                }
                if (selectResultInterface != null) {
                    selectResultInterface.onSelect(rotate_1.getCurrentItem());
                }
                dismiss();
            }
        });
        close_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void getDataList() {
        IndexDictionaryUtils.getIndexDictionary(context, indexKey, new IndexDictionaryUtils.GetDictionaryResult() {
            @Override
            public void reslut(List<IndexDictionaryUtils.ValueBean> beanList) {
                if (beanList != null) {
                    rotate_1.setData(beanList);
                }
            }
        });
    }

}
