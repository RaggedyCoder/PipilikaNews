package com.pipilika.news.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.asha.nightowllib.observer.IOwlObserver;
import com.pipilika.news.R;

/**
 * Created by sajid on 12/26/2015.
 */
public class CustomCardView extends CardView implements IOwlObserver {

    public CustomCardView(Context context) {
        super(context);
    }


    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onSkinChange(int mode, Activity activity) {
        @ColorRes
        int color = mode == 0 ? R.color.card_light_background : R.color.card_dark_background;
        setCardBackgroundColor(getResources().getColor(color));
    }
}
