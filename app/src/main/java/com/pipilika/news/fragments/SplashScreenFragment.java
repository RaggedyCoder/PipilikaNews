package com.pipilika.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pipilika.news.R;
import com.pipilika.news.activities.MainActivity;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.database.data.item.ZipDataItem;
import com.pipilika.news.database.data.source.ZipDataSource;
import com.pipilika.news.database.exception.DataSourceException;
import com.pipilika.news.utils.volley.Utf8JsonRequest;
import com.pipilika.news.utils.volley.ZipRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipFile;

public class SplashScreenFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private boolean isFirstTime;
    private ZipDataSource zipDataSource;
    private String url = "http://pipilika.com:60283/RecentNewsCluster/GetLatestNews";

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
                needZipRequest = jsonObject.getBoolean("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (needZipRequest) {
            if (!isFirstTime) {
                zipDataItem.setLatestOne(0);
                zipDataSource.updateZipDataItem(zipDataItem);
                Log.e("isFirstTime", isFirstTime + "");
            }
            Log.e("isFirstTime", isFirstTime + "");
            zipDataItem = new ZipDataItem();
            zipDataItem.setLatestOne(1);
            try {
                zipDataItem.setId(jsonObject.getString("latest_id"));
                zipDataSource.insertZipDataItem(zipDataItem);
            } catch (DataSourceException | JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> params = new HashMap<>();
            params.put("id", zipDataItem.getId());
            url = "http://pipilika.com:60283/RecentNewsCluster/TransferZipFile?id=" + params.get("id");
            ZipRequest zipRequest = new ZipRequest(getActivity(), Request.Method.GET, url, params, new Response.Listener<ZipFile>() {
                @Override
                public void onResponse(ZipFile zipFile) {
                    Log.e("zip", zipFile.getName());
                    zipDataSource.close();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            AppController.getInstance().addToRequestQueue(zipRequest);
        } else {
            try {
                ZipFile zipFile = new ZipFile(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters" + "/" + zipDataItem.getId() + ".zip");
                zipDataSource.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Log.e("zip", zipFile.getName());
            } catch (IOException e) {
                Log.e("error", e.getMessage());
            }
        }
        Log.e("needZipRequest", needZipRequest + "");
    }
}
