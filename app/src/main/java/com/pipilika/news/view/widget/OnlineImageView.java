package com.pipilika.news.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.pipilika.news.R;
import com.pipilika.news.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by tuman on 23/4/2015.
 */
public class OnlineImageView extends ImageView {

    private int tagTextID;
    private ProgressBar view;
    private ResponseObserver mObserver;
    /**
     * The URL of the network image to load
     */
    private String mUrl;
    /**
     * Resource ID of the image to be used as a placeholder until the network
     * image is loaded.
     */
    private int mDefaultImageId;
    /**
     * Resource ID of the image to be used if the network response fails.
     */
    private int mErrorImageId;
    /**
     * Local copy of the ImageLoader.
     */
    private ImageLoader mImageLoader;
    /**
     * Current ImageContainer. (either in-flight or finished)
     */
    private ImageContainer mImageContainer;

    private String mZipId;
    private String mCategory;
    private String mLocation;
    private int mPositionInList;
    private int mPosition;

    public OnlineImageView(Context context) {
        this(context, null);
    }

    public OnlineImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineImageView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OnlineImageView);
        tagTextID = a.getResourceId(R.styleable.OnlineImageView_loaderViewId, 0);
        view = (ProgressBar) getRootView().findViewById(tagTextID);
        a.recycle();

    }

    public void setResponseObserver(ResponseObserver observer) {
        mObserver = observer;
    }


    /**
     * Sets the default image resource ID to be used for this view until the
     * attempt to load it completes.
     */
    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    /**
     * Sets the error image resource ID to be used for this view in the event
     * that the image requested fails to load.
     */
    public void setErrorImageResId(int errorImage) {
        mErrorImageId = errorImage;
    }

    /**
     * Loads the image for the view if it isn't already loaded.
     *
     * @param isInLayoutPass True if this was invoked from a layout pass, false otherwise.
     */
    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        final int width = getWidth();
        int height = getHeight();

        boolean isFullyWrapContent = getLayoutParams() != null
                && getLayoutParams().height == LayoutParams.WRAP_CONTENT
                && getLayoutParams().width == LayoutParams.WRAP_CONTENT;
        // if the view's bounds aren't known yet, and this is not a
        // wrap-content/wrap-content
        // view, hold off on loading the image.
        if (width == 0 && height == 0 && !isFullyWrapContent) {
            return;
        }

        // if the URL to be loaded in this view is empty, cancel any old
        // requests and clear the
        // currently loaded image.
        if (TextUtils.isEmpty(mUrl)) {
            if (mImageContainer != null) {
                mImageContainer.cancelRequest();
                mImageContainer = null;
            }
            setDefaultImageOrNull();
            return;
        }

        // if there was an old request in this view, check if it needs to be
        // canceled.
        if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
            if (mImageContainer.getRequestUrl().equals(mUrl)) {
                // if the request is from the same URL, return.
                return;
            } else {
                // if there is a pre-existing request, cancel it if it's
                // fetching a different URL.
                mImageContainer.cancelRequest();
                setDefaultImageOrNull();
            }
        }

        // The pre-existing content of this view didn't match the current URL.
        // Load the new image
        // from the network.
        ImageContainer newContainer = mImageLoader.get(mUrl,
                new ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mErrorImageId != 0) {
                            setImageResource(mErrorImageId);
                        }

                        if (mObserver != null) {
                            mObserver.onError();
                        }
                    }

                    @Override
                    public void onResponse(final ImageContainer response,
                                           boolean isImmediate) {
                        // If this was an immediate response that was delivered
                        // inside of a layout
                        // pass do not set the image immediately as it will
                        // trigger a requestLayout
                        // inside of a layout. Instead, defer setting the image
                        // by posting back to
                        // the main thread.
                        if (isImmediate && isInLayoutPass) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                            return;
                        }

                        int bWidth = 0, bHeight = 0;
                        if (response.getBitmap() != null) {

                            setImageBitmap(response.getBitmap());
                            setBackgroundResource(R.drawable.background);
                            //bWidth = response.getBitmap().getWidth();
                            //bHeight = response.getBitmap().getHeight();
                            //adjustImageAspect(bWidth, bHeight);
                            File file = new File(Constants.IMAMGE_CACHE_PATH + mLocation + ".png");
                            Log.e("TAG", file.getAbsolutePath());
                            FileOutputStream outStream = null;
                            try {
                                outStream = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            response.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outStream);

                        } else if (mDefaultImageId != 0) {
                            setImageResource(mDefaultImageId);
                        }
                        if (mObserver != null) {
                            mObserver.onSuccess();
                        }
                        if (view == null) {
                            view = (ProgressBar) getRootView().findViewById(tagTextID);
                        }
                        view.setVisibility(GONE);
                    }
                });

        // update the ImageContainer to be the new bitmap container.
        mImageContainer = newContainer;

    }

    private void setDefaultImageOrNull() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mImageContainer != null) {
            // If the view was bound to an image request, cancel it and clear
            // out the image from the view.
            mImageContainer.cancelRequest();
            setImageBitmap(null);
            // also clear out the container so we can reload the image if
            // necessary.
            mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    public void setImageUrl(String url, ImageLoader imageLoader, String zipId, String category, int positionInList, int position) {
        mUrl = url;
        mCategory = category;
        mPositionInList = positionInList;
        mPosition = position;
        mZipId = zipId;
        Log.e("TAG", mUrl);
        mLocation = mZipId + "/" + mCategory + "/" + "cluster" + mPositionInList + "/" + mPosition;
        mImageLoader = imageLoader;
        File file = new File(Constants.IMAMGE_CACHE_PATH + mZipId + "/" + mCategory + "/" + "cluster" + mPositionInList + "/");
        Log.e("TAG", file.getAbsolutePath());
        file.mkdirs();
        loadImageIfNecessary(false);
    }

    public void setImageUrl(String url, ImageLoader imageLoader, String location) {
        mUrl = url;
        mLocation = location;
        mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }


    public interface ResponseObserver {
        void onError();

        void onSuccess();
    }
}