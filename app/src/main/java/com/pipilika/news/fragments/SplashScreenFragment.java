package com.pipilika.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipilika.news.R;

/**
 * Created by tuman on 3/5/2015.
 */
public class SplashScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash_screen, null, false);
        return rootView;
    }
}
