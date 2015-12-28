package com.pipilika.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asha.nightowllib.NightOwl;
import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.fragment.FullNewsFragment;
import com.pipilika.news.item.viewpager.ClusterPagerItem;
import com.pipilika.news.util.Utils;

public class FullNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        NightOwl.owlAfterCreate(this);
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
            if (Utils.hasLollipop()) {
                int color = appManager.getAppThemeMode() == 0 ? R.color.colorPrimaryDark : R.color.nightColorPrimaryDark;
                getWindow().setStatusBarColor(getResources().getColor(color));
            }
            //getSupportActionBar().setTitle(temp);
            FullNewsFragment fullNewsFragment = FullNewsFragment.newInstance(location, clusterPagerItem);
            getSupportFragmentManager().beginTransaction().add(R.id.container, fullNewsFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }
}
