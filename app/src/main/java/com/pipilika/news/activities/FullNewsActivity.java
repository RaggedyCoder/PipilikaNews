package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.pipilika.news.R;
import com.pipilika.news.fragments.FullNewsFragment;
import com.pipilika.news.items.viewpager.ClusterPagerItem;

public class FullNewsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_news_activity);

        if (savedInstanceState == null) {
            String location = getIntent().getExtras().getString("location");
            ClusterPagerItem clusterPagerItem = getIntent().getExtras().getParcelable("news");
            FullNewsFragment fullNewsFragment = FullNewsFragment.newInstance(location, clusterPagerItem);
            getSupportFragmentManager().beginTransaction().add(R.id.container, fullNewsFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
