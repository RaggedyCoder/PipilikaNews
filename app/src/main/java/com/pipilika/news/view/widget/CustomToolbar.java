package com.pipilika.news.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.asha.nightowllib.observer.IOwlObserver;
import com.pipilika.news.R;

/**
 * Created by sajid on 12/27/2015.
 */
public class CustomToolbar extends Toolbar implements IOwlObserver {
    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onSkinChange(int mode, Activity activity) {
        @ColorRes
        int color = mode == 0 ? R.color.colorPrimary : R.color.nightColorPrimary;
        setBackgroundColor(getResources().getColor(color));
    }
}
