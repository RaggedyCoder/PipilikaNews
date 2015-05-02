package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pipilika.news.R;

/**
 * Created by tuman on 29/4/2015.
 */
public class NewsSummaryTextView extends TextView {
    private TagTextView tagTextView;
    private int tagTextID;

    public NewsSummaryTextView(Context context) {
        this(context, null);
    }

    public NewsSummaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsSummaryTextView);
        tagTextID = a.getResourceId(R.styleable.NewsSummaryTextView_tagViewId, 0);
        tagTextView = (TagTextView) getRootView().findViewById(tagTextID);
        a.recycle();
    }

    public NewsSummaryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsSummaryTextView);
        tagTextID = a.getResourceId(R.styleable.NewsSummaryTextView_tagViewId, 0);
        tagTextView = (TagTextView) getRootView().findViewById(tagTextID);
        a.recycle();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewsSummaryTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NewsSummaryTextView);
        tagTextID = a.getResourceId(R.styleable.NewsSummaryTextView_tagViewId, 0);
        tagTextView = (TagTextView) getRootView().findViewById(tagTextID);
        a.recycle();
    }

    private void createTagTextView(int id) {
        tagTextView = (TagTextView) getRootView().findViewById(tagTextID);
    }

    @Override
    protected void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setSummary(String string) {
        setText(string);
        if (tagTextView == null) {
            createTagTextView(tagTextID);
        }

    }

    public TagTextView getTagTextView() {
        return tagTextView;
    }

    public void setTagTextView(TagTextView tagTextView) {
        this.tagTextView = tagTextView;
    }
}
