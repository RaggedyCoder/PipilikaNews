package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.pipilika.news.R;
import com.pipilika.news.application.AppController;
import com.pipilika.news.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by tuman on 12/5/2015.
 */
public class NewsImageView extends ImageView {

    private String mUrl;
    private AppController mAppController;
    private String mCategory;
    private String mZipId;
    private int mPosition;
    private int mPositionInList;
    private String mLocation;
    private int progressBarID;
    private View view;


    public NewsImageView(Context context) {
        super(context);
        mAppController = AppController.getInstance();
    }

    public NewsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsImageView);
        progressBarID = a.getResourceId(R.styleable.NewsImageView_loaderViewId, 0);
        view = getRootView().findViewById(progressBarID);
        a.recycle();
        mAppController = AppController.getInstance();
    }

    public NewsImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsImageView);
        progressBarID = a.getResourceId(R.styleable.NewsImageView_loaderViewId, 0);
        view = getRootView().findViewById(progressBarID);
        a.recycle();
        mAppController = AppController.getInstance();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewsImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsImageView);
        progressBarID = a.getResourceId(R.styleable.NewsImageView_loaderViewId, 0);
        view = getRootView().findViewById(progressBarID);
        a.recycle();
        mAppController = AppController.getInstance();
    }

    public void setImageUrl(String url, String location) {
        createCacheFolder(location);
        mUrl = url;
        imageRequest();
    }

    public void setImageUrl(String url, String zipId, String category, int positionInList, int position) {
        createCacheFolder(zipId, category, positionInList, position);
        mUrl = url;
        imageRequest();
    }

    private void imageRequest() {
        ImageRequest imageRequest = new ImageRequest(mUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                File file = new File(Constants.IMAMGE_CACHE_PATH + mLocation + ".png");
                Log.e("TAG", file.getAbsolutePath());
                FileOutputStream outStream = null;
                try {
                    outStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                setImageBitmap(bitmap);
                view = getRootView().findViewById(progressBarID);
                view.setVisibility(GONE);
                Log.e("TAG", progressBarID + " " + (view.getVisibility() == GONE));
            }
        }, getWidth(), getHeight(), Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        mAppController.addToRequestQueue(imageRequest);
    }

    private boolean createCacheFolder(String zipId, String category, int positionInList, int position) {
        mCategory = category;
        mPositionInList = positionInList;
        mPosition = position;
        mZipId = zipId;
        mLocation = mZipId + "/" + mCategory + "/" + "cluster" + mPositionInList + "/" + mPosition;
        File file = new File(Constants.IMAMGE_CACHE_PATH + mZipId + "/" + mCategory + "/" + "cluster" + mPositionInList + "/");
        return file.mkdirs();
    }

    private boolean createCacheFolder(String location) {
        mLocation = location;
        File file = new File(Constants.IMAMGE_CACHE_PATH + mZipId + "/" + mCategory + "/" + "cluster" + mPositionInList + "/");
        return file.mkdirs();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        view = getRootView().findViewById(progressBarID);
        view.setVisibility(GONE);
        Log.e("TAG", progressBarID + " " + (view.getVisibility() == GONE));
    }
}