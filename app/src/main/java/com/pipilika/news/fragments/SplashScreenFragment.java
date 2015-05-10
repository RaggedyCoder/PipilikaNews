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
import com.pipilika.news.utils.Constants;
import com.pipilika.news.utils.volley.Utf8JsonRequest;
import com.pipilika.news.utils.volley.ZipRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SplashScreenFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = SplashScreenFragment.class.getSimpleName();
    private AppManager appManager;
    private String url = "http://pipilika.com:60283/RecentNewsCluster/GetLatestNews";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash_screen, null, false);
        appManager = new AppManager(getActivity());
        jsonObjectRequest();
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        Log.e("TAG", jsonObject.toString());
        try {
            if (!jsonObject.getBoolean("status")) {
                File file = new File(Constants.ZIP_CACHE_PATH + appManager.getLatestNewsId() + ".json");
                if (file.exists()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                } else {
                    newZipFileRequest();
                }
            } else {
                appManager.setLatestNewsId(jsonObject.getString("latest_id"));
                newZipFileRequest();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonObjectRequest() {
        url = "http://pipilika.com:60283/RecentNewsCluster/GetLatestNews";
        if (!appManager.getLatestNewsId().equals("0")) {
            url += "?id=" + appManager.getLatestNewsId();
        }
        Utf8JsonRequest utf8JsonRequest = new Utf8JsonRequest(Request.Method.GET, url, null, this, this);
        AppController.getInstance().addToRequestQueue(utf8JsonRequest);
    }

    private void newZipFileRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", appManager.getLatestNewsId());
        url = "http://pipilika.com:60283/RecentNewsCluster/TransferZipFile?id=" + params.get("id");
        Log.e("URL", url);
        ZipRequest zipRequest = new ZipRequest(Request.Method.GET, url, params, new Response.Listener<ZipFile>() {
            @Override
            public void onResponse(ZipFile zipFile) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                byte[] bytes = null;
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    try {
                        InputStream zipInputStream = zipFile.getInputStream(zipEntry);
                        bytes = new byte[(int) zipEntry.getSize()];
                        zipInputStream.read(bytes);
                        zipInputStream.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                    File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters" + "/" + appManager.getLatestNewsId() + ".json");
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int len = bytes.length;
                    try {
                        fos.write(bytes, 0, len);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AppController.getInstance().addToRequestQueue(zipRequest);
    }
}
