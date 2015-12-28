package com.pipilika.news.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.asha.nightowllib.NightOwl;
import com.pipilika.news.R;
import com.pipilika.news.fragment.SplashScreenFragment;

import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        String languageToLoad = "bn";
        String country = "BD";
        Locale locale = new Locale(languageToLoad, country);
        Configuration config = new Configuration();
        config.locale = locale;
        Locale.setDefault(locale);
        getBaseContext().getResources().updateConfiguration(config, null);
        setContentView(R.layout.activity_splash);
        NightOwl.owlAfterCreate(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new SplashScreenFragment()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }
}
