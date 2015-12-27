package com.pipilika.news.nightmode.handler;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.asha.nightowllib.handler.impls.AbsSkinHandler;
import com.asha.nightowllib.paint.ColorBox;
import com.asha.nightowllib.paint.IOwlPaint;
import com.pipilika.news.R;
import com.pipilika.news.nightmode.switcher.NightModeTable;

/**
 * Created by sajid on 12/26/2015.
 */
public class CardViewHandler extends AbsSkinHandler implements NightModeTable.NightModeCardView {
    private static final String TAG = CardViewHandler.class.getSimpleName();

    @Override
    protected void onAfterCollect(View view, Context context, AttributeSet attrs, ColorBox box) {
        Object[] objects = box.get(R.styleable.nightModeCardView_nightCardBackgroundColor
                , NightModeTable.CardViewScope);
        if (objects != null) {
            // obtain original color
            TypedArray a = context.obtainStyledAttributes(attrs, android.support.v7.cardview.R.styleable.CardView, 0,
                    android.support.v7.cardview.R.style.CardView_Light);
            if (a != null) {
                int backgroundColor = a.getColor(android.support.v7.cardview.R.styleable.CardView_cardBackgroundColor, 0);
                objects[0] = backgroundColor;
                a.recycle();
            }
        }
    }

    public static class BackgroundPaint implements IOwlPaint {

        public BackgroundPaint() {
            Log.d(TAG, "BackgroundPaint");
        }

        @Override
        public void draw(@NonNull View view, @NonNull Object value) {
            CardView cardView = (CardView) view;
            Log.d(TAG, value.toString());
            cardView.setCardBackgroundColor((Integer) value);
        }

        @Override
        public Object[] setup(@NonNull View view, @NonNull TypedArray a, int attr) {
            Log.d(TAG, "setup");
            int color1 = 0;
            int color2 = a.getColor(attr, 0);
            return new Integer[]{color1, color2};
        }
    }
}
