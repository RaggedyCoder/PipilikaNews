package com.pipilika.news.utils.fonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class FontCache {

    private static final String TAG = FontCache.class.getSimpleName();
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf != null) {
            return tf;
        } else {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
                put(name, tf);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            if (fontCache == null) {
                return null;
            } else {
                return fontCache.get(name);
            }
        }

    }

    private static void put(String name, Typeface data) {

        if (name == null || data == null) {
            return;
        }
        if (fontCache != null) {
            fontCache.put(name, data);
        }
    }
}