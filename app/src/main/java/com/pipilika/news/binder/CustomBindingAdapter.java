package com.pipilika.news.binder;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;

/**
 * Created by sajid on 12/23/2015.
 */
public class CustomBindingAdapter {


    private static final String TAG = CustomBindingAdapter.class.getSimpleName();
    private static AppManager appManager = AppController.getAppManager();
    private static AppController appController = AppController.getInstance();

    @BindingAdapter("bind:imageSrc")
    public static void loadImage(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
        imageView.setColorFilter(appManager.getAppThemeMode() == 0 ? Color.argb(0xff, 0x0A, 0x0A, 0x0A) : Color.argb(0xff, 0xFA, 0xFA, 0xFA));
    }

    @BindingAdapter("bind:imageUrl")
    public static void downloadImage(ImageView imageView, String url) {
        Glide.with(appController).
                load(url).
                into(imageView);
    }

    @BindingAdapter("bind:favIcon")
    public static void downloadIcon(ImageView imageView, String url) {

        Log.d(TAG, "http://www.google.com/s2/favicons?domain=" + url.split("/")[2]);
        Glide.with(appController).
                load("http://www.google.com/s2/favicons?domain=" + url.split("/")[2]).
                into(imageView);
    }

    @BindingAdapter("bind:newsTime")
    public static void setNewsTime(TextView textView, String time) {
        textView.setText(appManager.getReadableDate(time));
    }
}