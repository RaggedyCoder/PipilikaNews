package com.pipilika.news.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.asha.nightowllib.NightOwl;
import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.fragments.NewsClusterFragment;
import com.pipilika.news.fragments.TimeLineFragment;
import com.pipilika.news.utils.Utils;
import com.pipilika.news.view.widget.CustomToolbar;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private Fragment mFragment;

    private AppManager appManager = AppController.getAppManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        if (Utils.hasLollipop()) {
            int color = appManager.getAppThemeMode() == 0 ? R.color.colorPrimaryDark : R.color.nightColorPrimaryDark;
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        Log.d(TAG, "onCreate(Bundle savedInstanceState)");
        setContentView(R.layout.activity_main);
        CustomToolbar toolbar = (CustomToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NightOwl.owlAfterCreate(this);
        NightOwl.owlRegisterCustom(toolbar);
        if (savedInstanceState == null) {
            mFragment = new NewsClusterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commit();
            Log.d(TAG, "Fragment Transaction Commit.");
        } else {
            String languageToLoad = "bn";
            String country = "BD";
            Locale locale = new Locale(languageToLoad, country);
            Configuration config = new Configuration();
            config.locale = locale;
            Locale.setDefault(locale);
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mFragment");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem actionNightMode = menu.getItem(2);
        actionNightMode.setChecked(!(appManager.getAppThemeMode() == 0));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }

    public void onFragmentChange(Fragment fragment) {
        mFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected(MenuItem item)");
        switch (item.getItemId()) {
            case R.id.action_timeline_news:
                TimeLineFragment timeLineFragment = new TimeLineFragment();
                mFragment = timeLineFragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, timeLineFragment)
                        .commit();
                break;
            case R.id.action_tutorial:
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra("openedFromMainActivity", true);
                startActivity(intent);
                break;
            case R.id.action_night_mode:
                Log.d(TAG, "skin change action attempt");
                item.setChecked(!item.isChecked());
                NightOwl.owlNewDress(this);
                if (Utils.hasLollipop()) {
                    int color = appManager.getAppThemeMode() == 0 ? R.color.colorPrimaryDark : R.color.nightColorPrimaryDark;
                    getWindow().setStatusBarColor(getResources().getColor(color));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mFragment", mFragment);

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if (mFragment instanceof TimeLineFragment) {
            NewsClusterFragment newsClusterFragment = new NewsClusterFragment();
            mFragment = newsClusterFragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, newsClusterFragment)
                    .commit();
        } else {
            super.onBackPressed();
        }
    }
}
