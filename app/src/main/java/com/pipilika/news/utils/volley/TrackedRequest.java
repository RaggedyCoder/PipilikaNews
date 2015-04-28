package com.pipilika.news.utils.volley;

import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by tuman on 28/4/2015.
 */
public class TrackedRequest extends Utf8JsonRequest {
    private ListenerTracker<JSONObject> listener;
    private int tag;

    public TrackedRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public TrackedRequest(int method, String url, String requestBody, ListenerTracker<JSONObject> listener, Response.ErrorListener errorListener, int tag) {
        this(method, url, requestBody, listener, errorListener);
        Log.e("TAG1", "" + (listener == null));
        this.listener = listener;
        this.tag = tag;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        Log.e("TAG2", "" + (this.listener == null));
        this.listener.onResponse(response, tag);
    }

    public interface ListenerTracker<JSONObject> extends Response.Listener<JSONObject> {
        void onResponse(JSONObject var1, int id);
    }


}
