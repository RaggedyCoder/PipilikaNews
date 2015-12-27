package com.pipilika.news.items;

import com.google.gson.annotations.SerializedName;
import com.pipilika.news.items.listview.ClusterListItem;

import java.util.List;

/**
 * Created by sajid on 10/20/2015.
 */
public class SingleClusterNews {

    @SerializedName("total_result")
    private int totalResult;
    private List<ClusterListItem> news;

    public SingleClusterNews() {
    }

    public SingleClusterNews(int totalResult, List<ClusterListItem> news) {
        this.totalResult = totalResult;
        this.news = news;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<ClusterListItem> getNews() {
        return news;
    }

    public void setNews(List<ClusterListItem> news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "SingleClusterNews{" +
                "totalResult=" + totalResult +
                ", news=" + news +
                '}';
    }
}
