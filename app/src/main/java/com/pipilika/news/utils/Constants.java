package com.pipilika.news.utils;

import android.os.Environment;

/**
 * Created by tuman on 6/5/2015.
 */
public interface Constants {
    String API_URL = "http://pipilika.com:32804/RecentNewsClusterEngine/";
    String API_GET_LATEST_NEWS = "GetLatestNews";
    String API_RECENT_CLUSTER_NEWS = "RecentClusterRequest";
    String API_SINGLE_CLUSTER_NEWS_ = "SingleClusterRequest";
    String API_TRANSFER_ZIPPED_NEWS = "TransferZipFile";
    String ZIP_CACHE_PATH = Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/clusters/";
    String IMAGE_CACHE_PATH = Environment.getExternalStorageDirectory() + "/Android/data/com.pipilika.news/images";

}
