package com.pipilika.news.util.font;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pipilika.news.R;

public class CustomFontHelper {

    public CustomFontHelper() {

    }

    public static void setCustomFont(TextView textview, Context context,
                                     AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String font = a.getString(R.styleable.CustomFont_font);
        setCustomFont(textview, font, context);
        a.recycle();
    }

    public static void setCustomFont(TextView textview, String font,
                                     Context context) {
        if (font == null) {
            return;
        }
        Typeface tf = FontCache.get(font, context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }
}
