package com.pipilika.news.adapters.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pipilika.news.R;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.view.widget.CustomTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CardClusterPagerAdapter extends PagerAdapter {

    private static final String TAG = CardClusterPagerAdapter.class.getSimpleName();
    CardNewsOnClickListner mCardNewsOnClickListner;
    private String category;
    private LayoutInflater inflater;
    private List<ClusterPagerItem> clusterPagerItems;
    private String zipId;
    private int positionInList;

    private Activity activity;

    public CardClusterPagerAdapter(Activity activity, List<ClusterPagerItem> clusterPagerItems, String category, String zipId, int positionInList) {
        super();
        this.activity = activity;
        if (activity instanceof CardNewsOnClickListner)
            mCardNewsOnClickListner = (CardNewsOnClickListner) activity;
        this.clusterPagerItems = clusterPagerItems;
        Log.e("TAG", "" + clusterPagerItems.size());
        this.category = category;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.zipId = zipId;
        this.positionInList = positionInList;
    }

    @Override
    public int getCount() {
        return clusterPagerItems.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View convertView = inflater.inflate(R.layout.card_cluster_pager_item, null);
        ClusterPagerItem clusterPagerItem = clusterPagerItems.get(position);

        CustomTextView headline = (CustomTextView) convertView.findViewById(R.id.news_headline);
        CustomTextView newsPaper = (CustomTextView) convertView.findViewById(R.id.news_paper_name);
        CustomTextView newsTime = (CustomTextView) convertView.findViewById(R.id.news_time);

        headline.setText(clusterPagerItem.getHeadline());
        newsPaper.setText(clusterPagerItem.getBanglaname());

        newsTime.setText(getReadableDate(clusterPagerItem.getPublished_time()));
        container.addView(convertView, 0);
        ((LinearLayout) convertView).setGravity(Gravity.CENTER);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardNewsOnClickListner.onCardItemClick(positionInList, position);
            }
        });
        return convertView;
    }

    private String getReadableDate(String published_time) {
        SimpleDateFormat nonReadableFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = new Date();
        try {
            date = nonReadableFormat.parse(published_time);
        } catch (ParseException e) {
            nonReadableFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            try {
                date = nonReadableFormat.parse(published_time);
            } catch (ParseException e1) {
                Log.e(TAG, e.getMessage());
            }
        }
        return DateUtils.getRelativeTimeSpanString(
                date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString().trim();
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    public interface CardNewsOnClickListner {
        void onCardItemClick(int clusterLocation, int selectedNews);
    }
}
