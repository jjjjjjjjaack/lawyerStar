package framework.mvp1.views.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.qbo.lawyerstar.R;

public class BorderLinearLayout extends LinearLayout {


    private static final int STROKE_WIDTH = 2;

    private int borderCol;

    private Paint borderPaint;


    public BorderLinearLayout(Context context) {
        super(context);
        initAttributeSet(context, null, 0,0);
        initView();
    }

    public BorderLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs, 0,0);
        initView();
    }

    public BorderLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs, defStyleAttr,0);
        initView();
    }


    public BorderLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributeSet(context, attrs, defStyleAttr,defStyleRes);
        initView();
    }

    public void initAttributeSet(Context context, AttributeSet attrs, int defStyleAttr,int defStyleRes) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.BorderLinearLayout, defStyleAttr, defStyleRes);
        try {
            borderCol = a.getInteger(R.styleable.BorderLinearLayout_borderColor, 0);//0 is default
        } finally {
            a.recycle();
        }
    }

    public void initView() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(STROKE_WIDTH);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        borderPaint.setColor(borderCol);
        int w = this.getMeasuredWidth();
        int h = this.getMeasuredHeight();
        RectF r = new RectF(2, 2, w - 2, h - 2);
        canvas.drawRoundRect(r, 5, 5, borderPaint);
    }

    public int getBordderColor() {
        return borderCol;
    }

    public void setBorderColor(int newColor) {
        borderCol = newColor;
        invalidate();
        requestLayout();
    }

}
