package com.pipilika.news.adapters.listview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.ClusterPagerAdapter;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.listview.ClusterListItem;
import com.pipilika.news.utils.volley.Utf8JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by tuman on 21/4/2015.
 */
public class ClusterListAdapter extends ArrayAdapter<ClusterListItem> implements Response.Listener<JSONObject>, Response.ErrorListener {

    private List<ClusterListItem> clusterListItems;
    private ClusterPagerAdapter clusterPagerAdapter;
    private LayoutInflater inflater;
    private Activity activity;

    public ClusterListAdapter(Activity activity, List<ClusterListItem> clusterListItems) {
        super(activity, R.layout.cluster_list_item, clusterListItems);
        this.activity = activity;
        this.clusterListItems = clusterListItems;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clusterListItems.size();
    }

    @Override
    public ClusterListItem getItem(int position) {
        return clusterListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cluster_list_item, parent, false);
            holder = new ViewHolder();
            holder.clusterPager = (ViewPager) convertView.findViewById(R.id.cluster_pager);
            holder.clusterPager.setClipToPadding(false);
            holder.clusterPager.setPageMargin((int) TypedValue.
                    applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, activity.getResources().getDisplayMetrics()));

            holder.clusterPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int pos) {
                    if (pos == clusterListItems.get(position).getNews().size()) {

                        String url = clusterListItems.get(position).getNext_url();
                        Utf8JsonRequest jsonObjectRequest = new Utf8JsonRequest(Request.Method.GET, url,
                                null, ClusterListAdapter.this, ClusterListAdapter.this);
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

                    }
                    // Toast.makeText(activity, "Selected page-" + pos, Toast.LENGTH_SHORT).show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.clusterPager.setPageMargin(15);
        holder.clusterPager.setPageMargin(15);
        holder.clusterPager.setClipChildren(false);
        clusterPagerAdapter = new ClusterPagerAdapter(activity, clusterListItems.get(position).getNews());
        holder.clusterPager.setAdapter(clusterPagerAdapter);
        return convertView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        String string = null;
        try {
            string = new String(jsonObject.toString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        ClusterListItem clusterListItem = gson.fromJson(jsonObject.toString(), ClusterListItem.class);
        clusterListItems.get(0).getNews().addAll(clusterListItem.getNews());
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private ViewPager clusterPager;
    }
}
