package com.pipilika.news.items.listview;

import com.pipilika.news.items.viewpager.ClusterPagerItem;

import java.util.List;

/**
 * Created by tuman on 21/4/2015.
 */
public class ClusterListItem extends ListItem {

    private int total;
    private String category;
    private String next_url;
    private String previous_url;
    private List<ClusterPagerItem> news;

    public ClusterListItem() {
        this(0, null, null, null, null);
    }

    public ClusterListItem(int total, String category, String next_url, String previous_url, List<ClusterPagerItem> news) {
        this.total = total;
        this.category = category;
        this.next_url = next_url;
        this.previous_url = previous_url;
        this.news = news;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNext_url() {
        return next_url;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }

    public String getPrevious_url() {
        return previous_url;
    }

    public void setPrevious_url(String previous_url) {
        this.previous_url = previous_url;
    }

    public List<ClusterPagerItem> getNews() {
        return news;
    }

    public void setNews(List<ClusterPagerItem> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "ClusterListItem{" +
                "total=" + total +
                ", category='" + category + '\'' +
                ", next_url='" + next_url + '\'' +
                ", previous_url='" + previous_url + '\'' +
                ", news=" + news +
                '}';
    }
}
