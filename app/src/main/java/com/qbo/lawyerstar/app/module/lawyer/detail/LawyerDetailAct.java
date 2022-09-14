package com.qbo.lawyerstar.app.module.lawyer.detail;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.InspectableProperty;

import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.module.lawyer.bean.LawyerBean;
import com.qbo.lawyerstar.app.module.lawyer.list.ILawyerListView;
import com.qbo.lawyerstar.app.view.ChangeGasStationImageView2;
import com.qbo.lawyerstar.app.view.WarpLinearLayout;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.base.util.ToolUtils;

public class LawyerDetailAct extends MvpAct<ILawyerDetailView, BaseModel, LawyerDetailPresenter>
        implements ILawyerDetailView {

    public static void openAct(Context context, String lawyerid) {
        Intent intent = new Intent(context, LawyerDetailAct.class);
        intent.putExtra("lawyerid", lawyerid);
        context.startActivity(intent);
    }

    @BindView(R.id.userlogo_civ)
    ChangeGasStationImageView2 userlogo_civ;

    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.years_tv)
    TextView years_tv;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.person_tv)
    TextView person_tv;
    @BindView(R.id.anjian_tv)
    TextView anjian_tv;
    @BindView(R.id.personal_rl)
    View personal_rl;
    @BindView(R.id.personl_detail_ll)
    View personl_detail_ll;
    @BindView(R.id.anjian_rl)
    View anjian_rl;
    @BindView(R.id.anjian_detail_ll)
    View anjian_detail_ll;
    @BindView(R.id.tags_wll)
    WarpLinearLayout tags_wll;

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_lawyer_detail;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.lawyer_list_tx7);
        personal_rl.setSelected(true);
        anjian_rl.setSelected(false);

        personal_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personal_rl.setSelected(true);
                anjian_rl.setSelected(false);
                personl_detail_ll.setVisibility(View.VISIBLE);
                anjian_detail_ll.setVisibility(View.GONE);
            }
        });
        anjian_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personal_rl.setSelected(false);
                anjian_rl.setSelected(true);
                personl_detail_ll.setVisibility(View.GONE);
                anjian_detail_ll.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void doBusiness() {
        String lawyerid = getIntent().getStringExtra("lawyerid");
        if (ToolUtils.isNull(lawyerid)) {
            finish();
            return;
        }
        presenter.getDetail(lawyerid);
    }

    @Override
    public void showInfo(boolean flag) {
        if (flag && presenter.lawyerBean != null) {
            GlideUtils.loadImageLawyerLogoDefult(getMContext(), presenter.lawyerBean.avatar, userlogo_civ);
            name_tv.setText(presenter.lawyerBean.real_name);
            years_tv.setText(getString(R.string.home_frag_tx3_3, presenter.lawyerBean.employment_year));
            address_tv.setText(presenter.lawyerBean.address_info_text);
            person_tv.setText(Html.fromHtml(presenter.lawyerBean.intro));
            anjian_tv.setText(Html.fromHtml(presenter.lawyerBean.case_intro));

            for (String str : presenter.lawyerBean.expertise) {
                TextView tv = new TextView(this);
                tv.setBackgroundResource(R.drawable.shape_storke_cccccc_r2);
                tv.setTextColor(0xff666666);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                tv.setPadding(ResourceUtils.dip2px2(this, 6)
                        , ResourceUtils.dip2px2(this, 3)
                        , ResourceUtils.dip2px2(this, 6)
                        , ResourceUtils.dip2px2(this, 3));
                tv.setText(str);
                tags_wll.addView(tv);
            }
        }
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    public LawyerDetailPresenter initPresenter() {
        return new LawyerDetailPresenter();
    }

    @Override
    public Context getMContext() {
        return this;
    }
}
