package com.pipilika.news.item.recyclerview;

import android.util.Log;

import com.pipilika.news.R;
import com.pipilika.news.util.NumericalExchange;

/**
 * Created by sajid on 12/22/2015.
 */
public class TimeLine {

    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private String time;
    private int timelineImageResource;

    public TimeLine() {
    }

    public TimeLine(int year, int month, int dayOfMonth, String time, int timelineImageResource) {

        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.time = time;
        this.timelineImageResource = timelineImageResource;
    }

    public int getYear() {

        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUIData(int hour) {
        setHour(hour);
        int formattedHour = hour <= 12 ? (hour == 0 ? 12 : hour) : hour - 12;
        String time = NumericalExchange.toBanglaNumerical(formattedHour);
        if (hour >= 0 && hour < 6) {
            time = "রাত" + "\n" + time;
            setTimelineImageResource(R.mipmap.ic_night_black_48dp);
        } else if (hour >= 6 && hour < 12) {
            time = "সকাল" + "\n" + time;
            setTimelineImageResource(R.mipmap.ic_morning_black_48dp);
        } else if (hour >= 12 && hour < 18) {
            time = "দুপুর" + "\n" + time;
            setTimelineImageResource(R.mipmap.ic_noon_black_48dp);
        } else if (hour >= 18 && hour < 24) {
            time = "সন্ধ্যা" + "\n" + time;
            setTimelineImageResource(R.mipmap.ic_evening_black_48dp);
        }
        Log.d("TAG", getTimelineImageResource() + "");
        time += ":" + NumericalExchange.toBanglaNumerical(0);
        setTime(time);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getTimelineImageResource() {
        return timelineImageResource;
    }

    public void setTimelineImageResource(int timelineImageResource) {
        this.timelineImageResource = timelineImageResource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeLine)) return false;

        TimeLine timeLine = (TimeLine) o;

        return
                getYear() == timeLine.getYear()
                        && getMonth() == timeLine.getMonth() && getDayOfMonth() == timeLine.getDayOfMonth()
                        && getHour() == timeLine.getHour()
                        && getTimelineImageResource() == timeLine.getTimelineImageResource()
                        && getTime().equals(timeLine.getTime());

    }

    @Override
    public int hashCode() {
        int result = getYear();
        result = 31 * result + getMonth();
        result = 31 * result + getDayOfMonth();
        result = 31 * result + getHour();
        result = 31 * result + getTime().hashCode();
        result = 31 * result + getTimelineImageResource();
        return result;
    }

    @Override
    public String toString() {
        return "TimeLine{" +
                "year=" + year +
                ", month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                ", hour=" + hour +
                ", time='" + time + '\'' +
                ", timelineImageResource=" + timelineImageResource +
                '}';
    }
}
