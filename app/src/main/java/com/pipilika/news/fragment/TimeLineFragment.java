package com.pipilika.news.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.pipilika.news.R;
import com.pipilika.news.activity.MainActivity;
import com.pipilika.news.adapter.recyclerview.TimeLineAdapter;
import com.pipilika.news.appdata.AppManager;
import com.pipilika.news.application.AppController;
import com.pipilika.news.item.fragment.SingleClusterNews;
import com.pipilika.news.item.recyclerview.ClusterListItem;
import com.pipilika.news.item.recyclerview.TimeLineDate;
import com.pipilika.news.util.Utils;
import com.pipilika.news.util.volley.ObjectRequest;
import com.pipilika.news.view.widget.CustomButton;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.pipilika.news.util.Constants.API_RECENT_CLUSTER_NEWS;
import static com.pipilika.news.util.Constants.API_SINGLE_CLUSTER_NEWS_;
import static com.pipilika.news.util.Constants.API_URL;
import static com.pipilika.news.util.Utils.newsFileName;

/**
 * Created by sajid on 11/9/2015.
 */
public class TimeLineFragment extends Fragment implements View.OnClickListener, Response.Listener<TimeLineDate>, Response.ErrorListener, RecyclerView.OnItemTouchListener {


    private static final String TAG = TimeLineFragment.class.getSimpleName();
    List<String> timelineDates;
    String date;
    private View rootView;
    private RecyclerView timelineClusterRecyclerView;
    private CustomButton timelineGetButton;
    private DatePicker timelineDatePicker;
    private ProgressBar progressWheel;
    private LinearLayoutManager linearLayoutManager;
    private TimeLineAdapter timeLineAdapter;
    private GestureDetector mGestureDetector;
    private String id;
    private int pageID = 0;
    private List<ClusterListItem> news;
    private ProgressDialog progressDialog;
    private SimpleDateFormat simpleDateFormat;
    private AppManager appManager = AppController.getAppManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
        }
        timelineClusterRecyclerView = findViewById(R.id.timeline_cluster_recycler_view);
        mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });
        timelineClusterRecyclerView.addOnItemTouchListener(this);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        timelineClusterRecyclerView.setLayoutManager(linearLayoutManager);
        timelineClusterRecyclerView.setHasFixedSize(true);
        timelineGetButton = findViewById(R.id.timeline_get_button);
        timelineDatePicker = findViewById(appManager.getAppThemeMode() == 0 ? R.id.timeline_date_picker_normal : R.id.timeline_date_picker_night);
        timelineDatePicker.setVisibility(View.VISIBLE);
        if (!Utils.hasLollipop()) {
            if (timelineDatePicker.getCalendarView() != null) {
                timelineDatePicker.getCalendarView().setShowWeekNumber(false);
            }
        }
        progressWheel = findViewById(appManager.getAppThemeMode() == 0 ? R.id.progress_wheel : R.id.progress_wheel_night);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        timelineGetButton.setOnClickListener(this);
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T findViewById(int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeline_get_button:

                Calendar calendar = new GregorianCalendar();
                Calendar selectedDate = new GregorianCalendar(timelineDatePicker.getYear(),
                        timelineDatePicker.getMonth(), timelineDatePicker.getDayOfMonth());
                if (isOldDate(calendar, selectedDate)) return;

                progressWheel.setVisibility(View.VISIBLE);
                timelineClusterRecyclerView.setVisibility(View.GONE);
                date = timelineDatePicker.getYear() + "-" +
                        timelineDatePicker.getMonth() + "-" + timelineDatePicker.getDayOfMonth();
                String url = API_URL + API_RECENT_CLUSTER_NEWS + "?date=" + date;
                ObjectRequest<TimeLineDate> timeLineDateObjectRequest = new ObjectRequest<>(Request.Method.GET, url, this, this, TimeLineDate.class);
                AppController.getInstance().addToRequestQueue(timeLineDateObjectRequest);
                Log.d(TAG, url);
                break;
        }
    }

    private boolean isOldDate(Calendar calendar, Calendar selectedDate) {
        if (calendar.get(Calendar.YEAR) < selectedDate.get(Calendar.YEAR)) {
            Toast.makeText(getContext(), "অবশ্যই পুর্বর্তী তারিখ সিলেক্ট করতে হবে", Toast.LENGTH_SHORT).show();
            return true;
        } else if (calendar.get(Calendar.YEAR) == selectedDate.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
                Toast.makeText(getContext(), "অবশ্যই পুর্বর্তী তারিখ সিলেক্ট করতে হবে", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    //,Response.Listener<SingleClusterNews>
    @Override
    public void onResponse(TimeLineDate timeLineDate) {
        Log.d(TAG, timeLineDate.toString());
        Collections.sort(timeLineDate.getDates(), new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {

                try {
                    Date leftDate = simpleDateFormat.parse(lhs);
                    Date rightDate = simpleDateFormat.parse(rhs);
                    return leftDate.compareTo(rightDate);
                } catch (Exception e) {

                }
                return lhs.compareTo(rhs);
            }
        });
        timeLineDate.setTimeLines(timeLineDate.getDates());
        timelineDates = timeLineDate.getDates();
        timeLineAdapter = new TimeLineAdapter(getActivity(), timeLineDate);
        timelineClusterRecyclerView.setAdapter(timeLineAdapter);
        timelineClusterRecyclerView.setVisibility(View.VISIBLE);
        progressWheel.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu)");
        menu.removeItem(R.id.action_timeline_news);
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            int position = recyclerView.getChildAdapterPosition(child);
            //yyyy-MM-dd-HH

            id = date + "-" +
                    timeLineAdapter.getItem(position).getHour();
            Log.e("TAG", id);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.downloading_news_content));
            progressDialog.setCancelable(false);
            progressDialog.show();
            downloadNews(id, true);
        }
        return false;
    }

    private void newClusterSave(String id, List<ClusterListItem> news) {
        File file = new File(newsFileName(id));
        byte[] bytes = new GsonBuilder().create().toJson(news).getBytes();
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 1024);
            write(outputStream, bytes);
        } catch (IOException e) {
            // Log.e(TAG, e.getMessage());
        }
    }

    private void write(OutputStream outputStream, byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }


    private void downloadNews(final String id, boolean isNew) {
        if (isNew) {
            news = new ArrayList<>();
            pageID = 0;
        }
        pageID++;
        String url = API_URL + API_SINGLE_CLUSTER_NEWS_ + "?clusterID=" + id + "&pageID=" + pageID;
        ObjectRequest<SingleClusterNews> singleClusterNewsObjectRequest = new ObjectRequest<>(Request.Method.GET, url, new Response.Listener<SingleClusterNews>() {
            @Override
            public void onResponse(SingleClusterNews singleClusterNews) {
                Log.e("TAG", "" + pageID);
                news.addAll(singleClusterNews.getNews());
                if ((pageID * 10) < singleClusterNews.getTotalResult()) {
                    downloadNews(id, false);
                } else {
                    singleClusterNews.setNews(news);
                    newClusterSave(id, singleClusterNews.getNews());
                    progressDialog.cancel();
                    MainActivity activity = (MainActivity) getActivity();
                    activity.onFragmentChange(NewsClusterFragment.newInstance(id));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.cancel();
                Toast.makeText(getContext(), "সায়মিক গোলযোগ দেখা দিয়েছে।পুনরায় চেষ্টা করুন।", Toast.LENGTH_SHORT).show();
            }
        }, SingleClusterNews.class);
        AppController.getInstance().addToRequestQueue(singleClusterNewsObjectRequest);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
