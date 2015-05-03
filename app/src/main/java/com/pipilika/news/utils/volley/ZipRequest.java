package com.pipilika.news.utils.volley;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipFile;

/**
 * Created by tuman on 2/5/2015.
 */
public class ZipRequest extends Request<ZipFile> {
    private static final int ZIP_TIMEOUT_MS = 1000;
    private static final int ZIP_MAX_RETRIES = 2;
    private static final float ZIP_BACKOFF_MULTI = 2.0F;
    private final Response.Listener<ZipFile> listener;
    private Map<String, String> params;
    private Activity activity;
    private File mFile;

    public ZipRequest(String url, Response.ErrorListener errorListener) {
        this(null, Method.GET, url, null, null, errorListener);
    }

    public ZipRequest(Activity activity, int method, String url, Map<String, String> params, Response.Listener<ZipFile> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.activity = activity;
        this.params = params;
        this.listener = listener;
        this.setRetryPolicy(new DefaultRetryPolicy(ZIP_TIMEOUT_MS, ZIP_MAX_RETRIES, ZIP_BACKOFF_MULTI));
        mFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/");
        if (!mFile.exists()) {
            mFile.mkdir();
            mFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters/");
            mFile.mkdir();
        } else {
            Log.e("PATH", Environment.getExternalStorageDirectory().getPath());
            mFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters/" + params.get("id") + ".zip");
        }
    }

    @Override
    protected Response<ZipFile> parseNetworkResponse(NetworkResponse networkResponse) {
        byte[] data = networkResponse.data;
        Log.e("Content-Type", networkResponse.headers.get("Content-Type"));
        Log.e("Content-Disposition", networkResponse.headers.get("Content-Disposition"));
        if (!mFile.exists()) {
            try {
                FileOutputStream zipOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters/" + params.get("id") + ".zip");
                zipOutputStream.write(data);
                zipOutputStream.flush();
                zipOutputStream.close();
            } catch (Exception e) {
                Log.e("TAGGGG", e.getClass().getSimpleName() + "- " + e.getMessage());
            }
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters/" + params.get("id") + ".zip");
        } catch (IOException e) {
            Log.e("TAGGGG", e.getMessage());
        } catch (OutOfMemoryError outOfMemoryError) {
            VolleyLog.e("Caught OOM for %d byte image, url=%s", networkResponse.data.length, this.getUrl());
            return Response.error(new ParseError(outOfMemoryError));
        }
        return Response.success(zipFile, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }


    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    protected void deliverResponse(ZipFile zipFile) {
        if (this.listener != null)
            this.listener.onResponse(zipFile);
    }
}
