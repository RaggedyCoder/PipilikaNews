package com.pipilika.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;

import com.pipilika.news.R;
import com.pipilika.news.adapter.recyclerview.AboutAdapter;
import com.pipilika.news.item.recyclerview.AboutItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajid on 12/28/2015.
 */
public class AboutActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private static final String LICENSE_URL = "file:///android_asset/licences.html";

    private static final int LICENSE = 2;
    private RecyclerView aboutRecyclerView;
    private WebView licenseWebView;
    private AboutAdapter aboutAdapter;
    private List<AboutItem> aboutItems;
    private LayoutManager layoutManager;
    private GestureDetector mGestureDetector;
    private Toolbar toolbar;

    private Animation animOpen;
    private Animation animClose;

    private Animation.AnimationListener mAnimationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initializeView();
        setSupportActionBar(toolbar);
        initializeAdapter();

        initializeAnimation();
    }

    private void initializeAnimation() {
        animOpen = AnimationUtils.loadAnimation(this, R.anim.dim_grow);
        animClose = AnimationUtils.loadAnimation(this, R.anim.dim);
        initializeAnimationListener();
        animClose.setAnimationListener(mAnimationListener);

    }

    private void initializeAnimationListener() {
        mAnimationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                licenseWebView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }


    private void initializeView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        aboutRecyclerView = (RecyclerView) findViewById(R.id.about_recycler_view);
        licenseWebView = (WebView) findViewById(R.id.license_web_view);
        licenseWebView.loadUrl(LICENSE_URL);
        layoutManager = new LinearLayoutManager(this);
        aboutRecyclerView.setLayoutManager(layoutManager);
        aboutRecyclerView.addOnItemTouchListener(this);
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    private void initializeAdapter() {
        initializeListItem();
        aboutAdapter = new AboutAdapter(this, aboutItems);
        aboutRecyclerView.setAdapter(aboutAdapter);

    }

    private void initializeListItem() {
        String[] items = getResources().getStringArray(R.array.about_app_array);
        aboutItems = new ArrayList<>();
        for (String item : items) {
            AboutItem aboutItem = new AboutItem();
            aboutItem.setTitle(item);
            aboutItems.add(aboutItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        mGestureDetector.setIsLongpressEnabled(true);

        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            int position = recyclerView.getChildAdapterPosition(child);
            switch (position) {
                case LICENSE:
                    licenseWebView.setVisibility(View.VISIBLE);
                    licenseWebView.startAnimation(animOpen);
                    break;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (licenseWebView.getVisibility() == View.VISIBLE) {
            licenseWebView.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
