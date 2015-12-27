package com.pipilika.news.adapters.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pipilika.news.fragments.WelcomeFourFragment;
import com.pipilika.news.fragments.WelcomeOneFragment;
import com.pipilika.news.fragments.WelcomeThreeFragment;
import com.pipilika.news.fragments.WelcomeTwoFragment;

/**
 * Created by sajid on 12/18/2015.
 */

public class WelcomePagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;

    public WelcomePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new WelcomeOneFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new WelcomeTwoFragment();
            case 2: // Fragment # 1 - This will show SecondFragment
                return new WelcomeThreeFragment();
            case 3: // Fragment # 1 - This will show SecondFragment
                return new WelcomeFourFragment();
            default:
                return null;
        }
    }

}