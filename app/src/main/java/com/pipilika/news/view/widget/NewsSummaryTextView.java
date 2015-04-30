package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.pipilika.news.R;
import com.pipilika.news.utils.graphics.RightTriangleF;

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
        setSummary(null);
    }

    public void setSummary(String string) {
        //  setText(string);
        if (tagTextView == null) {
            createTagTextView(tagTextID);
        }
        boolean change = false;
        Paint paint = getPaint();
        paint.setTextSize(TypedValue.
                applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
        int[] locationForTag = new int[2];
        tagTextView.getLocationInWindow(locationForTag);
        RightTriangleF rightTriangleF = new RightTriangleF(tagTextView.getmRightTriangleF());
        int[] locationForSummary = new int[2];
        this.getLocationInWindow(locationForSummary);
        Log.e("Location of summary", locationForSummary[0] + " " + locationForSummary[1]);
        Log.e("Line count", getLineCount() + "");
        StringBuilder stringBuilder = new StringBuilder(getText());
        for (int i = 0; i < getLineCount(); i++) {
            Log.e("line count-", " " + getLineCount());
            float length = getLayout().getLineWidth(i);
            float height = getLineHeight();
            RectF rectf = new RectF(this.getLayout().getLineLeft(i) + getTotalPaddingLeft(), this.getLayout().getLineTop(i), this.getLayout().getLineRight(i) + getTotalPaddingLeft(), this.getLayout().getLineBottom(i));
            rectf.offset(locationForSummary[0], locationForSummary[1]);
            rightTriangleF.offsetTo(locationForTag[0], locationForTag[1]);
            if (rightTriangleF.intersect(rectf)) {
                change = true;
                for (int j = getLayout().getLineEnd(i); j >= getLayout().getLineStart(i); j--) {
                    if (j != getText().length()) {
                        if (getText().charAt(j) == ' ') {
                            stringBuilder = new StringBuilder(getText().toString());
                            stringBuilder.setCharAt(j, '\n');
                            break;
                        }
                    }
                }
                Log.e("TAG", getText().toString());
            }
            Log.e("line-" + i, rightTriangleF.intersect(rectf) + " ");
        }
        if (change)
            setText(stringBuilder.toString());
        int count = 0;
        String strings = getText().toString();
        for (int i = 0; i < strings.length(); i++) {
            if (strings.charAt(i) == '\n') {
                count++;
            }
        }
        Log.e("count ", strings + " " + count);

    }

    public TagTextView getTagTextView() {
        return tagTextView;
    }

    public void setTagTextView(TagTextView tagTextView) {
        this.tagTextView = tagTextView;
    }
}
