package com.pipilika.news.adapters.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipilika.news.R;
import com.pipilika.news.adapters.viewpager.ClusterPagerAdapter;
import com.pipilika.news.items.listview.ClusterListItem;

import java.util.List;

public class ClusterListAdapter extends RecyclerView.Adapter<ClusterListAdapter.ViewHolder> {

    private static final String TAG = ClusterListAdapter.class.getSimpleName();
    private List<ClusterListItem> clusterListItems;
    private Activity activity;
    private String zipId;
    private LayoutInflater inflater;

    public ClusterListAdapter(List<ClusterListItem> clusterListItems, Activity activity, String zipId) {
        this.clusterListItems = clusterListItems;
        this.activity = activity;
        this.zipId = zipId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rootView = inflater.inflate(R.layout.cluster_list_item, parent, false);
        return new ViewHolder(rootView, activity);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (clusterListItems != null) {
            ClusterListItem clusterListItem = clusterListItems.get(position);
            ClusterPagerAdapter clusterPagerAdapter = new ClusterPagerAdapter(activity, clusterListItem.getNews(), clusterListItem.getCategory(), zipId, position);
            //CardClusterPagerAdapter clusterPagerAdapter = new CardClusterPagerAdapter(activity, clusterListItem.getNews(), clusterListItem.getCategory(), zipId, position);
            holder.clusterPager.setAdapter(clusterPagerAdapter);
            holder.clusterPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == holder.clusterPager.getAdapter().getCount() - 1) {
                        holder.clusterPager.setPageMarginDrawable(R.drawable.background);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return clusterListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final int CLUSTER_PAGER_ID = R.id.cluster_pager;
        private ViewPager clusterPager;
        private Activity activity;

        public ViewHolder(View convertView, Activity activity) {
            super(convertView);
            clusterPager = (ViewPager) convertView.findViewById(CLUSTER_PAGER_ID);
            clusterPager.setClipToPadding(false);
            this.activity = activity;
            clusterPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, this.activity.getResources().getDisplayMetrics()));
            clusterPager.setPageMargin(15);
            clusterPager.setPageMargin(15);
            clusterPager.setClipChildren(false);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
