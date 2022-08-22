package com.qbo.lawyerstar.app.view.scrolltextview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.haohaohu.autoscrolltextview.MarqueeTextView;
import com.qbo.lawyerstar.R;

public class MAutoScrollTextView extends MBaseScrollTextView {

    public MAutoScrollTextView(Context context) {
        super(context);
    }

    public MAutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public MarqueeTextView makeTextView() {
        MarqueeTextView textView = new MarqueeTextView(getContext());
        textView.setTextColor(getResources().getColor(R.color.c_333333));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        return textView;
    }
}
