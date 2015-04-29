package com.pipilika.news.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.pipilika.news.R;

/**
 * Created by tuman on 21/4/2015.
 */
public class TagTextView extends TextView {
    private int tagColor;
    private RectF mRectF;

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        a.recycle();
        mRectF = new RectF();
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        a.recycle();
        mRectF = new RectF();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        tagColor = a.getColor(R.styleable.TagTextView_tagBackgroundColor, 0);
        a.recycle();
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        super.onDraw(canvas);
    }

    private void drawBackground(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        Point a = new Point(0, height);
        Point b = new Point(width, height);
        Point c = new Point(width, 0);
        Point d = new Point(0, height);

        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.close();
        path.computeBounds(mRectF, true);
        Paint paint = getPaint();
        paint.setColor(tagColor);
        canvas.drawPath(path, getPaint());
    }
}
