package com.pipilika.news.utils.graphics;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by tuman on 29/4/2015.
 */
public class RightTriangleF implements Parcelable {

    public PointF top;
    private float height;
    private float width;

    public RightTriangleF() {
        this(new PointF(0, 0), 0, 0);
    }

    public RightTriangleF(PointF top, float height, float width) {
        this.top = top;
        this.height = height;
        this.width = width;
    }

    public RightTriangleF(RightTriangle r) {
        if (r != null) {
            this.top.x = r.top.x;
            this.top.y = r.top.y;
            this.height = r.height;
            this.width = r.width;
        }
    }

    public RightTriangleF(RightTriangleF r) {
        top = r.top;
        height = r.height;
        width = r.width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RightTriangleF r = (RightTriangleF) o;
        return top == r.top && height == r.height && width == r.width;
    }

    @Override
    public int hashCode() {
        int result = (height != +0.0f ? Float.floatToIntBits(height) : 0);
        result = 31 * result + (top.x != +0.0f ? Float.floatToIntBits(top.x) : 0);
        result = 31 * result + (top.y != +0.0f ? Float.floatToIntBits(top.y) : 0);
        result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RightTriangleF{" +
                "top=" + top +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    /**
     * Returns true if the rectangle is empty (left >= right or top >= bottom)
     */
    public final boolean isEmpty() {
        return width <= 0 || height <= 0;
    }

    /**
     * Set the right triangle at (0,0) with height = 0 and width = 0
     */
    public void setEmpty() {

        top.x = top.y = height = width = 0;
    }

    public void offset(float dx, float dy) {
        top.x += dx;
        top.y += dy;
    }

    public void offsetTo(float newTopX, float newTopY) {
        top.x = newTopX;
        top.y = newTopY;
    }

    public void offsetTo(PointF newTop) {
        top = newTop;
    }

    public boolean contains(float x, float y) {
        return height != 0 && width != 0 // check for empty first
                && x <= top.x && x >= (top.x - width) && y >= top.y && y >= (top.y + height)
                && y < (((point(2).y - top.y) / (point(2).x - top.x)) * x + (((point(2).x - top.x) * top.x) - ((point(2).y - top.y) * top.x)) / (point(2).x - top.x));
    }

    public boolean contains(PointF p) {
        return contains(p.x, p.y);
    }

    public boolean contains(RectF r) {
        return contains(r.left, r.top) && contains(r.right, r.top)
                && contains(r.right, r.bottom) && contains(r.left, r.bottom);
    }

    public boolean contains(RightTriangleF r) {
        return contains(r.top) && contains(r.top.x, r.top.y + height)
                && contains(r.top.x - width, r.top.y + height);
    }

    // Given three colinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    private boolean onSegment(PointF p, PointF q, PointF r) {
        return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
    }

    private boolean intersect(PointF p1, PointF q1, PointF p2, PointF q2) {
        // Find the four orientations needed for general and
        // special cases
        float o1 = orientation(p1, q1, p2);
        float o2 = orientation(p1, q1, q2);
        float o3 = orientation(p2, q2, p1);
        float o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and p2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);

    }

    private boolean intersect(RectF r) {
        PointF rectLeftTop = new PointF(r.left, r.top);
        PointF rectRightTop = new PointF(r.right, r.top);
        PointF rectRightBottom = new PointF(r.right, r.bottom);
        PointF rectLeftBottom = new PointF(r.left, r.bottom);

        PointF rtTop = this.top;
        PointF rtBottomRight = point(1);
        PointF rtBottomLeft = point(2);
        return intersect(rectLeftTop, rectRightTop, rtTop, rtBottomRight)
                || intersect(rectLeftTop, rectRightTop, rtBottomRight, rtBottomLeft)
                || intersect(rectLeftTop, rectRightTop, rtBottomLeft, rtTop)

                || intersect(rectRightTop, rectRightBottom, rtTop, rtBottomRight)
                || intersect(rectRightTop, rectRightBottom, rtBottomRight, rtBottomLeft)
                || intersect(rectRightTop, rectRightBottom, rtBottomLeft, rtTop)

                || intersect(rectRightBottom, rectLeftBottom, rtTop, rtBottomRight)
                || intersect(rectRightBottom, rectLeftBottom, rtBottomRight, rtBottomLeft)
                || intersect(rectRightBottom, rectLeftBottom, rtBottomLeft, rtTop)

                || intersect(rectLeftBottom, rectLeftTop, rtTop, rtBottomRight)
                || intersect(rectLeftBottom, rectLeftTop, rtBottomRight, rtBottomLeft)
                || intersect(rectLeftBottom, rectLeftTop, rtBottomLeft, rtTop);
    }

    private float orientation(PointF p, PointF q, PointF r) {
        float val = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;  // colinear

        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    public PointF point(int number) {
        PointF[] points = new PointF[3];
        allPoints(points);
        return points[number];
    }


    public void allPoints(PointF[] points) {
        points[0] = top;
        points[1] = new PointF(top.x, top.y + height);
        points[2] = new PointF(top.x - width, points[1].y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

