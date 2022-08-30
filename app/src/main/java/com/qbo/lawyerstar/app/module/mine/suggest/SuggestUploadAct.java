package com.qbo.lawyerstar.app.module.mine.suggest;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qbo.lawyerstar.R;
import com.qbo.lawyerstar.app.utils.IndexDictionaryUtils;

import java.util.List;

import butterknife.BindView;
import framework.mvp1.base.adapter.MCommAdapter;
import framework.mvp1.base.adapter.MCommVH;
import framework.mvp1.base.f.BaseModel;
import framework.mvp1.base.f.MvpAct;
import framework.mvp1.base.f.MvpFrag;
import framework.mvp1.base.util.GlideUtils;
import framework.mvp1.base.util.ResourceUtils;
import framework.mvp1.pics.GlideEngine;

public class SuggestUploadAct extends MvpAct<ISuggestUploadView, BaseModel, SuggestUploadPresenter> implements ISuggestUploadView {

    @BindView(R.id.category_rcv)
    RecyclerView category_rcv;
    private MCommAdapter categoryAdapter;

    @BindView(R.id.image_rcv)
    RecyclerView image_rcv;
    private MCommAdapter imageAdapter;

    @Override
    public SuggestUploadPresenter initPresenter() {
        return new SuggestUploadPresenter();
    }

    @Override
    public void baseInitialization() {
        setStatusBarComm(true);
    }

    @Override
    public int setR_Layout() {
        return R.layout.act_suggest_upload;
    }

    @Override
    public void viewInitialization() {
        setBackPress();
        setMTitle(R.string.suggest_upload_tx1);

        category_rcv.setLayoutManager(new GridLayoutManager(this, 3));
        categoryAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<IndexDictionaryUtils.ValueBean>() {
            @Override
            public int setLayout() {
                return R.layout.item_suggestupload_category_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, IndexDictionaryUtils.ValueBean valueBean) {
                GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) mCommVH.itemView.getLayoutParams();
                if (position % 3 == 0) {
                    lp.leftMargin = 0;
                    lp.rightMargin = ResourceUtils.dip2px2(context, 4);
                } else if (position % 3 == 1) {
                    lp.leftMargin = ResourceUtils.dip2px2(context, 4);
                    lp.rightMargin = ResourceUtils.dip2px2(context, 4);
                } else if (position % 3 == 2) {
                    lp.leftMargin = ResourceUtils.dip2px2(context, 4);
                    lp.rightMargin = 0;
                }
                mCommVH.setText(R.id.name_tv, valueBean.label);
                if (presenter.valueBeanMap.containsKey(valueBean.value)) {
                    mCommVH.itemView.setSelected(true);
                } else {
                    mCommVH.itemView.setSelected(false);
                }

                mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.valueBeanMap.clear();
                        presenter.valueBeanMap.put(valueBean.value, valueBean);
                        mCommVH.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        category_rcv.setAdapter(categoryAdapter);

        image_rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        imageAdapter = new MCommAdapter(this, new MCommVH.MCommVHInterface<LocalMedia>() {
            @Override
            public int setLayout() {
                return R.layout.item_selectimg_list;
            }

            @Override
            public void initViews(Context context, MCommVH mCommVH, View itemView) {

            }

            @Override
            public void bindData(Context context, MCommVH mCommVH, int position, LocalMedia path) {
                ImageView iv = (ImageView) mCommVH.getView(R.id.img_iv);
                View delv = mCommVH.getView(R.id.del_iv);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (path == null) {
                    iv.setImageResource(R.mipmap.ic_add_image_1);
                    delv.setVisibility(View.GONE);
                    mCommVH.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PictureSelector.create((SuggestUploadAct.this))
                                    .openGallery(PictureMimeType.ofImage())
                                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                                    .maxSelectNum(3)
                                    .selectionData(presenter.selectionData)
                                    .isCamera(true)// 是否显示拍照按钮
                                    .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }
                    });

                } else {
                    GlideUtils.loadImageDefult(context, path.getPath(), iv);
                    delv.setVisibility(View.VISIBLE);
                    mCommVH.itemView.setOnClickListener(null);
                    delv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.selectionData.remove(path);
                            mCommVH.adapter.setData(presenter.buildShowList());
                        }
                    });

                }
            }
        });
        image_rcv.setAdapter(imageAdapter);
        imageAdapter.addOneData(null);
    }

    @Override
    public void doBusiness() {
        IndexDictionaryUtils.getIndexDictionary(this, "SuggestionsCategoryLabels", new IndexDictionaryUtils.GetDictionaryResult() {
            @Override
            public void reslut(List<IndexDictionaryUtils.ValueBean> beanList) {
                if (isDestroyed()) {
                    return;
                }
                try {
                    categoryAdapter.setData(beanList);
                }catch (Exception e){
                }
            }
        });
    }

    @Override
    public void doWakeUpBusiness() {

    }

    @Override
    public void doReleaseSomething() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                presenter.selectionData = selectList;
                imageAdapter.setData(presenter.buildShowList());
            }
        }
    }

    @Override
    public Context getMContext() {
        return this;
    }
}
