package com.pipilika.news.adapters.listview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.ClusterPagerAdapter;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.listview.ClusterListItem;
import com.pipilika.news.utils.volley.TrackedRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by tuman on 21/4/2015.
 */
public class ClusterListAdapter extends BaseAdapter implements Response.ErrorListener {

    private List<ClusterListItem> clusterListItems;
    private ClusterPagerAdapter clusterPagerAdapter;
    private LayoutInflater inflater;
    private Activity activity;

    public ClusterListAdapter(Activity activity, List<ClusterListItem> clusterListItems) {
        this.activity = activity;
        this.clusterListItems = clusterListItems;
        Log.e("TAG SIZE", clusterListItems.size() + "size");
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cluster_list_item, parent, false);
            holder = new ViewHolder();
            holder.clusterPager = (ViewPager) convertView.findViewById(R.id.cluster_pager);
            holder.clusterPager.setClipToPadding(false);
            holder.clusterPager.setPageMargin((int) TypedValue.
                    applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, activity.getResources().getDisplayMetrics()));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.e("TAG", position + " of list");
        final int rr = position;
        holder.clusterPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int pos) {
                if (pos == clusterListItems.get(rr).getNews().size()) {

                    String url = clusterListItems.get(rr).getNext_url();
                    TrackedRequest jsonObjectRequest = new TrackedRequest(Request.Method.GET, url,
                            null, new TrackedRequest.ListenerTracker<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject, int id) {
                            String string = null;
                            try {
                                string = new String(jsonObject.toString().getBytes(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().create();
                            ClusterListItem clusterListItem = gson.fromJson(jsonObject.toString(), ClusterListItem.class);
                            clusterListItems.get(id).getNews().addAll(clusterListItem.getNews());
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onResponse(JSONObject jsonObject) {

                        }
                    }, ClusterListAdapter.this, rr);
                    AppController.getInstance().addToRequestQueue(jsonObjectRequest);

                }
                Log.e("TAG", rr + " Selected page-" + pos);
            }
        });
        holder.clusterPager.setPageMargin(15);
        holder.clusterPager.setPageMargin(15);
        holder.clusterPager.setClipChildren(false);
        clusterPagerAdapter = new ClusterPagerAdapter(activity, clusterListItems.get(position).getNews(),
                clusterListItems.get(position).getCategory());
        holder.clusterPager.setAdapter(clusterPagerAdapter);
        return convertView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    public static class ViewHolder {
        private ViewPager clusterPager;
    }
}
