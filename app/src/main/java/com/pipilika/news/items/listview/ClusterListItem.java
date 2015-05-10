package com.pipilika.news.items.listview;

import com.pipilika.news.items.viewpager.ClusterPagerItem;

import java.util.List;

/**
 * Created by tuman on 21/4/2015.
 */
public class ClusterListItem extends ListItem {

    private String category;
    private List<ClusterPagerItem> news;

    public ClusterListItem() {
        this(null, null);
    }

    public ClusterListItem(String category, List<ClusterPagerItem> news) {
        this.category = category;
        this.news = news;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
                "category='" + category + '\'' +
                ", news=" + news +
                '}';
    }
}
