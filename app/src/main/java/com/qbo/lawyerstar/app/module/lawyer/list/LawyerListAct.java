package com.qbo.lawyerstar.app.module.lawyer.list;

import android.content.Context;
import android.text.Editable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;
import com.qbo.lawyerstar.app.module.lawyer.detail.LawyerDetailAct;
import com.qbo.lawyerstar.app.view.WarpLinearLayout;
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
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;

public class LawyerListAct extends MvpAct<ILawyerListView, BaseModel, LawyerListPresenter> implements ILawyerListView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    MCommAdapter mCommAdapter;

    @BindView(R.id.tv_back_right)
    ImageView tv_back_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.city_ll)
    View city_ll;
    @BindView(R.id.years_ll)
    View years_ll;

    @BindView(R.id.rl_back)
    View rl_back;
    @BindView(R.id.input_ll)
    View input_ll;
    @BindView(R.id.search_et)
    EditText search_et;

    private int modeType = 0;//0普通 1搜索

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_lawyer_list;
    }

    @Override
    public void viewInitialization() {
//        setBackPress();
        setMTitle(R.string.lawyer_list_tx1);
        tv_back_right.setImageResource(R.mipmap.ic_search_1);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getData(false);
            }
        });
        rcy.setLayoutManager(new LinearLayoutManager(this));
        mCommAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<LawyerBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_lawyer_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, LawyerBean bean) {
                GlideUtils.loadImageLawyerLogoDefult(context, bean.avatar, (ImageView) mCommVH.getView(R.id.userlogo_civ));
                mCommVH.setText(R.id.name_tv, bean.real_name);
                mCommVH.setText(R.id.years_tv, getString(R.string.home_frag_tx3_3, bean.employment_year));
                mCommVH.setText(R.id.address, bean.address_info_text);

                WarpLinearLayout tags_wll = (WarpLinearLayout) mCommVH.getView(R.id.tags_wll);
                tags_wll.removeAllViews();
                for (String str:bean.expertise){
                    TextView tv = new TextView(context);
                    tv.setBackgroundResource(R.drawable.shape_storke_cccccc_r2);
                    tv.setTextColor(0xff666666);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    tv.setPadding(ResourceUtils.dip2px2(context,6)
                            ,ResourceUtils.dip2px2(context,3)
                            ,ResourceUtils.dip2px2(context,6)
                            ,ResourceUtils.dip2px2(context,3));
                    tv.setText(str);
                    tags_wll.addView(tv);
                }
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LawyerDetailAct.openAct(getMContext(), bean);
                    }
                });
            }
        });
        mCommAdapter.setShowEmptyView(true);
        rcy.setAdapter(mCommAdapter);

        tv_back_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeModeType();
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modeType == 1) {
                    changeModeType();
                } else {
                    finish();
                }
            }
        });

        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.req.search = search_et.getText().toString();
                    presenter.getData(true);
                    return true;
                }
                return false;
            }
        });
    }

    private void changeModeType() {
        if (modeType == 0) {
            modeType = 1;
            input_ll.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
            tv_back_right.setVisibility(View.GONE);
            search_et.setText("");
            presenter.req.search = "";
            search_et.requestFocus();
        } else {
            modeType = 0;
            search_et.setText("");
            presenter.req.search = "";
            input_ll.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            tv_back_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void doBusiness() {
        presenter.getData(true);
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public LawyerListPresenter initPresenter() {
        return new LawyerListPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void loadDataResult(boolean isRefresh, List<LawyerBean> lawyerBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if (isRefresh) {
            if (lawyerBeans != null) {
                mCommAdapter.setData(lawyerBeans);
            } else {
                mCommAdapter.setData(new ArrayList());
            }
        } else {
            if (lawyerBeans != null) {
                mCommAdapter.addData(lawyerBeans);
            }
        }
    }
}
