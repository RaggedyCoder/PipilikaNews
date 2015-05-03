package com.pipilika.news.items.viewpager;

/**
 * Created by tuman on 20/4/2015.
 */
public class ClusterPagerItem extends PagerItem {
    private int id;
    private String headline;
    private String summary;
    private String image;
    private String content;
    private String published_time;
    private String newspaper;
    private String detail_url;

    public ClusterPagerItem() {

    }

    public ClusterPagerItem(int id, String headline, String summary, String image, String content,
                            String published_time, String newspaper, String detail_url) {
        this.id = id;
        this.headline = headline;
        this.summary = summary;
        this.image = image;
        this.content = content;
        this.published_time = published_time;
        this.newspaper = newspaper;
        this.detail_url = detail_url;
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

    public String getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(String newspaper) {
        this.newspaper = newspaper;
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
                ", newspaper='" + newspaper + '\'' +
                ", detail_url='" + detail_url + '\'' +
                '}';
    }
}


