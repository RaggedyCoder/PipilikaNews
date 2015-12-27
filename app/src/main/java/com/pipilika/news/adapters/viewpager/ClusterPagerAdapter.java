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

import com.bumptech.glide.Glide;
import com.pipilika.news.R;
import com.pipilika.news.activities.FullNewsActivity;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.utils.BanglaTime;
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
    private Activity activity;

    private AppController appController = AppController.getInstance();

    public ClusterPagerAdapter(Activity activity, List<ClusterPagerItem> clusterPagerItems, String category, String zipId, int positionInList) {
        super();
        this.activity = activity;
        this.clusterPagerItems = clusterPagerItems;
        Log.e(TAG, "" + clusterPagerItems.size());
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
        final View convertView = inflater.inflate(R.layout.cluster_pager_item, container, false);

        Log.e("View", (convertView.getVisibility() == View.VISIBLE) + "");
        ClusterPagerItem clusterPagerItem = clusterPagerItems.get(position);
        ImageView newsImageView = (ImageView) convertView.findViewById(R.id.news_image);
        ImageView newsPaperLogo = (ImageView) convertView.findViewById(R.id.news_paper_logo);
        if ((convertView.getVisibility() == View.VISIBLE)) {
//            imageLoader.displayImage(clusterPagerItem.getImageUrl(), newsImageView);
            Glide.with(appController).
                    load(clusterPagerItem.getImageUrl()).
                    into(newsImageView);
            //imageLoader.displayImage(, newsPaperLogo);
            Glide.with(appController).
                    load("http://www.google.com/s2/favicons?domain=" + clusterPagerItem.getUrl().split("/")[2]).
                    into(newsPaperLogo);
        }

        CustomTextView headline = (CustomTextView) convertView.findViewById(R.id.news_headline);
        CustomTextView newsPaper = (CustomTextView) convertView.findViewById(R.id.news_paper_name);
        CustomTextView newsSummary = (CustomTextView) convertView.findViewById(R.id.news_summary);
        CustomTextView newsTime = (CustomTextView) convertView.findViewById(R.id.news_time);

        Log.e("url", clusterPagerItem.getUrl().split("/")[2]);
        headline.setText(clusterPagerItem.getHeadline());
        String paperName = clusterPagerItem.getPaperNameBangla() != null ? (clusterPagerItem.getPaperNameBangla().trim().length() != 0 ? clusterPagerItem.getPaperNameBangla() : clusterPagerItem.getPaperName()) : clusterPagerItem.getPaperName();
        newsPaper.setText(paperName);
        String summary = clusterPagerItem.getSummary().replace('\n', ' ');
        int i = 300;
        do {
            if (summary.length() <= 300) {
                break;
            } else {
                if (summary.charAt(i) == ' ' || summary.charAt(i) == '।' || summary.charAt(i) == '.') {
                    break;
                } else {
                    i++;
                }
            }
        } while (true);
        if (summary.length() > 300) {
            summary = summary.substring(0, (i - 1)) + "...";

        }
        newsSummary.setText(summary);

        newsTime.setText(getReadableDate(clusterPagerItem.getPublishedTime()));
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

    private String getReadableDate(String publishedTime) {
        SimpleDateFormat nonReadableFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = new Date();
        try {
            date = nonReadableFormat.parse(publishedTime);
        } catch (ParseException e) {
            nonReadableFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            try {
                date = nonReadableFormat.parse(publishedTime);
            } catch (ParseException e1) {
                Log.e(TAG, e.getMessage());
            }
        }
        CharSequence readableFormat = DateUtils.getRelativeTimeSpanString(
                date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        Log.d(TAG, readableFormat.toString());
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
