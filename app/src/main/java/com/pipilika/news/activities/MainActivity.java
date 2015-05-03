package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.pipilika.news.R;
import com.pipilika.news.application.AppController;
import com.pipilika.news.fragments.NewsClusterFragment;
import com.pipilika.news.utils.volley.ZipRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class MainActivity extends ActionBarActivity {

    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsClusterFragment())
                    .commit();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "1430651781825");

        String url = "http://pipilika.com:60283/RecentNewsClusterEngine/TransferZipFile?id=1430651781825";
        ZipRequest zipRequest = new ZipRequest(this, Request.Method.GET, url, params, new Response.Listener<ZipFile>() {
            @Override
            public void onResponse(ZipFile zipFile) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    try {
                        InputStream zipInputStream = zipFile.getInputStream(zipEntry);
                        bytes = new byte[(int) zipEntry.getSize()];
                        Log.e("SIZE", zipEntry.getSize() + " and int-" + ((int) zipEntry.getSize()));
                        zipInputStream.read(bytes);
                        zipInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.e(volleyError.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(zipRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
