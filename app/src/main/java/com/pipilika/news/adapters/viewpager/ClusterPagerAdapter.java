package com.pipilika.news.adapters.viewpager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.pipilika.news.R;
import com.pipilika.news.activities.FullNewsActivity;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.utils.Constants;
import com.pipilika.news.view.widget.CustomTextView;
import com.pipilika.news.view.widget.OnlineImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClusterPagerAdapter extends PagerAdapter {

    private static final String TAG = ClusterPagerAdapter.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private String category;
    private LayoutInflater inflater;
    private List<ClusterPagerItem> clusterPagerItems;
    private String zipId;
    private int positionInList;

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
        final View convertView;
        ClusterPagerItem clusterPagerItem = clusterPagerItems.get(position);
        if (checkCache(position)) {
            convertView = inflater.inflate(R.layout.cache_cluster_pager_item, null);
            ImageView onlineImageView = (ImageView) convertView.findViewById(R.id.news_image);
            File file = new File(Constants.IMAMGE_CACHE_PATH + zipId + "/" + category + "/" + "cluster" + positionInList + "/" + position + ".png");
            FileInputStream fi = null;
            try {
                fi = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fi);
            onlineImageView.setImageBitmap(bitmap);
        } else {
            convertView = inflater.inflate(R.layout.cluster_pager_item, null);
            OnlineImageView onlineImageView = (OnlineImageView) convertView.findViewById(R.id.news_image);
            onlineImageView.setDrawingCacheEnabled(true);
            onlineImageView.setImageUrl(clusterPagerItem.getImage(), imageLoader, zipId, category, positionInList, position);
        }

        CustomTextView headline = (CustomTextView) convertView.findViewById(R.id.news_headline);
        CustomTextView newsPaper = (CustomTextView) convertView.findViewById(R.id.news_paper_name);
        CustomTextView tagText = (CustomTextView) convertView.findViewById(R.id.news_category);
        CustomTextView newsSummary = (CustomTextView) convertView.findViewById(R.id.news_summary);
        CustomTextView newsTime = (CustomTextView) convertView.findViewById(R.id.news_time);

        headline.setText(clusterPagerItem.getHeadline());
        newsPaper.setText(clusterPagerItem.getBanglaname());
        newsSummary.setText(clusterPagerItem.getSummary().replace('\n', ' '));

        newsTime.setText(getReadableDate(clusterPagerItem.getPublished_time()));
        tagText.setText(category);
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
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        return readableFormat.format(date);


    }

    private boolean checkCache(int position) {
        File file = new File(Constants.IMAMGE_CACHE_PATH + zipId + "/" + category + "/" + "cluster" + positionInList + "/");
        Log.e(TAG, file.getAbsolutePath());
        if (!file.exists()) {
            Log.e(TAG, "true");
            file.mkdirs();
        }
        file = new File(Constants.IMAMGE_CACHE_PATH + zipId + "/" + category + "/" + "cluster" + positionInList + "/" + position + ".png");
        return file.exists();
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
