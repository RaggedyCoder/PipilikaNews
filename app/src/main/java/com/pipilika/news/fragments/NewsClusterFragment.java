package com.pipilika.news.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pipilika.news.R;
import com.pipilika.news.adapters.listview.ClusterListAdapter;
import com.pipilika.news.database.data.item.ZipDataItem;
import com.pipilika.news.database.data.source.ZipDataSource;
import com.pipilika.news.items.listview.ClusterListItem;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
        ZipDataSource zipDataSource = new ZipDataSource(getActivity());
        zipDataSource.open();
        ZipDataItem zipDataItem = new ZipDataItem();
        zipDataItem.setLatestOne(1);
        zipDataItem = zipDataSource.getZipDataItem(zipDataItem);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters" + "/" + zipDataItem.getId() + ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        zipDataSource.close();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        byte[] bytes = null;
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            try {
                InputStream zipInputStream = zipFile.getInputStream(zipEntry);
                bytes = new byte[(int) zipEntry.getSize()];
                Log.e("SIZE", zipEntry.getSize() + " and int-" + ((int) zipEntry.getSize()));
                zipInputStream.read(bytes);
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string = null;
        try {
            string = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        string.trim();
        List<ClusterListItem> clusters = gson.fromJson(string, new TypeToken<ArrayList<ClusterListItem>>() {
        }.getType());
        ClusterListAdapter clusterListAdapter = new ClusterListAdapter(getActivity(), clusters);
        clusterListView.setAdapter(clusterListAdapter);
        //Log.e("URL", allNewsItem.toString());
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {

    }
}