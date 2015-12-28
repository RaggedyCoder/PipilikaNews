package com.pipilika.news.item.viewpager;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ClusterPagerItem extends PagerItem implements Parcelable {
    public static final Parcelable.Creator<ClusterPagerItem> CREATOR = new Parcelable.Creator<ClusterPagerItem>() {
        public ClusterPagerItem createFromParcel(Parcel in) {
            return new ClusterPagerItem(in);
        }

        public ClusterPagerItem[] newArray(int size) {
            return new ClusterPagerItem[size];
        }
    };
    private String headline;
    private String summary;
    @SerializedName("published_time")
    private String publishedTime;
    private String content;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("papername")
    private String paperName;
    @SerializedName("banglaname")
    private String paperNameBangla;
    private String url;

    public ClusterPagerItem() {
        this(null, null, null, null, null, null, null, null);

    }

    public ClusterPagerItem(String headline, String summary, String publishedTime, String content, String imageUrl, String paperName, String paperNameBangla, String url) {
        this.headline = headline;
        this.summary = summary;
        this.publishedTime = publishedTime;
        this.content = content;
        this.imageUrl = imageUrl;
        this.paperName = paperName;
        this.paperNameBangla = paperNameBangla;
        this.url = url;
    }

    public ClusterPagerItem(Parcel in) {
        setHeadline(in.readString());
        setSummary(in.readString());
        setPublishedTime(in.readString());
        setContent(in.readString());
        setImageUrl(in.readString());
        setPaperName(in.readString());
        setPaperNameBangla(in.readString());
        setUrl(in.readString());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaperNameBangla() {
        return paperNameBangla;
    }

    public void setPaperNameBangla(String paperNameBangla) {
        this.paperNameBangla = paperNameBangla;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getHeadline());
        dest.writeString(this.getSummary());
        dest.writeString(this.getPublishedTime());
        dest.writeString(this.getContent());
        dest.writeString(this.getImageUrl());
        dest.writeString(this.getPaperName());
        dest.writeString(this.getPaperNameBangla());
        dest.writeString(this.getUrl());
    }
}


