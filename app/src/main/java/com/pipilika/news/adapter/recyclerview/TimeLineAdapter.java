package com.pipilika.news.adapter.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pipilika.news.BR;
import com.pipilika.news.R;
import com.pipilika.news.item.recyclerview.TimeLine;
import com.pipilika.news.item.recyclerview.TimeLineDate;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private static final String TAG = TimeLineAdapter.class.getSimpleName();
    private List<TimeLine> timelineItems;
    private Activity activity;
    private LayoutInflater inflater;

    public TimeLineAdapter(Activity activity, TimeLineDate timeLineDate) {
        this.timelineItems = timeLineDate.getTimeLines();
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rootView = inflater.inflate(R.layout.list_item_timeline_date, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TimeLine timeLine = timelineItems.get(position);
        holder.getBinding().setVariable(BR.timeLine, timeLine);
        Log.d(TAG, timeLine.toString());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, timelineItems.size() + "");
        return timelineItems.size();
    }

    public TimeLine getItem(int position) {
        return timelineItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View convertView) {
            super(convertView);
            binding = DataBindingUtil.bind(convertView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
