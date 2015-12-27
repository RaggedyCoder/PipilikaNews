package com.pipilika.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.asha.nightowllib.NightOwl;
import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.WelcomePagerAdapter;
import com.pipilika.news.appdata.AppManager;

/**
 * Created by sajid on 12/18/2015.
 */
public class WelcomeActivity extends AppCompatActivity {
    WelcomePagerAdapter welcomePagerAdapter;
    int i = 0;
    AppManager appManager;
    private ViewPager welcomeViewPager;
    private Button nextButton;
    private Button skipButton;
    private boolean openedFromMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NightOwl.owlBeforeCreate(this);
        super.onCreate(savedInstanceState);
        appManager = new AppManager(this);
        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                openedFromMainActivity = getIntent().getExtras().getBoolean("openedFromMainActivity", false);
            }
        }
        if (!appManager.getFirstTimer() && !openedFromMainActivity) {
            appManager.setFirstTimer(false);
            Intent intent = new Intent(WelcomeActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_tutorial);
            NightOwl.owlAfterCreate(this);
            welcomeViewPager = (ViewPager) findViewById(R.id.welcome_view_pager);
            nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = welcomeViewPager.getCurrentItem();
                    Log.d("TAG", welcomeViewPager.getCurrentItem() + "");
                    if (welcomeViewPager.getCurrentItem() < 3) {
                        welcomeViewPager.setCurrentItem(++i, true);

                    } else if (welcomeViewPager.getCurrentItem() == 3) {
                        if (openedFromMainActivity) {
                            finish();
                        } else {
                            appManager.setFirstTimer(false);
                            Intent intent = new Intent(WelcomeActivity.this, SplashScreenActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
            skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (openedFromMainActivity) {
                        finish();
                    } else {
                        appManager.setFirstTimer(false);
                        Intent intent = new Intent(WelcomeActivity.this, SplashScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
            welcomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 3) {
                        skipButton.setVisibility(View.GONE);
                        nextButton.setText(R.string.done);
                    } else {
                        skipButton.setVisibility(View.VISIBLE);
                        nextButton.setText(R.string.next);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
            welcomeViewPager.setAdapter(welcomePagerAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NightOwl.owlResume(this);
    }
}
