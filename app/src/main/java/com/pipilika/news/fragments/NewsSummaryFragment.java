package com.pipilika.news.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.SummaryClusterPagerAdapter;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.items.listview.ClusterListItem;
import com.pipilika.news.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuman on 1/6/2015.
 */
public class NewsSummaryFragment extends Fragment {

    private static final String CLUSTER_LOCATION = "location";
    private static final String SELECTED_NEWS = "news";
    private static final String TAG = NewsSummaryFragment.class.getSimpleName();
    private ViewPager viewPager;
    private View rootView;
    private int clusterLocation;
    private int selectedNews;

    public static NewsSummaryFragment newInstance(int clusterLocation, int selectedNews) {
        NewsSummaryFragment fragment = new NewsSummaryFragment();
        Bundle args = new Bundle();
        args.putInt(CLUSTER_LOCATION, clusterLocation);
        args.putInt(SELECTED_NEWS, selectedNews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clusterLocation = getArguments().getInt(CLUSTER_LOCATION);
            selectedNews = getArguments().getInt(SELECTED_NEWS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news_summary, null, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.cluster_pager);
        AppManager appManager = new AppManager(getActivity());
        File file = new File(Constants.ZIP_CACHE_PATH + appManager.getLatestNewsId() + ".txt");
        byte[] bytes = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        String jsonData = null;
        try {
            jsonData = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        List<ClusterListItem> clusters = null;
        try {
            clusters = gson.fromJson(jsonData.trim(), new TypeToken<ArrayList<ClusterListItem>>() {
            }.getType());
        } catch (Exception e) {
            Log.e("gson parsing", e.getMessage());
        }
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getActivity().getResources().getDisplayMetrics()));
        viewPager.setClipChildren(false);
        SummaryClusterPagerAdapter clusterPagerAdapter = new SummaryClusterPagerAdapter(getActivity(), clusters.get(clusterLocation).getNews(), clusters.get(clusterLocation).getCategory(), appManager.getLatestNewsId(), clusterLocation);
        viewPager.setAdapter(clusterPagerAdapter);
        viewPager.setCurrentItem(selectedNews);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
