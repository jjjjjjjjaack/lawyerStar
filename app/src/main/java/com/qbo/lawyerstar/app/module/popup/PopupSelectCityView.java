package com.qbo.lawyerstar.app.module.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.github.gzuliyujiang.wheelview.contract.OnWheelChangedListener;
import com.github.gzuliyujiang.wheelview.widget.WheelView;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import java.util.List;

import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.T;
import framework.mvp1.views.pop.PopupBaseView;

public class PopupSelectCityView extends PopupBaseView {

    private WheelView rotate_1, rotate_2, rotate_3;
    private boolean showArea;
    private View sure_tv, close_tv;
    private SelectCityInterface selectCityInterface;

    public interface SelectCityInterface {
        void onSelect(FCityBean prvoince, FCityBean city,FCityBean areaBean);
    }

    public PopupSelectCityView(Context context, boolean showArea, SelectCityInterface selectCityInterface) {
        super(context, 0, ResourceUtils.getScreenHeight(context) / 3);
        this.selectCityInterface = selectCityInterface;
    }

    @Override
    public int getLayoutID() {
        return R.layout.popup_select_city;
    }

    @Override
    public void initPopupView() {
        sure_tv = popView.findViewById(R.id.sure_tv);
        close_tv = popView.findViewById(R.id.close_tv);
        rotate_1 = popView.findViewById(R.id.rotate_1);
        rotate_2 = popView.findViewById(R.id.rotate_2);
        rotate_3 = popView.findViewById(R.id.rotate_3);

        rotate_3.setVisibility(showArea?View.VISIBLE:View.GONE);
        rotate_1.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {

            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
                try {
                    FCityBean cityBean = (FCityBean) rotate_1.getData().get(position);
                    rotate_2.setData(cityBean.getChildren());
                } catch (Exception e) {

                }
            }

            @Override
            public void onWheelScrollStateChanged(WheelView view, int state) {

            }

            @Override
            public void onWheelLoopFinished(WheelView view) {

            }
        });
        rotate_2.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onWheelScrolled(WheelView view, int offset) {

            }

            @Override
            public void onWheelSelected(WheelView view, int position) {
                try {
                    FCityBean cityBean = (FCityBean) rotate_2.getData().get(position);
                    rotate_3.setData(cityBean.getChildren());
                } catch (Exception e) {

                }
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
                if(rotate_1.getData()==null||rotate_1.getData().size()==0){
                    T.showShort(context,"数据获取中,请稍后");
                    return;
                }
                if (selectCityInterface != null) {
                    selectCityInterface.onSelect(rotate_1.getCurrentItem(), rotate_2.getCurrentItem()
                            ,rotate_3.getCurrentItem());
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

        getDataList();
    }

    public void getDataList() {
        REQ_Factory.GET_CITY_DATA_REQ req = new REQ_Factory.GET_CITY_DATA_REQ();
        BasePresent.doStaticCommRequest(context, req, false, true, new BasePresent.DoCommRequestInterface<BaseResponse, List<FCityBean>>() {
            @Override
            public void doStart() {

            }

            @Override
            public List<FCityBean> doMap(BaseResponse baseResponse) {
                List<FCityBean> provinces = (List<FCityBean>) FCityBean.fromJSONListAuto(baseResponse.datas, FCityBean.class);
                return provinces;
            }

            @Override
            public void onSuccess(List<FCityBean> cityBeans) throws Exception {
                if (cityBeans.size() > 0) {
                    rotate_1.setData(cityBeans);
                    rotate_1.setDefaultPosition(0);
                    if (cityBeans.get(0).getChildren().size() > 0) {
                        rotate_2.setData(cityBeans.get(0).getChildren());
                        if (cityBeans.get(0).getChildren().get(0).getChildren().size() > 0) {
                            rotate_3.setData(cityBeans.get(0).getChildren().get(0).getChildren());
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

}
