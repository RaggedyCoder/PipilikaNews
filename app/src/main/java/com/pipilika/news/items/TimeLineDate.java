package com.pipilika.news.items;

import java.util.ArrayList;

/**
 * Created by sajid on 10/19/2015.
 */
public class TimeLineDate {

    private String date;

    private ArrayList<String> ids;

    public TimeLineDate() {
        this(null, null);
    }

    public TimeLineDate(String date, ArrayList<String> ids) {
        this.date = date;
        this.ids = ids;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "TimeLineDate{" +
                "date='" + date + '\'' +
                ", ids=" + ids +
                '}';
    }
}
