package com.pipilika.news.application;

import android.app.Activity;
import android.app.Application;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.asha.nightowllib.NightOwl;
import com.asha.nightowllib.observer.IOwlObserver;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.nightmode.handler.CardViewHandler;
import com.pipilika.news.nightmode.handler.ToolBarHandler;
import com.pipilika.news.nightmode.switcher.NightModeTable;
import com.pipilika.news.util.LruBitmapCache;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private static AppManager appManager;
    LruBitmapCache mLruBitmapCache;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized AppManager getAppManager() {
        return appManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appManager = new AppManager(this);
        mInstance = this;
        createNightMode();
    }

    private void createNightMode() {
        int mode = appManager.getAppThemeMode();

        NightOwl.builder().subscribedBy(new SkinObserver()).defaultMode(mode).create();
        NightOwl.owlRegisterHandler(ToolBarHandler.class, NightModeTable.NightModeToolbar.class);
        NightOwl.owlRegisterHandler(CardViewHandler.class, NightModeTable.NightModeCardView.class);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static class SkinObserver implements IOwlObserver {
        @Override
        public void onSkinChange(int appThemeMode, Activity activity) {
            appManager.setAppThemeMode(appThemeMode);
            Log.d(TAG, "Skin Change Called");
            SharedPreferencesCompat.EditorCompat.getInstance().apply(appManager.getEditor());
        }
    }


}