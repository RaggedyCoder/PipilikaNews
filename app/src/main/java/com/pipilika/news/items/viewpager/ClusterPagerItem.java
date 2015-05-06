package com.pipilika.news.items.viewpager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tuman on 20/4/2015.
 */
public class ClusterPagerItem extends PagerItem implements Parcelable {
    public static final Parcelable.Creator<ClusterPagerItem> CREATOR = new Parcelable.Creator<ClusterPagerItem>() {
        public ClusterPagerItem createFromParcel(Parcel in) {
            return new ClusterPagerItem(in);
        }

        public ClusterPagerItem[] newArray(int size) {
            return new ClusterPagerItem[size];
        }
    };
    private int id;
    private String headline;
    private String summary;
    private String image;
    private String content;
    private String published_time;
    private String banglaname;
    private String detail_url;

    public ClusterPagerItem() {

    }

    public ClusterPagerItem(int id, String headline, String summary, String image, String content,
                            String published_time, String banglaname, String detail_url) {
        this.id = id;
        this.headline = headline;
        this.summary = summary;
        this.image = image;
        this.content = content;
        this.published_time = published_time;
        this.banglaname = banglaname;
        this.detail_url = detail_url;
    }

    public ClusterPagerItem(Parcel in) {
        this.id = in.readInt();
        this.headline = in.readString();
        this.summary = in.readString();
        this.image = in.readString();
        this.content = in.readString();
        this.published_time = in.readString();
        this.banglaname = in.readString();
        this.detail_url = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublished_time() {
        return published_time;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }

    public String getBanglaname() {
        return banglaname;
    }

    public void setBanglaname(String banglaname) {
        this.banglaname = banglaname;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ClusterPagerItem{" +
                "id=" + id +
                ", headline='" + headline + '\'' +
                ", summary='" + summary + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", published_time='" + published_time + '\'' +
                ", banglaname='" + banglaname + '\'' +
                ", detail_url='" + detail_url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId());
        dest.writeString(this.getHeadline());
        dest.writeString(this.getSummary());
        dest.writeString(this.getImage());
        dest.writeString(this.getContent());
        dest.writeString(this.getPublished_time());
        dest.writeString(this.getBanglaname());
        dest.writeString(this.getDetail_url());
    }
}


