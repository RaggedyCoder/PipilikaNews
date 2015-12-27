package com.pipilika.news.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.pipilika.news.BR;
import com.pipilika.news.R;
import com.pipilika.news.items.viewpager.ClusterPagerItem;

public class FullNewsFragment extends Fragment {
    private static final String LOCATION = "location";
    private static final String NEWS = "news";
    private static final String TAG = FullNewsFragment.class.getSimpleName();

    private String location;
    private ClusterPagerItem news;
    private ScrollView scrollView;
    private ImageButton back;
    private View rootView;
    private View newsHeadlineImageContainer;
    private ViewDataBinding binding;

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
        rootView = inflater.inflate(R.layout.fragment_full_news, container, false);
        binding = DataBindingUtil.bind(rootView);
        binding.setVariable(BR.news, news);
        newsHeadlineImageContainer = rootView.findViewById(R.id.news_headline_image_container);
        scrollView = (ScrollView) rootView.findViewById(R.id.scroll_view);
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
        return rootView;
    }

    private void OnScroll(int scrollY) {
        if (newsHeadlineImageContainer != null) {
            newsHeadlineImageContainer.setTranslationY(-scrollY * 0.35f);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        back.setOnClickListener(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
