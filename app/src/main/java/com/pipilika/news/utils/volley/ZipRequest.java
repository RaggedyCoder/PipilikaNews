package com.pipilika.news.utils.volley;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.pipilika.news.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipFile;

public class ZipRequest extends Request<ZipFile> {

    private static final String TAG = ZipRequest.class.getSimpleName();
    private static final int ZIP_TIMEOUT_MS = 1000;
    private static final int ZIP_MAX_RETRIES = 2;
    private static final float ZIP_BACKOFF_MULTI = 2.0F;
    private final Response.Listener<ZipFile> listener;
    private Map<String, String> params;
    private File mFile;

    public ZipRequest(String url, Map<String, String> params, Response.Listener<ZipFile> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, params, listener, errorListener);
    }

    public ZipRequest(int method, String url, Map<String, String> params, Response.Listener<ZipFile> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.listener = listener;
        this.setRetryPolicy(new DefaultRetryPolicy(ZIP_TIMEOUT_MS, ZIP_MAX_RETRIES, ZIP_BACKOFF_MULTI));
        mFile = new File(Constants.ZIP_CACHE_PATH);
        if (!mFile.exists()) {
            mFile.mkdirs();
        }
    }

    @Override
    protected Response<ZipFile> parseNetworkResponse(NetworkResponse networkResponse) {
        if (mFile.exists()) {
            try {
                createZipCache(networkResponse.data);
            } catch (Exception e) {
                Log.e(TAG, e.getClass().getSimpleName() + "- " + e.getMessage());
            }
        }
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(Constants.ZIP_CACHE_PATH + params.get("id") + ".zip");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return Response.error(new VolleyError(e));
        } catch (OutOfMemoryError outOfMemoryError) {
            Log.e(TAG, String.format("Caught OOM for %d byte image, url=%s", networkResponse.data.length, this.getUrl()));
            return Response.error(new ParseError(outOfMemoryError));
        }
        return Response.success(zipFile, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    private void createZipCache(byte[] data) throws Exception {
        FileOutputStream zipOutputStream = new FileOutputStream(Constants.ZIP_CACHE_PATH + params.get("id") + ".zip");
        zipOutputStream.write(data);
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    @Override
    protected void deliverResponse(ZipFile zipFile) {
        if (this.listener != null)
            this.listener.onResponse(zipFile);
    }
}
