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
import com.pipilika.news.item.recyclerview.AboutItem;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    private static final String TAG = AboutAdapter.class.getSimpleName();
    private List<AboutItem> aboutItems;
    private Activity activity;
    private LayoutInflater inflater;

    public AboutAdapter(Activity activity, List<AboutItem> aboutItems) {
        this.activity = activity;
        this.aboutItems = aboutItems;
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_item_about, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AboutItem aboutItem = aboutItems.get(position);
        holder.getBinding().setVariable(BR.aboutItem, aboutItem);
        Log.d(TAG, aboutItem.toString());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, aboutItems.size() + "");
        return aboutItems.size();
    }

    public AboutItem getItem(int position) {
        return aboutItems.get(position);
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
