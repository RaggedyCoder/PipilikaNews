package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.pipilika.news.R;
import com.pipilika.news.fragments.SplashScreenFragment;

/**
 * Created by tuman on 3/5/2015.
 */
public class SplashScreenActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SplashScreenFragment())
                    .commit();
        }
    }
}
