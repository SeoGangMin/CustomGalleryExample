package com.gallery.example.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by seogangmin on 2016. 9. 25..
 */

public class SqaureRelativeLayout extends RelativeLayout {
    public SqaureRelativeLayout(Context context) {
        super(context);
    }

    public SqaureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqaureRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int u, int r, int d) {
        getChildAt(0).layout(0, 0, r - l, d - u); // Layout with max size
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        View child = getChildAt(0);
        child.measure(widthMeasureSpec, widthMeasureSpec);
        int width = resolveSize(child.getMeasuredWidth(), widthMeasureSpec);
        child.measure(width, width); // 2nd pass with the correct size
        setMeasuredDimension(width, width);
    }
}
