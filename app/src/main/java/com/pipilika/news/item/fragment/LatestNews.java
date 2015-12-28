package com.pipilika.news.item.fragment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sajid on 10/19/2015.
 */
public class LatestNews {


    private boolean status;

    @SerializedName("latest_id")
    private String latestId;

    public LatestNews() {
        this(false, null);
    }

    public LatestNews(boolean status, String latestId) {
        this.status = status;
        this.latestId = latestId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLatestId() {
        return latestId;
    }


    public void setLatestId(String latestId) {
        this.latestId = latestId;
    }

    @Override
    public String toString() {
        return "LatestNews{" +
                "status=" + status +
                ", latestId=" + latestId +
                '}';
    }

}
