package com.pipilika.news.appdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.DateUtils;
import android.util.Log;

import com.pipilika.news.utils.BanglaTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppManager {

    private static final String TAG = AppManager.class.getSimpleName();
    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;
    private static final String PREF_NAME = "PipilikaNewsPref";
    private static final String KEY_LATEST_NEWS = "latest";
    private static final String KEY_FIRST_TIME_OPEN = "firstTime";
    private static final String KEY_APP_THEME_MODE = "themeMode";
    private SharedPreferences mSharedPreferences;
    private Editor editor;
    private Context context;

    public AppManager(Context context) {
        this.context = context;
        mSharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mSharedPreferences.edit();
        editor.apply();
    }

    private SharedPreferences getSharedPreferences(final String prefName, final int mode) {
        return this.context.getSharedPreferences(prefName, mode);
    }

    public String getLatestNewsId() {
        return mSharedPreferences.getString(KEY_LATEST_NEWS, "0");
    }

    public void setLatestNewsId(String id) {
        editor.putString(KEY_LATEST_NEWS, id);
        editor.commit();
    }

    public boolean getFirstTimer() {
        return mSharedPreferences.getBoolean(KEY_FIRST_TIME_OPEN, true);
    }

    public void setFirstTimer(boolean id) {
        editor.putBoolean(KEY_FIRST_TIME_OPEN, id);
        editor.commit();
    }

    public int getAppThemeMode() {
        return mSharedPreferences.getInt(KEY_APP_THEME_MODE, 0);
    }

    public void setAppThemeMode(int themeMode) {
        editor.putInt(KEY_APP_THEME_MODE, themeMode);
        editor.commit();
    }

    public String getReadableDate(String published_time) {
        SimpleDateFormat nonReadableFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = nonReadableFormat.parse(published_time);
        } catch (ParseException e) {
            nonReadableFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);
            try {
                date = nonReadableFormat.parse(published_time);
            } catch (ParseException e1) {
                Log.e(TAG, e.getMessage());
            }
        }
        CharSequence readableFormat = DateUtils.getRelativeTimeSpanString(
                date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return BanglaTime.getBanglaTime(readableFormat).toString();
    }

    public Editor getEditor() {
        return editor;
    }
}