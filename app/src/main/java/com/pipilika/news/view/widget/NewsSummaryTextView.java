package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tuman on 29/4/2015.
 */
public class NewsSummaryTextView extends TextView {
    public NewsSummaryTextView(Context context) {
        super(context);
    }

    public NewsSummaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsSummaryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewsSummaryTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
