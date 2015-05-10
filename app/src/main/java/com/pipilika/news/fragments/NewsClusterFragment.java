package com.pipilika.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pipilika.news.R;
import com.pipilika.news.adapters.listview.ClusterListAdapter;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.items.listview.ClusterListItem;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsClusterFragment extends Fragment {

    private static final String TAG = NewsClusterFragment.class.getSimpleName();
    private ListView clusterListView;
    private View rootView;

    public NewsClusterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.cluster_news_fragment, null, false);
        clusterListView = (ListView) rootView.findViewById(R.id.cluster_list_view);
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
        if (clusters != null) {
            margeAllCategory(clusters);
            ClusterListAdapter clusterListAdapter = new ClusterListAdapter(getActivity(), clusters, appManager.getLatestNewsId());
            clusterListView.setAdapter(clusterListAdapter);
        }
        return rootView;
    }

    private void margeAllCategory(List<ClusterListItem> clusters) {

        for (int i = 0; i < clusters.size() - 1; i++) {
            ClusterListItem clusterListItem1 = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); ) {
                ClusterListItem clusterListItem2 = clusters.get(j);
                if (clusterListItem1.getCategory().equals(clusterListItem2.getCategory())) {
                    List<ClusterPagerItem> clusterPagerItem1 = clusterListItem1.getNews();
                    List<ClusterPagerItem> clusterPagerItem2 = clusterListItem2.getNews();
                    for (int k = 0; k < clusterPagerItem2.size(); k++) {
                        clusterPagerItem1.add(clusterPagerItem2.get(k));
                    }
                    clusters.remove(j);
                } else {
                    j++;
                }
            }
        }
    }
}