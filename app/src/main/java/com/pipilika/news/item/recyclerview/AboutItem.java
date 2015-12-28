package com.pipilika.news.item.recyclerview;

/**
 * Created by sajid on 12/28/2015.
 */


public class AboutItem {

    private String title;

    public AboutItem() {
    }

    public AboutItem(String title) {

        this.title = title;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AboutItem)) return false;

        AboutItem aboutItem = (AboutItem) o;

        return title.equals(aboutItem.title);

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "AboutItem{" +
                "title='" + title + '\'' +
                '}';
    }
}
