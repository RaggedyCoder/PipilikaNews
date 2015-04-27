package com.pipilika.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pipilika.news.R;
import com.pipilika.news.adapters.listview.ClusterListAdapter;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.AllNewsItem;
import com.pipilika.news.utils.volley.Utf8JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * A fragment containing the view for all kind of news.
 */
public class NewsClusterFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ListView clusterListView;

    public NewsClusterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cluster_news_fragment, null, false);
        clusterListView = (ListView) rootView.findViewById(R.id.cluster_list_view);
        String url = "https://s3-ap-southeast-1.amazonaws.com/recent-news/images/20-04-2015/recent.json";
        Utf8JsonRequest jsonObjectRequest = new Utf8JsonRequest(Request.Method.GET, url, null, this, this);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        String string = null;
        try {
            string = new String(jsonObject.toString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        AllNewsItem allNewsItem = gson.fromJson(jsonObject.toString(), AllNewsItem.class);
        ClusterListAdapter clusterListAdapter = new ClusterListAdapter(getActivity(), allNewsItem.getClusters());
        clusterListView.setAdapter(clusterListAdapter);
        //Log.e("URL", allNewsItem.toString());
    }
}