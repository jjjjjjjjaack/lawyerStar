package com.qbo.lawyerstar.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qbo.lawyerstar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: FAZIntermediary
 * @Package: com.fanganzhi.agency.views.my
 * @ClassName: WarpLinearLayout
 * @Description: java类作用描述
 * @Author: liys
 * @CreateDate: 2021/5/11 16:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/5/11 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WarpLinearLayout extends ViewGroup {

    private Type mType;
    private List<WarpLine> mWarpLineGroup;


    public WarpLinearLayout(Context context) {
        this(context, null);
    }

    public WarpLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.WarpLinearLayoutDefault);
    }

    public WarpLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mType = new Type(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int with = 0;
        int height = 0;
        int childCount = getChildCount();
        /**
         * 在调用childView。getMeasre之前必须先调用该行代码，用于对子View大小的测量
         */
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 计算宽度
         */
        switch (withMode) {
            case MeasureSpec.EXACTLY:
                with = withSize;
                break;
            case MeasureSpec.AT_MOST:
                for (int i = 0; i < childCount; i++) {
                    if (i != 0) {
                        with += mType.horizontal_Space;
                    }
                    with += getChildAt(i).getMeasuredWidth();
                }
                with += getPaddingLeft() + getPaddingRight();
                with = with > withSize ? withSize : with;
                break;
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < childCount; i++) {
                    if (i != 0) {
                        with += mType.horizontal_Space;
                    }
                    with += getChildAt(i).getMeasuredWidth();
                }
                with += getPaddingLeft() + getPaddingRight();
                break;
            default:
                with = withSize;
                break;

        }
        /**
         * 根据计算出的宽度，计算出所需要的行数
         */
        WarpLine warpLine = new WarpLine();
        /**
         * 不能够在定义属性时初始化，因为onMeasure方法会多次调用
         */
        mWarpLineGroup = new ArrayList<WarpLine>();
        for (int i = 0; i < childCount; i++) {
            if (warpLine.lineWidth + getChildAt(i).getMeasuredWidth() + mType.horizontal_Space > with) {
                if (warpLine.lineView.size() == 0) {
                    warpLine.addView(getChildAt(i));
                    mWarpLineGroup.add(warpLine);
                    warpLine = new WarpLine();
                } else {
                    mWarpLineGroup.add(warpLine);
                    warpLine = new WarpLine();
                    warpLine.addView(getChildAt(i));
                }
            } else {
                warpLine.addView(getChildAt(i));
            }
        }
        /**
         * 添加最后一行
         */
        if (warpLine.lineView.size() > 0 && !mWarpLineGroup.contains(warpLine)) {
            mWarpLineGroup.add(warpLine);
        }
        /**
         * 计算宽度
         */
        height = getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mWarpLineGroup.size(); i++) {
            if (i != 0) {
                height += mType.vertical_Space;
            }
            height += mWarpLineGroup.get(i).height;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = height > heightSize ? heightSize : height;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        setMeasuredDimension(with, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("WarpLinearLayout", l + " " + t + " " + r + " " + b);
        t = getPaddingTop();
        for (int i = 0; i < mWarpLineGroup.size(); i++) {
            int left = getPaddingLeft();
            WarpLine warpLine = mWarpLineGroup.get(i);
            int lastWidth = getMeasuredWidth() - warpLine.lineWidth;
            for (int j = 0; j < warpLine.lineView.size(); j++) {
                View view = warpLine.lineView.get(j);
                if (isFull()) {//需要充满当前行时
                    view.layout(left, t, left + view.getMeasuredWidth() + lastWidth / warpLine.lineView.size(), t + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mType.horizontal_Space + lastWidth / warpLine.lineView.size();
                } else {
                    switch (getGrivate()) {
                        case 0://右对齐
                            view.layout(left + lastWidth, t, left + lastWidth + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                        case 2://居中对齐
                            view.layout(left + lastWidth / 2, t, left + lastWidth / 2 + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                        default://左对齐
                            view.layout(left, t, left + view.getMeasuredWidth(), t + view.getMeasuredHeight());
                            break;
                    }
                    left += view.getMeasuredWidth() + mType.horizontal_Space;
                }
            }
            t += warpLine.height + mType.vertical_Space;
        }
    }

    /**
     * 用于存放一行子View
     */
    private final class WarpLine {
        private List<View> lineView = new ArrayList<View>();
        /**
         * 当前行中所需要占用的宽度
         */
        private int lineWidth = getPaddingLeft() + getPaddingRight();
        /**
         * 该行View中所需要占用的最大高度
         */
        private int height = 0;

        private void addView(View view) {
            if (lineView.size() != 0) {
                lineWidth += mType.horizontal_Space;
            }
            height = height > view.getMeasuredHeight() ? height : view.getMeasuredHeight();
            lineWidth += view.getMeasuredWidth();
            lineView.add(view);
        }
    }

    private boolean isDraging;
    private Handler mHandler = new Handler();
    private long PRESSREANGE = 500;
    private View dragView;
    private int dragPos = 0;
    private int startX, startY;
    private int startLeft, startRight, startTop, startBottom;
    private long startTime;
    private long CLICKRANGE = 10;


    @SuppressLint("WrongCall")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dragView = null;
                startX = (int) event.getX();
                startY = (int) event.getY();
                startTime = event.getDownTime();
                View v = hasView(startX, startY);
                if (v != null) {
                    /* 长按操作 */
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isDraging = true;
                            dragView = v;
                            dragPos = ((ViewGroup) v.getParent()).indexOfChild(v);
                            startLeft = dragView.getLeft();
                            startTop = dragView.getTop();
                        }
                    }, PRESSREANGE);
                }
                Log.i("WarpLinearLayout", "down" + startX + " " + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                int lastX = (int) event.getX();
                int lastY = (int) event.getY();
                int tx = lastX - startX;
                int ty = lastY - startY;
                Log.i("WarpLinearLayout", "move" + lastX + " " + lastY);

                //移动
//                moveView(lastX, lastY);

                if (Math.abs(lastX - startX) > CLICKRANGE || Math.abs(lastY - startY) > CLICKRANGE) {
                    this.mHandler.removeCallbacksAndMessages(null);
                }
                if (isDraging && dragView != null) {
                    Log.i("WarpLinearLayout", "dragView" + dragView.getX() + " " + dragView.getY());
//                    dragView.setTranslationX(lastX-startX);
//                    dragView.setTranslationY(lastY-startY);
                    dragView.layout(startLeft + tx,
                            startTop + ty,
                            startLeft + tx + dragView.getWidth(),
                            startTop + ty + dragView.getHeight());

                    int pos = chechViewPos(lastX, lastY);
                    if (pos != -1) {
                        Log.i("WarpLinearLayoutPos", pos + "");
                        if (dragPos != pos) {
                            removeView(dragView);
                            addView(dragView, pos);
                            dragPos = pos;
                        }
                    }
                }
//                Log.i("WarpLinearLayout", "move" + lastX + " " + lastY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.i("WarpLinearLayout", "up");
                this.mHandler.removeCallbacksAndMessages(null);
                layout(getLeft(), getTop(), getRight(), getBottom() + 1);
//                requestLayout();
                isDraging = false;
                dragView = null;
                break;
        }
        return true;
    }

    private View hasView(int startX, int startY) {
        for (int i = 0; i < mWarpLineGroup.size(); i++) {
            WarpLine warpLine = mWarpLineGroup.get(i);
            for (int j = 0; j < warpLine.lineView.size(); j++) {
                View view = warpLine.lineView.get(j);
                if (view.getX() < startX && startX < (view.getX() + view.getWidth())) {
                    if (view.getY() < startY && startY < (view.getY() + view.getHeight())) {
                        return view;
                    }
                }
            }
        }
        return null;
    }


    private int chechViewPos(int touchx, int touchy) {
        if (dragView == null) {
            return -1;
        }
        int frontIndex = -1;
        int nextIndex = 0;
        for (int i = 0; i < mWarpLineGroup.size(); i++) {
            WarpLine warpLine = mWarpLineGroup.get(i);
            for (int j = 0; j < warpLine.lineView.size(); j++) {
                View view = warpLine.lineView.get(j);
                if (view.getTop() < touchy && view.getBottom() > touchy) {
                    if (view.getRight() < touchx) {
                        frontIndex = ((ViewGroup) view.getParent()).indexOfChild(view);
                        if (i == mWarpLineGroup.size() - 1 && j == warpLine.lineView.size() - 1) {
                            return frontIndex;
                        }
                    } else {
                        return frontIndex;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 对样式的初始化
     */
    private final static class Type {
        /*
         *对齐方式 right 0，left 1，center 2
         */
        private int grivate;
        /**
         * 水平间距,单位px
         */
        private float horizontal_Space;
        /**
         * 垂直间距,单位px
         */
        private float vertical_Space;
        /**
         * 是否自动填满
         */
        private boolean isFull;

        Type(Context context, AttributeSet attrs) {
            if (attrs == null) {
                return;
            }
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WarpLinearLayout);
            grivate = typedArray.getInt(R.styleable.WarpLinearLayout_grivate, grivate);
            horizontal_Space = typedArray.getDimension(R.styleable.WarpLinearLayout_horizontal_Space, horizontal_Space);
            vertical_Space = typedArray.getDimension(R.styleable.WarpLinearLayout_vertical_Space, vertical_Space);
            isFull = typedArray.getBoolean(R.styleable.WarpLinearLayout_isFull, isFull);
        }
    }

    public int getGrivate() {
        return mType.grivate;
    }

    public float getHorizontal_Space() {
        return mType.horizontal_Space;
    }

    public float getVertical_Space() {
        return mType.vertical_Space;
    }

    public boolean isFull() {
        return mType.isFull;
    }

    public void setGrivate(int grivate) {
        mType.grivate = grivate;
    }

    public void setHorizontal_Space(float horizontal_Space) {
        mType.horizontal_Space = horizontal_Space;
    }

    public void setVertical_Space(float vertical_Space) {
        mType.vertical_Space = vertical_Space;
    }

    public void setIsFull(boolean isFull) {
        mType.isFull = isFull;
    }

    /**
     * 每行子View的对齐方式
     */
    public final static class Gravite {
        public final static int RIGHT = 0;
        public final static int LEFT = 1;
        public final static int CENTER = 2;
    }
}