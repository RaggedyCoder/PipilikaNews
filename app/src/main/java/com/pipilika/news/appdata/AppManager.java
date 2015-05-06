package com.pipilika.news.appdata;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppManager {

    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;
    private static final String PREF_NAME = "PipilikaNewsPref";
    private static final String KEY_LATEST_NEWS = "latest";
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

    public String getLatestNewsId() {
        return mSharedPreferences.getString(KEY_LATEST_NEWS, "0");
    }

    public void setLatestNewsId(String id) {
        editor.putString(KEY_LATEST_NEWS, id);
        editor.commit();
    }


}