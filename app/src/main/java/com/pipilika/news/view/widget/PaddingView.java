package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pipilika.news.R;

/**
 * Created by tuman on 13/5/2015.
 */
public class PaddingView extends View {

    private View mView;
    private int viewID;

    public PaddingView(Context context) {
        super(context);
    }

    public PaddingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PaddingView);
        viewID = a.getResourceId(R.styleable.PaddingView_viewId, 0);
        mView = getRootView().findViewById(viewID);
        a.recycle();
    }

    public PaddingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PaddingView);
        viewID = a.getResourceId(R.styleable.PaddingView_viewId, 0);
        mView = getRootView().findViewById(viewID);
        a.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaddingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mView == null) {
            mView = getRootView().findViewById(viewID);
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mView.getHeight(), MeasureSpec.EXACTLY);
        Log.e("height", "" + mView.getHeight());
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
