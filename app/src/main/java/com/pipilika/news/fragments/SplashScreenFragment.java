package com.pipilika.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.database.data.item.ZipDataItem;
import com.pipilika.news.database.data.source.ZipDataSource;
import com.pipilika.news.database.exception.DataSourceException;
import com.pipilika.news.utils.volley.Utf8JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tuman on 3/5/2015.
 */
public class SplashScreenFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private boolean isFirstTime;
    private ZipDataSource zipDataSource;
    private String url = "http://pipilika.com:60283/RecentNewsClusterEngine/GetLatestNews";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash_screen, null, false);
        AppManager appManager = new AppManager(getActivity());
        zipDataSource = new ZipDataSource(getActivity());
        zipDataSource.open();
        if (appManager.isFirstTime()) {
            isFirstTime = true;
            appManager.setFirstTime();
        } else {
            isFirstTime = false;
        }
        Utf8JsonRequest utf8JsonRequest = new Utf8JsonRequest(Request.Method.GET, url, null, this, this);
        AppController.getInstance().addToRequestQueue(utf8JsonRequest);
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        ZipDataItem zipDataItem;
        Log.e("TAG", jsonObject.toString());
        boolean needZipRequest = true;
        if (isFirstTime) {
            zipDataItem = new ZipDataItem();
            zipDataItem.setLatestOne(1);
            try {
                zipDataItem.setId(jsonObject.getString("latest_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                zipDataSource.insertZipDataItem(zipDataItem);
            } catch (DataSourceException e) {
                e.printStackTrace();
            }
        } else {
            zipDataItem = new ZipDataItem();
            zipDataItem.setLatestOne(1);
            zipDataItem = zipDataSource.getZipDataItem(zipDataItem);
            Log.e("TAG", zipDataItem.getId());
            try {
                needZipRequest = jsonObject.getString("latest_id").equals(zipDataItem.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
