package com.qbo.lawyerstar.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.qbo.lawyerstar.R;

import framework.mvp1.base.util.ResourceUtils;


public class ChangeGasStationImageView2 extends androidx.appcompat.widget.AppCompatImageView {

    public ChangeGasStationImageView2(Context context) {
        this(context, null);

    }

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids;
    private float ltop;
    private float lbottom;
    private float rtop;
    private float rbottom;

    public ChangeGasStationImageView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ChangeGasStationImageView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChangeGasStationImageView, defStyleAttr, 0);
        float rid = ResourceUtils.dip2px2(getContext(), 6f);
        ltop = a.getDimension(R.styleable.ChangeGasStationImageView_ltop, rid);
        lbottom = a.getDimension(R.styleable.ChangeGasStationImageView_lbottom, rid);
        rtop = a.getDimension(R.styleable.ChangeGasStationImageView_rtop, rid);
        rbottom = a.getDimension(R.styleable.ChangeGasStationImageView_rbottom, rid);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 设置View宽高的测量值
//        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
//                getDefaultSize(0, heightMeasureSpec));
//        // 只有setMeasuredDimension调用之后，才能使用getMeasuredWidth()和getMeasuredHeight()来获取视图测量出的宽高，以此之前调用这两个方法得到的值都会是0
//        int childWidthSize = getMeasuredWidth();
//        // 高度和宽度一样
//        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
//                childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float rid = ResourceUtils.dip2px2(getContext(), 6f);
        //创建圆角数组
//        rids = new float[]{rid, rid, rid, rid, 0.0f, 0.0f, 0.0f, 0.0f};
        rids = new float[]{ltop, ltop, rtop, rtop, rbottom, rbottom, lbottom, lbottom};
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
