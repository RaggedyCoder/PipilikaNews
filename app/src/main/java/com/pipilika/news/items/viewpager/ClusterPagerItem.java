package com.pipilika.news.items.viewpager;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String published_time;
    private String content;
    private String image;
    private String papername;
    private String banglaname;
    private String url;

    public ClusterPagerItem() {
        this(null, null, null, null, null, null, null, null);

    }

    public ClusterPagerItem(String headline, String summary, String published_time, String content, String image, String papername, String banglaname, String url) {
        this.headline = headline;
        this.summary = summary;
        this.published_time = published_time;
        this.content = content;
        this.image = image;
        this.papername = papername;
        this.banglaname = banglaname;
        this.url = url;
    }

    public ClusterPagerItem(Parcel in) {
        headline = in.readString();
        summary = in.readString();
        published_time = in.readString();
        content = in.readString();
        image = in.readString();
        papername = in.readString();
        banglaname = in.readString();
        url = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanglaname() {
        return banglaname;
    }

    public void setBanglaname(String banglaname) {
        this.banglaname = banglaname;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
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
        dest.writeString(this.getPublished_time());
        dest.writeString(this.getContent());
        dest.writeString(this.getImage());
        dest.writeString(this.getPapername());
        dest.writeString(this.getBanglaname());
        dest.writeString(this.getUrl());
    }
}


