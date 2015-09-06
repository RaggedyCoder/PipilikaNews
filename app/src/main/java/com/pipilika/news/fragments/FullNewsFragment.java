package com.pipilika.news.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;
import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.items.viewpager.ClusterPagerItem;
import com.pipilika.news.utils.BanglaTime;
import com.pipilika.news.utils.Constants;
import com.pipilika.news.view.widget.CustomTextView;
import com.pipilika.news.view.widget.NewsImageView;
import com.pipilika.news.view.widget.PaddingView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FullNewsFragment extends Fragment {
    private static final String LOCATION = "location";
    private static final String NEWS = "news";
    private static final String TAG = FullNewsFragment.class.getSimpleName();

    private String location;
    private ClusterPagerItem news;
    private CustomTextView category;
    private CustomTextView content;
    private ScrollView scrollView;
    private PaddingView paddingView;

    private NewsImageView imageView;
    private CustomTextView headline;
    private CustomTextView newsPaper;
    private CustomTextView newsTime;
    private ImageButton back;
    private View rootView;

    public FullNewsFragment() {
    }

    public static FullNewsFragment newInstance(String location, Parcelable news) {
        FullNewsFragment fragment = new FullNewsFragment();
        Bundle args = new Bundle();
        args.putString(LOCATION, location);
        args.putParcelable(NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = getArguments().getString(LOCATION);
            news = getArguments().getParcelable(NEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_full_news, container, false);
        if (checkCache()) {
            File file = new File(Constants.IMAGE_CACHE_PATH + location + ".png");
            imageView = (NewsImageView) rootView.findViewById(R.id.news_image);
            FileInputStream fi = null;
            try {
                fi = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fi);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView = (NewsImageView) rootView.findViewById(R.id.news_image);
            File file = new File(Constants.IMAGE_CACHE_PATH);
            file.mkdirs();
            imageView.setImageUrl(news.getImage(), "/" + location);
        }
        paddingView = (PaddingView) rootView.findViewById(R.id.padding_view);
        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
        headline = (CustomTextView) rootView.findViewById(R.id.news_headline);
        newsPaper = (CustomTextView) rootView.findViewById(R.id.news_paper_name);
        content = (CustomTextView) rootView.findViewById(R.id.news_content);
        category = (CustomTextView) rootView.findViewById(R.id.news_category);
        newsTime = (CustomTextView) rootView.findViewById(R.id.news_time);
        back = (ImageButton) rootView.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Log.e("TAG", "clicked");
            }
        });
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                OnScroll(scrollView.getScrollY());
            }
        });
        String temp = "";
        AppManager appManager = new AppManager(getActivity());
        for (int i = (appManager.getLatestNewsId() + "/").length();
             i < location.length(); i++) {
            if (location.charAt(i) == '/')
                break;
            temp += location.charAt(i);
        }
        category.setText(temp);
        headline.setText(news.getHeadline());
        content.setText(news.getContent());
        newsPaper.setText(news.getBanglaname());
        newsTime.setText(getReadableDate(news.getPublished_time()));
        return rootView;
    }

    private void OnScroll(int scrollY) {
        final int mMinHeaderTranslation = paddingView.getViewHeight();
        if (imageView != null) {
            ViewHelper.setTranslationY(imageView, -scrollY * 0.35f);
        } else {
            ViewHelper.setTranslationY(imageView, -scrollY * 0.35f);
        }
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

    private boolean checkCache() {
        File file = new File(Constants.IMAGE_CACHE_PATH + location + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Constants.IMAGE_CACHE_PATH + location + ".png");
        return file.exists();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
