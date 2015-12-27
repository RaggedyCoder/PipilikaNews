package com.pipilika.news.utils.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by tuman on 23/4/2015.
 */
public class Utf8JsonRequest extends JsonRequest<JSONObject> {

    private static final String TAG = Utf8JsonRequest.class.getSimpleName();

    public Utf8JsonRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String utf8String = new String(response.data, "UTF-8");
            Log.e("TAG", utf8String);
            return Response.success(new JSONObject(utf8String), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}