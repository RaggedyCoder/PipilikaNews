package com.pipilika.news.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pipilika.news.R;
import com.pipilika.news.adapters.recyclerview.ClusterListAdapter;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.items.SingleClusterNews;
import com.pipilika.news.items.listview.ClusterListItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.pipilika.news.utils.Utils.newsFileName;

public class NewsClusterFragment extends Fragment {

    private static final String TAG = NewsClusterFragment.class.getSimpleName();
    private static final String NEWS_ID = "newsId";
    private RecyclerView clusterRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private View rootView;
    private AppManager appManager;
    private String id = null;
    private ClusterListAdapter clusterListAdapter;

    public NewsClusterFragment() {
    }

    public static NewsClusterFragment newInstance(String id) {
        NewsClusterFragment newsClusterFragment = new NewsClusterFragment();
        newsClusterFragment.setMenuVisibility(true);
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, id);
        newsClusterFragment.setId(id);
        newsClusterFragment.setArguments(bundle);
        return newsClusterFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && getArguments() != null) {
            id = getArguments().getString(NEWS_ID);
            Log.e(TAG, id + "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_cluster_news, container, false);
        clusterRecyclerView = (RecyclerView) rootView.findViewById(R.id.cluster_recycler_view);
        appManager = new AppManager(getActivity());
        List<ClusterListItem> clusters = newCluster(id != null ? id : appManager.getLatestNewsId());


        if (clusters != null) {
            clusterListAdapter = new ClusterListAdapter(clusters, getActivity(), appManager.getLatestNewsId());
            clusterRecyclerView.setAdapter(clusterListAdapter);
            clusterRecyclerView.setHasFixedSize(true);
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            clusterRecyclerView.setLayoutManager(mLinearLayoutManager);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
        inflater.inflate(R.menu.menu_main, menu);
    }

    private List<ClusterListItem> newCluster(String id) {
        File file = new File(newsFileName(id));
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            read(inputStream, bytes);
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }


        String jsonData = "";
        try {
            jsonData = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(jsonData.trim(), new TypeToken<ArrayList<ClusterListItem>>() {
            }.getType());
        } catch (Exception e) {
            return gson.fromJson(jsonData.trim(), SingleClusterNews.class).getNews();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuVisibility(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private int read(InputStream inputStream, byte[] bytes) throws IOException {
        return inputStream.read(bytes);
    }

    public void setId(String id) {
        this.id = id;
    }
}