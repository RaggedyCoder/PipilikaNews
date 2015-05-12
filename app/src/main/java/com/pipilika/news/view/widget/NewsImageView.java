package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by tuman on 12/5/2015.
 */
public class NewsImageView extends ImageView {

    private String mUrl;

    public NewsImageView(Context context) {
        super(context);
    }

    public NewsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewsImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
