package com.pipilika.news.adapters.recyclerview.item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajid on 10/19/2015.
 */
public class TimeLineDate {

    @SerializedName("date")
    private String date;

    @SerializedName("ids")
    private ArrayList<String> dates;

    private ArrayList<TimeLine> timeLines;

    public TimeLineDate() {

    }

    public TimeLineDate(String date, ArrayList<String> dates) {
        this.date = date;
        this.dates = dates;
        setTimeLines(dates);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getDates() {
        return dates;
    }

    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
        setTimeLines(dates);
    }

    public ArrayList<TimeLine> getTimeLines() {
        return timeLines;
    }

    public void setTimeLines(ArrayList<TimeLine> timeLines) {
        this.timeLines = timeLines;
    }

    public void setTimeLines(List<String> dates) {
        ArrayList<TimeLine> timeLines = new ArrayList<>();
        for (String date : dates) {
            String[] splitterDate = date.split("-");
            TimeLine timeLine = new TimeLine();
            timeLine.setYear(Integer.parseInt(splitterDate[0]));
            timeLine.setMonth(Integer.parseInt(splitterDate[1]));
            timeLine.setDayOfMonth(Integer.parseInt(splitterDate[2]));
            timeLine.setUIData(Integer.parseInt(splitterDate[3]));
            timeLines.add(timeLine);
        }
        setTimeLines(timeLines);
    }

    @Override
    public String toString() {
        return "TimeLineDate{" +
                "date='" + date + '\'' +
                ", dates=" + dates +
                ", timeLines=" + timeLines +
                '}';
    }
}
