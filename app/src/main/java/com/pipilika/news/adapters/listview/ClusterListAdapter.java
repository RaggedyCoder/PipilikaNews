package com.pipilika.news.adapters.listview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.ClusterPagerAdapter;
import com.pipilika.news.items.listview.ClusterListItem;

import java.util.List;

public class ClusterListAdapter extends BaseAdapter implements Response.ErrorListener {

    private List<ClusterListItem> clusterListItems;
    private ClusterPagerAdapter clusterPagerAdapter;
    private LayoutInflater inflater;
    private Activity activity;
    private String zipId;

    public ClusterListAdapter(Activity activity, List<ClusterListItem> clusterListItems, String zipId) {
        this.activity = activity;
        this.clusterListItems = clusterListItems;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.zipId = zipId;
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
        if (convertView == null)
            convertView = inflater.inflate(R.layout.cluster_list_item, parent, false);
        holder = new ViewHolder();
        holder.clusterPager = (ViewPager) convertView.findViewById(R.id.cluster_pager);
        holder.clusterPager.setClipToPadding(false);
        holder.clusterPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, activity.getResources().getDisplayMetrics()));
        holder.clusterPager.setClipChildren(false);
        ClusterListItem clusterListItem = clusterListItems.get(position);
        clusterPagerAdapter = new ClusterPagerAdapter(activity, clusterListItem.getNews(), clusterListItem.getCategory(), zipId, position);
        holder.clusterPager.setAdapter(clusterPagerAdapter);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    public static class ViewHolder {
        private ViewPager clusterPager;
    }
}
