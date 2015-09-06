package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.CardClusterPagerAdapter;
import com.pipilika.news.fragments.NewsClusterFragment;
import com.pipilika.news.fragments.NewsSummaryFragment;


public class MainActivity extends AppCompatActivity implements CardClusterPagerAdapter.CardNewsOnClickListner {


    private static final String TAG = MainActivity.class.getSimpleName();
    private View view;
    private NewsSummaryFragment newsSummaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsClusterFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().remove(newsSummaryFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCardItemClick(int clusterLocation, int selectedNews) {
        Log.e(TAG, clusterLocation + " on card news clicked- " + selectedNews);
        view = findViewById(R.id.sub_container);
        view.setVisibility(View.VISIBLE);
        newsSummaryFragment = NewsSummaryFragment.newInstance(clusterLocation, selectedNews);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sub_container, newsSummaryFragment)
                .commit();
    }
}
