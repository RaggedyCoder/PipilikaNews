package com.pipilika.news.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tuman on 5/5/2015.
 */
public class CustomViewPager extends ViewPager {
    private int max = 0;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(10, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > max) {
                max = h;
            }
            Log.e("TAG" + getChildCount(), "height" + child.getHeight());
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
