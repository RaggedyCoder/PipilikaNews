package com.pipilika.news.appdata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tuman on 4/5/2015.
 */
public class AppManager {

    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;
    private static final String PREF_NAME = "PipilikaNewsPref";
    private static final String KEY_FIRST_TIMER = "timer";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private Activity activity;

    public AppManager(Activity activity) {
        this.activity = activity;
        mSharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mSharedPreferences.edit();
        editor.apply();
    }

    private SharedPreferences getSharedPreferences(final String prefName, final int mode) {
        return this.activity.getSharedPreferences(prefName, mode);
    }


    public boolean isFirstTime() {
        return mSharedPreferences.getBoolean(KEY_FIRST_TIMER, true);
    }

    public void setFirstTime() {
        editor.putBoolean(KEY_FIRST_TIMER, false);
        editor.commit();
    }


}