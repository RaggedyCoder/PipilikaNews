package com.pipilika.news.fragment;

import android.app.AlertDialog;
import android.content.Context;
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
import com.pipilika.news.activity.MainActivity;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.item.fragment.LatestNews;
import com.pipilika.news.util.Constants;
import com.pipilika.news.util.volley.ObjectRequest;
import com.pipilika.news.util.volley.ZipRequest;
import com.pipilika.news.view.widget.CustomTextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.pipilika.news.util.Constants.API_GET_LATEST_NEWS;
import static com.pipilika.news.util.Constants.API_TRANSFER_ZIPPED_NEWS;
import static com.pipilika.news.util.Constants.API_URL;

public class SplashScreenFragment extends Fragment implements Response.ErrorListener {

    private static final String TAG = SplashScreenFragment.class.getSimpleName();
    private static int BUFFER_SIZE = 4096;
    AlertDialog alertDialog;
    private AppManager appManager;
    private View rootView;
    private Response.Listener<LatestNews> latestNewsListener;
    private Response.Listener<ZipFile> zipFileListener;

    private CustomTextView loadingTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager = new AppManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        loadingTitle = (CustomTextView) rootView.findViewById(R.id.loading_title);
        loadingTitle.setText(R.string.server_news_check_message);
        checkRecentNewsCluster();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(R.string.no_internet_connection);
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                getActivity().finish();
            }
        });
        alertDialogBuilder.setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkRecentNewsCluster();
                alertDialog.dismiss();
            }
        });
        alertDialog = alertDialogBuilder.create();
        latestNewsListener = new Response.Listener<LatestNews>() {
            @Override
            public void onResponse(LatestNews latestNews) {
                Log.e(TAG, "json object-" + latestNews.toString());
                if (latestNews.getLatestId().equals(appManager.getLatestNewsId())) {
                    File file = new File(newsFileName(appManager.getLatestNewsId()));
                    if (file.exists()) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        newZipFileRequest();
                    }
                } else {
                    appManager.setLatestNewsId(latestNews.getLatestId());
                    newZipFileRequest();
                }
            }
        };

        zipFileListener = new Response.Listener<ZipFile>() {
            @Override
            public void onResponse(ZipFile zipFile) {
                processZipFile(BUFFER_SIZE);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        };
    }

    private void processZipFile(final int bufferSize) {
        ZipInputStream zipInputStream;
        ZipEntry zipEntry;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipFileName(appManager.getLatestNewsId())));
            zipEntry = zipInputStream.getNextEntry();
            extractFile(zipInputStream, zipEntry.getName(), bufferSize);
            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String zipFileName(String fileName) {

        return String.format(getActivity().getCacheDir().getAbsolutePath() + "%s%s.zip", Constants.ZIP_CACHE_PATH, fileName);
    }

    private String newsFileName(String fileName) {
        return String.format(getActivity().getCacheDir().getAbsolutePath() +"%s%s.txt", Constants.ZIP_CACHE_PATH, fileName);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if (appManager.getLatestNewsId().equals("0")) {
            alertDialog.show();
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().finish();
            startActivity(intent);
        }

    }

    private void checkRecentNewsCluster() {
        String url = API_URL + API_GET_LATEST_NEWS;
        ObjectRequest<LatestNews> objectRequest = new ObjectRequest<>(Request.Method.GET, url, latestNewsListener, this,
                LatestNews.class);
        AppController.getInstance().addToRequestQueue(objectRequest);
    }

    private void newZipFileRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", appManager.getLatestNewsId());
        loadingTitle.setText(R.string.downloading_news_content);
        String url = API_URL + API_TRANSFER_ZIPPED_NEWS + "?id=" + appManager.getLatestNewsId();
        Log.e("URL", url);
        ZipRequest zipRequest = new ZipRequest(Request.Method.GET, url, params, zipFileListener, this);
        zipRequest.setActivity(getActivity());
        AppController.getInstance().addToRequestQueue(zipRequest);
    }

    private void extractFile(ZipInputStream zipInputStream, String name, final int bufferSize) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(getActivity().getCacheDir().getAbsolutePath() + Constants.ZIP_CACHE_PATH + name));
        byte[] bytesIn = new byte[bufferSize];
        int read;
        while ((read = zipInputStream.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
