package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.fragments.FullNewsFragment;
import com.pipilika.news.items.viewpager.ClusterPagerItem;

public class FullNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);

        if (savedInstanceState == null) {
            String location = getIntent().getExtras().getString("location");
            ClusterPagerItem clusterPagerItem = getIntent().getExtras().getParcelable("news");
            String temp = "";
            AppManager appManager = new AppManager(this);
            for (int i = (appManager.getLatestNewsId() + "/").length();
                 i < location.length(); i++) {
                if (location.charAt(i) == '/')
                    break;
                temp += location.charAt(i);
            }
            //getSupportActionBar().setTitle(temp);
            FullNewsFragment fullNewsFragment = FullNewsFragment.newInstance(location, clusterPagerItem);
            getSupportFragmentManager().beginTransaction().add(R.id.container, fullNewsFragment).commit();
        }
    }
}
