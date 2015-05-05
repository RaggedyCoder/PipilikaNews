package com.pipilika.news.adapters.viewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.pipilika.news.R;
import com.pipilika.news.application.AppController;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.view.widget.CustomTextView;
import com.pipilika.news.view.widget.NewsSummaryTextView;
import com.pipilika.news.view.widget.OnlineImageView;
import com.pipilika.news.view.widget.TagTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tuman on 20/4/2015.
 */
public class ClusterPagerAdapter extends PagerAdapter {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ViewPager clusterPager;
    private String category;
    private LayoutInflater inflater;
    private List<ClusterPagerItem> clusterPagerItems;

    private Activity activity;

    public ClusterPagerAdapter(Activity activity, List<ClusterPagerItem> clusterPagerItems, String category) {
        super();
        this.activity = activity;
        this.clusterPagerItems = clusterPagerItems;
        Log.e("TAG", "" + clusterPagerItems.size());
        this.category = category;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = inflater.inflate(R.layout.cluster_pager_item, null);
        OnlineImageView onlineImageView = (OnlineImageView) view.findViewById(R.id.news_image);
        onlineImageView.setImageUrl(clusterPagerItems.get(position).getImage(), imageLoader);
        CustomTextView headline = (CustomTextView) view.findViewById(R.id.news_headline);
        headline.setText(clusterPagerItems.get(position).getHeadline());
        CustomTextView newsPaper = (CustomTextView) view.findViewById(R.id.news_paper_name);
        newsPaper.setText(clusterPagerItems.get(position).getBanglaname());
        TagTextView tagText = (TagTextView) view.findViewById(R.id.news_category);
        CustomTextView newsTime = (CustomTextView) view.findViewById(R.id.news_time);
        final NewsSummaryTextView newsSummary = (NewsSummaryTextView) view.findViewById(R.id.news_summary);
        newsSummary.setSummary(clusterPagerItems.get(position).getSummary().replace('\n', ' '));
        Log.e("pager view-", "" + view.getWidth());
        newsSummary.setTagTextView(tagText);
        newsSummary.post(new Runnable() {
            @Override
            public void run() {
                Log.e("Line count", newsSummary.getLineCount() + "");
            }
        });
        SimpleDateFormat nonReadableFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = new Date();
        try {
            date = nonReadableFormat.parse(clusterPagerItems.get(position).getPublished_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        newsTime.setText(readableFormat.format(date));
        tagText.setText(category);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsSummary.setSummary(clusterPagerItems.get(position).getSummary());
                Toast.makeText(activity, "pressed-" + newsSummary.getLineCount(), Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view, 0);
        container.setMinimumHeight(view.getHeight());
        ((LinearLayout) view).setGravity(Gravity.CENTER);
        return view;
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
