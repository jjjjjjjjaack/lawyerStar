package com.qbo.lawyerstar.app.module.inpopview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.net.REQ_Factory;

import java.util.ArrayList;
import java.util.List;

import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BasePresent;
import framework.mvp1.base.net.BaseResponse;

public class InPopSelectCityView extends InPopBaseView {

    RecyclerView province_rcv;
    RecyclerView city_rcv;
    RecyclerView region_rcv;

    TextView reset_tv;
    TextView commit_tv;

    MCommAdapter provinceAdapter;
    MCommAdapter cityAdapter;
    MCommAdapter regionAdapter;

    FCityBean provinceBean;
    FCityBean cityBean;
    FCityBean regionBean;



    public InPopSelectCityView(Context mContext, PopFilterBaseInterface popFilterBaseInterface) {
        super(mContext, popFilterBaseInterface);
    }

    @Override
    public int getLayout() {
        return R.layout.inpopup_select_city;
    }

    @Override
    public void setPopData() {
        getDataList();
    }

    @Override
    public void viewInit() {
        province_rcv = popView.findViewById(R.id.province_rcv);
        city_rcv = popView.findViewById(R.id.city_rcv);
        region_rcv = popView.findViewById(R.id.region_rcv);
        reset_tv = popView.findViewById(R.id.reset_tv);
        commit_tv = popView.findViewById(R.id.commit_tv);
        region_rcv.setVisibility(View.GONE);
        province_rcv.setLayoutManager(new LinearLayoutManager(mContext));
        provinceAdapter = new MCommAdapter(mContext, new MCommVH.MCommVHInterface<FCityBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_inpopup_select_province;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, FCityBean bean) {
                TextView name_tv = (TextView) mCommVH.getView(R.id.name_tv);
                if (provinceBean != null && provinceBean.getValue().equals(bean.getValue())) {
                    name_tv.setSelected(true);
                } else {
                    name_tv.setSelected(false);
                }
                mCommVH.setText(R.id.name_tv, bean.getLabel());
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        provinceBean = bean;
                        cityBean = null;
                        regionBean = null;
                        cityAdapter.setData(bean.getChildren());
                        try {
                            regionAdapter.setData(bean.getChildren().get(0).getChildren());
                        } catch (Exception e) {
                            regionAdapter.setData(new ArrayList());
                        }
                        provinceAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        province_rcv.setAdapter(provinceAdapter);
        city_rcv.setLayoutManager(new LinearLayoutManager(mContext));
        cityAdapter = new MCommAdapter(mContext, new MCommVH.MCommVHInterface<FCityBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_inpopup_select_city;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, FCityBean bean) {
                TextView name_tv = (TextView) mCommVH.getView(R.id.name_tv);
                if (cityBean != null && cityBean.getValue().equals(bean.getValue())) {
                    name_tv.setSelected(true);
                } else {
                    name_tv.setSelected(false);
                }
                mCommVH.setText(R.id.name_tv, bean.getLabel());
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cityBean = bean;
                        regionBean = null;
                        regionAdapter.setData(bean.getChildren());
                        cityAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        city_rcv.setAdapter(cityAdapter);
        region_rcv.setLayoutManager(new LinearLayoutManager(mContext));
        regionAdapter = new MCommAdapter(mContext, new MCommVH.MCommVHInterface<FCityBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_inpopup_select_city;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, FCityBean bean) {
                TextView name_tv = (TextView) mCommVH.getView(R.id.name_tv);
                if (regionBean != null && regionBean.getValue().equals(bean.getValue())) {
                    name_tv.setSelected(true);
                } else {
                    name_tv.setSelected(false);
                }
                mCommVH.setText(R.id.name_tv, bean.getLabel());
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        regionBean = bean;
                        regionAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        region_rcv.setAdapter(regionAdapter);
    }


    public void getDataList() {
        REQ_Factory.GET_CITY_DATA_REQ req = new REQ_Factory.GET_CITY_DATA_REQ();
        BasePresent.doStaticCommRequest(mContext, req, false, true, new BasePresent.DoCommRequestInterface<BaseResponse, List<FCityBean>>() {
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
                    provinceAdapter.setData(cityBeans);
                    if (cityBeans.get(0).getChildren().size() > 0) {
                        cityAdapter.setData(cityBeans.get(0).getChildren());
                        if (cityBeans.get(0).getChildren().get(0).getChildren().size() > 0) {
                            regionAdapter.setData(cityBeans.get(0).getChildren().get(0).getChildren());
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
