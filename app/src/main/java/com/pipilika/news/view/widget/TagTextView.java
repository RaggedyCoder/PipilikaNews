package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import com.pipilika.news.R;
import com.pipilika.news.utils.graphics.RightTriangleF;

/**
 * Created by tuman on 21/4/2015.
 */
public class TagTextView extends CustomTextView {
    private int tagColor;
    private boolean mirror;
    private RightTriangleF mRightTriangleF;

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        mirror = a.getBoolean(R.styleable.TagTextView_mirror, false);
        a.recycle();
        mRightTriangleF = new RightTriangleF(new PointF(getWidth(), 0), getHeight(), getWidth(), mirror);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        a.recycle();
        mRightTriangleF = new RightTriangleF(new PointF(getWidth(), 0), getHeight(), getWidth(), mirror);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        a.recycle();
        mRightTriangleF = new RightTriangleF(new PointF(getWidth(), 0), getHeight(), getWidth(), mirror);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        super.onDraw(canvas);
    }

    private void drawBackground(Canvas canvas) {

        int[] location = new int[2];
        getLocationOnScreen(location);
        Log.e("Location of own", location[0] + " " + location[1]);
        mRightTriangleF = new RightTriangleF(new PointF(getWidth(), 0), getHeight(), getWidth(), mirror);
        PointF[] points = new PointF[3];
        Log.e("TAG", mRightTriangleF.height + "");
        mRightTriangleF.allPoints(points);
        Path path = new Path();
        path.moveTo(points[0].x, points[0].y);
        path.lineTo(points[1].x, points[1].y);
        path.lineTo(points[2].x, points[2].y);
        path.lineTo(points[0].x, points[0].y);
        path.close();
        Paint paint = getPaint();
        paint.setColor(tagColor);
        canvas.drawPath(path, paint);
    }

    public RightTriangleF getmRightTriangleF() {
        return mRightTriangleF;
    }


    public void setmRightTriangleF(RightTriangleF mRightTriangleF) {
        this.mRightTriangleF = mRightTriangleF;
    }
}
