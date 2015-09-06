package com.pipilika.news.adapters.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pipilika.news.R;
import com.pipilika.news.activities.FullNewsActivity;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.utils.BanglaTime;
import com.pipilika.news.utils.ImageLoader;
import com.pipilika.news.view.widget.CustomTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClusterPagerAdapter extends PagerAdapter {

    private static final String TAG = ClusterPagerAdapter.class.getSimpleName();


    private String category;
    private LayoutInflater inflater;
    private List<ClusterPagerItem> clusterPagerItems;
    private String zipId;
    private int positionInList;
    private ImageLoader imageLoader;
    private Activity activity;

    public ClusterPagerAdapter(Activity activity, List<ClusterPagerItem> clusterPagerItems, String category, String zipId, int positionInList) {
        super();
        this.activity = activity;
        this.clusterPagerItems = clusterPagerItems;
        Log.e("TAG", "" + clusterPagerItems.size());
        this.category = category;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.zipId = zipId;
        this.positionInList = positionInList;
        imageLoader = new ImageLoader(activity.getApplicationContext());


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
        final View convertView = inflater.inflate(R.layout.cluster_pager_item, container, false);

        Log.e("View", (convertView.getVisibility() == View.VISIBLE) + "");
        ClusterPagerItem clusterPagerItem = clusterPagerItems.get(position);
        ImageView newsImageView = (ImageView) convertView.findViewById(R.id.news_image);
        ImageView newsPaperLogo = (ImageView) convertView.findViewById(R.id.news_paper_logo);
        if ((convertView.getVisibility() == View.VISIBLE)) {
            imageLoader.displayImage(clusterPagerItem.getImage(), newsImageView);
            imageLoader.displayImage("http://www.google.com/s2/favicons?domain=" + clusterPagerItem.getUrl().split("/")[2], newsPaperLogo);
        }

        CustomTextView headline = (CustomTextView) convertView.findViewById(R.id.news_headline);
        CustomTextView newsPaper = (CustomTextView) convertView.findViewById(R.id.news_paper_name);
        CustomTextView newsSummary = (CustomTextView) convertView.findViewById(R.id.news_summary);
        CustomTextView newsTime = (CustomTextView) convertView.findViewById(R.id.news_time);

        Log.e("url", clusterPagerItem.getUrl().split("/")[2]);
        headline.setText(clusterPagerItem.getHeadline());
        newsPaper.setText(clusterPagerItem.getBanglaname());
        newsSummary.setText(clusterPagerItem.getSummary().replace('\n', ' '));

        newsTime.setText(getReadableDate(clusterPagerItem.getPublished_time()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FullNewsActivity.class);
                intent.putExtra("news", clusterPagerItems.get(position));
                intent.putExtra("location", zipId + "/" + category + "/" + "cluster" + positionInList + "/" + position);
                activity.startActivity(intent);
            }
        });
        container.addView(convertView, 0);
        ((LinearLayout) convertView).setGravity(Gravity.CENTER);
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
        CharSequence readableFormat = DateUtils.getRelativeTimeSpanString(
                date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return BanglaTime.getBanglaTime(readableFormat).toString();
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
}
