package com.qbo.lawyerstar.app.module.inpopview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.util.List;

import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.util.ResourceUtils;

public class InPopSelectEmploymentYearView extends InPopBaseView {

    RecyclerView years_rcv;
    MCommAdapter mCommAdapter;

    TextView reset_tv;
    TextView commit_tv;

    IndexDictionaryUtils.ValueBean cacheBean;//记录最后确定选择的内容
    IndexDictionaryUtils.ValueBean selectBean;

    public interface SelectEmploymentYearInterface extends PopFilterBaseInterface {
        void onConfirm(IndexDictionaryUtils.ValueBean valueBean);
    }

    public InPopSelectEmploymentYearView(Context mContext, SelectEmploymentYearInterface popFilterBaseInterface) {
        super(mContext, popFilterBaseInterface);
    }

    @Override
    public int getLayout() {
        return R.layout.inpopup_select_employment_year;
    }

    @Override
    public void setPopData() {
        getDataList();
    }


    @Override
    public void viewInit() {
        years_rcv = popView.findViewById(R.id.years_rcv);
        reset_tv = popView.findViewById(R.id.reset_tv);
        commit_tv = popView.findViewById(R.id.commit_tv);
        years_rcv.setLayoutManager(new GridLayoutManager(mContext, 4));
        mCommAdapter = new MCommAdapter(mContext, new MCommVH.MCommVHInterface<IndexDictionaryUtils.ValueBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_inpop_item_tv_1;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, IndexDictionaryUtils.ValueBean valueBean) {
                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) mCommVH.itemView.getLayoutParams();
                if(position%4==0){
                  lp.leftMargin = ResourceUtils.dip2px2(context,0);
                  lp.rightMargin = ResourceUtils.dip2px2(context,4);
                }else if(position%4==3){
                    lp.leftMargin = ResourceUtils.dip2px2(context,4);
                    lp.rightMargin = ResourceUtils.dip2px2(context,0);
                }else{
                    lp.leftMargin = ResourceUtils.dip2px2(context,4);
                    lp.rightMargin = ResourceUtils.dip2px2(context,4);
                }
                mCommVH.setText(R.id.name_tv, valueBean.label);
                if (selectBean != null && selectBean.value.equals(valueBean.value)) {
                    mCommVH.itemView.setSelected(true);
                } else {
                    mCommVH.itemView.setSelected(false);
                }
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectBean = valueBean;
                        mCommVH.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        years_rcv.setAdapter(mCommAdapter);
        reset_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBean = null;
                mCommAdapter.notifyDataSetChanged();
            }
        });
        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacheBean = selectBean;
                if (popFilterBaseInterface != null) {
                    ((SelectEmploymentYearInterface) popFilterBaseInterface).onConfirm(selectBean);
                }
            }
        });
    }

    @Override
    public void showPopView(View clickView, boolean noanim) {
        super.showPopView(clickView, noanim);
        if (cacheBean != null) {
            selectBean = cacheBean;
        }
        mCommAdapter.notifyDataSetChanged();
    }

    public void getDataList() {
        IndexDictionaryUtils.getIndexDictionary(mContext, "LawyerYears", new IndexDictionaryUtils.GetDictionaryResult() {
            @Override
            public void reslut(List<IndexDictionaryUtils.ValueBean> beanList) {
                if (beanList != null) {
                    mCommAdapter.setData(beanList);
                }
            }
        });
    }
}
