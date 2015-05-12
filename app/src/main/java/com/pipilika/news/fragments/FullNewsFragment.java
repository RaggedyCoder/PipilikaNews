package com.pipilika.news.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.pipilika.news.R;
import com.pipilika.news.appdata.AppManager;
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

public class FullNewsFragment extends Fragment {
    private static final String LOCATION = "location";
    private static final String NEWS = "news";
    private static final String TAG = FullNewsFragment.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private String location;
    private ClusterPagerItem news;
    private CustomTextView category;
    private CustomTextView content;

    private ImageView imageView;
    private OnlineImageView onlineImageView;
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
        if (checkCache()) {
            File file = new File(Constants.IMAMGE_CACHE_PATH + location + ".png");
            rootView = inflater.inflate(R.layout.fragment_full_news, container, false);
            imageView = (ImageView) rootView.findViewById(R.id.news_image);
            FileInputStream fi = null;
            try {
                fi = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                Log.e(TAG, e.getMessage());
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fi);
            imageView.setImageBitmap(bitmap);
        } else {
            rootView = inflater.inflate(R.layout.fragment_full_news_download, container, false);
            onlineImageView = (OnlineImageView) rootView.findViewById(R.id.news_image);
            File file = new File(Constants.IMAMGE_CACHE_PATH);
            file.mkdirs();
            onlineImageView.setImageUrl(news.getImage(), imageLoader, "/" + location);
        }
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

    private String getReadableDate(String published_time) {
        SimpleDateFormat nonReadableFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = new Date();
        try {
            date = nonReadableFormat.parse(published_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat readableFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        return readableFormat.format(date);
    }

    private boolean checkCache() {
        File file = new File(Constants.IMAMGE_CACHE_PATH + location + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(Constants.IMAMGE_CACHE_PATH + location + ".png");
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
