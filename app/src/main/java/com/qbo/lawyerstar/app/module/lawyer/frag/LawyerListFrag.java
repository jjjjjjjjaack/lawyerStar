package com.qbo.lawyerstar.app.module.lawyer.frag;

import android.content.Context;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.bean.FCityBean;
import com.qbo.lawyerstar.app.module.inpopview.InPopSelectCityView;
import com.qbo.lawyerstar.app.module.inpopview.InPopSelectEmploymentYearView;
import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;
import com.qbo.lawyerstar.app.module.lawyer.detail.LawyerDetailAct;
import com.qbo.lawyerstar.app.module.lawyer.list.ILawyerListView;
import com.qbo.lawyerstar.app.module.lawyer.list.LawyerListPresenter;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;
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
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.StatusBarUtils;
import framework.mvp1.base.util.ToolUtils;

public class LawyerListFrag extends MvpFrag<ILawyerListView, BaseModel, LawyerListPresenter> implements ILawyerListView {
    @BindView(R.id.statusiv)
    View statusiv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    MCommAdapter mCommAdapter;

    @BindView(R.id.tv_back_right)
    ImageView tv_back_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.city_tv)
    TextView city_tv;
    @BindView(R.id.years_tv)
    TextView years_tv;
    @BindView(R.id.city_ll)
    View city_ll;
    @BindView(R.id.years_ll)
    View years_ll;

    @BindView(R.id.rl_back)
    View rl_back;
    @BindView(R.id.rl_back_tv)
    TextView rl_back_tv;
    @BindView(R.id.option_ll)
    View option_ll;
    @BindView(R.id.input_ll)
    View input_ll;
    @BindView(R.id.search_et)
    EditText search_et;

    private int modeType = 0;//0普通 1搜索


    private InPopSelectCityView inPopSelectCityView;
    private InPopSelectEmploymentYearView inPopSelectEmploymentYearView;



    @Override
    public LawyerListPresenter initPresenter() {
        return new LawyerListPresenter();
    }

    @Override
    public void baseInitialization() {

    }

    @Override
    public int setR_Layout() {
        return R.layout.frag_lawyer_list;
    }

    @Override
    public void viewInitialization() {
//        statusiv.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(getMContext());
//        setBackPress();
        tv_back_right.setImageResource(R.mipmap.ic_top_seatch_white);
        rl_back_tv.setText("");
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
        rcy.setLayoutManager(new LinearLayoutManager(getMContext()));
        mCommAdapter = new MCommAdapter(getMContext(), new MCommVH.MCommVHInterface<LawyerBean>() {
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
                for (String str : bean.expertise) {
                    TextView tv = new TextView(context);
                    tv.setBackgroundResource(R.drawable.shape_storke_cccccc_r2);
                    tv.setTextColor(0xff666666);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    tv.setPadding(ResourceUtils.dip2px2(context, 6)
                            , ResourceUtils.dip2px2(context, 3)
                            , ResourceUtils.dip2px2(context, 6)
                            , ResourceUtils.dip2px2(context, 3));
                    tv.setText(str);
                    tags_wll.addView(tv);
                }
                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LawyerDetailAct.openAct(getMContext(), bean.getId());
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
//                    finish();
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
        inPopSelectCityView = new InPopSelectCityView(getMContext(), new InPopSelectCityView.SelectCityInterface() {
            @Override
            public void onConfirm(FCityBean provinceBean, FCityBean cityBean, FCityBean regionBean) {
                inPopSelectCityView.dismiss(true);
                presenter.req.filter.address_info = new ArrayList<>();
                if (provinceBean == null) {
                    city_tv.setText(getString(R.string.lawyer_list_tx2));
                } else {
                    presenter.req.filter.address_info = new ArrayList<>();
                    presenter.req.filter.address_info.add(0, ToolUtils.String2Int(provinceBean.getId()));
                    city_tv.setText(provinceBean.getLabel());
                    if (cityBean != null) {
                        presenter.req.filter.address_info.add(1, ToolUtils.String2Int(cityBean.getId()));
                        city_tv.setText(provinceBean.getLabel() + " " + cityBean.getLabel());
                    }
                }
                presenter.getData(true);
            }

            @Override
            public void isShow() {
                if (city_ll != null) {
                    city_ll.setSelected(true);
                }
            }

            @Override
            public void isDismiss() {
                if (city_ll != null) {
                    city_ll.setSelected(false);
                }
            }
        });

        city_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inPopSelectEmploymentYearView != null && inPopSelectEmploymentYearView.isShowing()) {
                    inPopSelectEmploymentYearView.dismiss(false);
                }
                inPopSelectCityView.showPopView(view, true);
            }
        });

        inPopSelectEmploymentYearView = new InPopSelectEmploymentYearView(getMContext(), new InPopSelectEmploymentYearView.SelectEmploymentYearInterface() {
            @Override
            public void onConfirm(IndexDictionaryUtils.ValueBean valueBean) {
                inPopSelectEmploymentYearView.dismiss(true);
                presenter.req.filter.employment_year = (valueBean == null ? null : valueBean.value);
                years_tv.setText(valueBean == null ? getString(R.string.lawyer_list_tx3) : valueBean.label);
                presenter.getData(true);
            }

            @Override
            public void isShow() {
                if (years_ll != null) {
                    years_ll.setSelected(true);
                }
            }

            @Override
            public void isDismiss() {
                if (years_ll != null) {
                    years_ll.setSelected(false);
                }
            }
        });
        years_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inPopSelectCityView != null && inPopSelectCityView.isShowing()) {
                    inPopSelectCityView.dismiss(false);
                }
                inPopSelectEmploymentYearView.showPopView(v, true);
            }
        });
    }

    private void changeModeType() {
        if (modeType == 0) {
            modeType = 1;
            input_ll.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
            tv_back_right.setVisibility(View.GONE);
            rl_back_tv.setText("取消");
            option_ll.setVisibility(View.GONE);
            search_et.setText("");
            presenter.req.search = "";
            presenter.req.filter.address_info = new ArrayList<>();
            presenter.req.filter.employment_year = "";
            city_tv.setText(getString(R.string.lawyer_list_tx2));
            years_tv.setText(getString(R.string.lawyer_list_tx3));
            search_et.requestFocus();
            presenter.getData(true);
        } else {
            modeType = 0;
            search_et.setText("");
            rl_back_tv.setText("");
            presenter.req.search = "";
            input_ll.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
            option_ll.setVisibility(View.VISIBLE);
            tv_back_right.setVisibility(View.VISIBLE);
            presenter.getData(true);
        }
    }

    @Override
    public void doBusiness() {
        presenter.getData(true);
    }

    @Override
    public void onWakeBussiness() {

    }


    @Override
    public Context getMContext() {
        return getContext();
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (inPopSelectCityView != null) {
//            inPopSelectCityView.isTouchClose((int) event.getX(), (int) event.getY());
//        }
//        if (inPopSelectEmploymentYearView != null) {
//            inPopSelectEmploymentYearView.isTouchClose((int) event.getX(), (int) event.getY());
//        }
//        return super.onTouchEvent(event);
//    }
}
