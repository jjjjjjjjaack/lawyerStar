package com.qbo.lawyerstar.app.module.contract.library.list;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.contract.library.bean.ContractLibBean;
import com.qbo.lawyerstar.app.module.contract.library.detail.ContractLibDetailAct;
import com.qbo.lawyerstar.app.module.inpopview.InPopSelectIndexDictionaryView;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.ResourceUtils;

public class ContractLibListAct extends MvpAct<IContractLibListView, BaseModel,
        ContractLibListPresenter> implements IContractLibListView {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    MCommAdapter mCommAdapter;

    @BindView(R.id.search_et)
    EditText search_et;
    @BindView(R.id.category_tv)
    TextView category_tv;
    @BindView(R.id.business_tv)
    TextView business_tv;

    @BindView(R.id.category_ll)
    View category_ll;
    @BindView(R.id.business_ll)
    View business_ll;
    InPopSelectIndexDictionaryView categoryInPopView;
    InPopSelectIndexDictionaryView businessInPopView;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_contract_library_list;
    }

    @Override
    public void viewInitialization() {
        setBackPress();

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.getData(true, search_et.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(true, search_et.getText().toString().trim());
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(false, "");
            }
        });

        rcy.setLayoutManager(new GridLayoutManager(this, 2));
        mCommAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<ContractLibBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_contract_lib_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, ContractLibBean bean) {
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position % 2 == 0) {
                    lp.leftMargin = ResourceUtils.dip2px2(context, 12);
                    lp.rightMargin = ResourceUtils.dip2px2(context, 5.5f);
                } else {
                    lp.rightMargin = ResourceUtils.dip2px2(context, 12);
                    lp.leftMargin = ResourceUtils.dip2px2(context, 5.5f);
                }
                lp.topMargin = ResourceUtils.dip2px2(context, 14);
                mCommVH.loadImageResourceByGilde(R.id.logo_iv, bean.cover_img);
                mCommVH.setText(R.id.title_tv, bean.title);
                mCommVH.setText(R.id.desc_tv, bean.sub_title);
                mCommVH.setText(R.id.price_tv, bean.price + "元");

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContractLibDetailAct.openAct(context, bean);
                    }
                });
            }
        });
        mCommAdapter.setShowEmptyView(true, rcy);
        rcy.setAdapter(mCommAdapter);

        categoryInPopView = new InPopSelectIndexDictionaryView(getMContext(), "ContractLibraryType", new InPopSelectIndexDictionaryView.SelectIndexDictionaryInterface() {
            @Override
            public void onConfirm(IndexDictionaryUtils.ValueBean valueBean) {
                categoryInPopView.dismiss(true);
                presenter.req.filter.type = (valueBean == null ? null : valueBean.value);
                category_tv.setText(valueBean == null ? getString(R.string.contract_lib_list_tx2) : valueBean.label);
                presenter.getData(true, search_et.getText().toString().trim());
            }

            @Override
            public void isShow() {
                if (category_ll != null) {
                    category_ll.setSelected(true);
                }
            }

            @Override
            public void isDismiss() {
                if (category_ll != null) {
                    category_ll.setSelected(false);
                }
            }
        });

        category_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (businessInPopView != null && businessInPopView.isShowing()) {
                    businessInPopView.dismiss(false);
                }
                categoryInPopView.showPopView(view, true);
            }
        });

        businessInPopView = new InPopSelectIndexDictionaryView(getMContext(), "Industry", new InPopSelectIndexDictionaryView.SelectIndexDictionaryInterface() {
            @Override
            public void onConfirm(IndexDictionaryUtils.ValueBean valueBean) {
                businessInPopView.dismiss(true);
                presenter.req.filter.industry = (valueBean == null ? null : valueBean.value);
                business_tv.setText(valueBean == null ? getString(R.string.contract_lib_list_tx3) : valueBean.label);
                presenter.getData(true, search_et.getText().toString().trim());
            }

            @Override
            public void isShow() {
                if (business_ll != null) {
                    business_ll.setSelected(true);
                }
            }

            @Override
            public void isDismiss() {
                if (business_ll != null) {
                    business_ll.setSelected(false);
                }
            }
        });

        business_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryInPopView != null && categoryInPopView.isShowing()) {
                    categoryInPopView.dismiss(false);
                }
                businessInPopView.showPopView(view, true);
            }
        });

    }

    @Override
    public void doBusiness() {
        presenter.getData(true, search_et.getText().toString().trim());
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public ContractLibListPresenter initPresenter() {
        return new ContractLibListPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void loadDataResult(boolean isRefresh, List<ContractLibBean> contractLibBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (isRefresh) {
            if (contractLibBeans != null) {
                mCommAdapter.setData(contractLibBeans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (contractLibBeans != null) {
                mCommAdapter.addData(contractLibBeans);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (categoryInPopView != null) {
            categoryInPopView.isTouchClose((int) event.getX(), (int) event.getY());
        }
        if (businessInPopView != null) {
            businessInPopView.isTouchClose((int) event.getX(), (int) event.getY());
        }
        return super.onTouchEvent(event);
    }
}
