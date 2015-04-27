package com.pipilika.news.items;

import com.pipilika.news.items.listview.ClusterListItem;

import java.util.List;

/**
 * Created by tuman on 23/4/2015.
 */
public class AllNewsItem {

    private int total;
    private List<ClusterListItem> clusters;

    public AllNewsItem(int total, List<ClusterListItem> clusters) {
        this.total = total;
        this.clusters = clusters;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClusterListItem> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterListItem> clusters) {
        this.clusters = clusters;
    }

    @Override
    public String toString() {
        return "AllNewsItem{" +
                "total=" + total +
                ", clusters=" + clusters +
                '}';
    }
}
