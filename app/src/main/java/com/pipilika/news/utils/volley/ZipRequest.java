package com.pipilika.news.utils.volley;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
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
    private static final float ZIP_BACKOFF_MULT = 2.0F;
    private final Response.Listener<ZipFile> listener;
    private Map<String, String> params;
    private Activity activity;

    public ZipRequest(String url, Response.ErrorListener errorListener) {
        this(null, Method.GET, url, null, null, errorListener);
    }

    public ZipRequest(Activity activity, int method, String url, Map<String, String> params, Response.Listener<ZipFile> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.activity = activity;
        this.params = params;
        this.listener = listener;
        this.setRetryPolicy(new DefaultRetryPolicy(ZIP_TIMEOUT_MS, ZIP_MAX_RETRIES, ZIP_BACKOFF_MULT));

    }

    @Override
    protected Response<ZipFile> parseNetworkResponse(NetworkResponse networkResponse) {
        byte[] data = networkResponse.data;
        Log.e("TAGG", networkResponse.statusCode + "");
        File file = new File("/sdcard/com.pipilika.news");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream zipOutputStream = new FileOutputStream("/sdcard/com.pipilika.news/" + "tuman.zip");
            zipOutputStream.write(data);
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (Exception e) {
            Log.e("TAGGGG", e.getClass().getSimpleName() + "- " + e.getMessage());
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile("/sdcard/com.pipilika.news/" + "tuman.zip");
        } catch (IOException e) {
            Log.e("TAGGGG", e.getMessage());
        }
        return Response.success(zipFile, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return this.params;
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
