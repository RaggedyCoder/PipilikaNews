package com.pipilika.news.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

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
        params.put("id", "1430630045604");
        String url = "http://pipilika.com:60283/RecentNewsClusterEngine/TransferZipFile?id=1430630045604";
        ZipRequest zipRequest = new ZipRequest(this, Request.Method.GET, url, params, null, new Response.ErrorListener() {
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
