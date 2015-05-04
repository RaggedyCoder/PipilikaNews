package com.pipilika.news.database.data.item;

/**
 * Created by tuman on 4/5/2015.
 */
public class ZipDataItem implements Item {
    private String id;
    private int latestOne;

    public ZipDataItem() {
        this(null, 0);
    }

    public ZipDataItem(String id, int latestOne) {
        this.id = id;
        this.latestOne = latestOne;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLatestOne() {
        return latestOne;
    }

    public void setLatestOne(int latestOne) {
        this.latestOne = latestOne;
    }

    @Override
    public String toString() {
        return "ZipDataItem{" +
                "id='" + id + '\'' +
                ", latestOne=" + latestOne +
                '}';
    }
}
