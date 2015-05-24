package com.pipilika.news.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.pipilika.news.activities.MainActivity;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.utils.Constants;
import com.pipilika.news.utils.volley.Utf8JsonRequest;
import com.pipilika.news.utils.volley.ZipRequest;
import com.pipilika.news.view.widget.CustomTextView;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class SplashScreenFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String TAG = SplashScreenFragment.class.getSimpleName();
    private static int BUFFER_SIZE = 4096;
    AlertDialog alertDialog;
    private AppManager appManager;
    private CustomTextView loadingTitle;
    private String url = "http://pipilika.com:60283/RecentNewsCluster/GetLatestNews";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_splash_screen, null, false);
        loadingTitle = (CustomTextView) rootView.findViewById(R.id.loading_title);
        loadingTitle.setText("Checking Available News");
        appManager = new AppManager(getActivity());
        jsonObjectRequest();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("Unable to connect to the internet.Please try again later.");
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                getActivity().finish();
            }
        });
        alertDialogBuilder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jsonObjectRequest();
                alertDialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        alertDialog.show();
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        Log.e("TAG", jsonObject.toString());
        try {
            if (!jsonObject.getBoolean("status")) {
                File file = new File(Constants.ZIP_CACHE_PATH + appManager.getLatestNewsId() + ".txt");
                if (file.exists()) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                } else {
                    newZipFileRequest();
                }
            } else {
                File file = new File(Constants.IMAMGE_CACHE_PATH + appManager.getLatestNewsId());
                Log.e("TAG", file.isDirectory() + " ");
                FileUtils.deleteDirectory(file);
                file = new File(Constants.IMAMGE_CACHE_PATH + appManager.getLatestNewsId() + ".zip");
                FileUtils.forceDelete(file);
                file = new File(Constants.IMAMGE_CACHE_PATH + appManager.getLatestNewsId() + ".txt");
                FileUtils.forceDelete(file);
                appManager.setLatestNewsId(jsonObject.getString("latest_id"));
                newZipFileRequest();
            }
        } catch (JSONException | IOException e) {
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
        loadingTitle.setText("Downloading News Content");
        url = "http://pipilika.com:60283/RecentNewsCluster/TransferZipFile?id=" + params.get("id");
        Log.e("URL", url);
        ZipRequest zipRequest = new ZipRequest(Request.Method.GET, url, params, new Response.Listener<ZipFile>() {
            @Override
            public void onResponse(ZipFile zipFile) {
                ZipInputStream zipInputStream;
                ZipEntry zipEntry;
                try {
                    zipInputStream = new ZipInputStream(new FileInputStream(Constants.ZIP_CACHE_PATH + appManager.getLatestNewsId() + ".zip"));
                    zipEntry = zipInputStream.getNextEntry();
                    extractFile(zipInputStream, zipEntry.getName());
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
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

    private void extractFile(ZipInputStream zipInputStream, String name) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Constants.ZIP_CACHE_PATH + name));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read;
        while ((read = zipInputStream.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
