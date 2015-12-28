package com.pipilika.news.util;

import android.util.Log;

import java.util.Scanner;

/**
 * Created by tuman on 11/8/2015.
 */
public class BanglaTime {

    private static final String TAG = BanglaTime.class.getSimpleName();
    private static final CharSequence yesterdaySequence = "Yesterday";
    private static final CharSequence secondAgoSequence = " second ago";
    private static final CharSequence secondsAgoSequence = " seconds ago";
    private static final CharSequence minuteAgoSequence = " minute ago";
    private static final CharSequence minutesAgoSequence = " minutes ago";
    private static final CharSequence hourAgoSequence = " hour ago";
    private static final CharSequence hoursAgoSequence = " hours ago";
    private static final CharSequence dayAgoSequence = " day ago";
    private static final CharSequence daysAgoSequence = " days ago";
    private static final CharSequence monthAgoSequence = " month ago";
    private static final CharSequence monthsAgoSequence = " months ago";


    public static CharSequence getBanglaTime(CharSequence timeAgo) {
        Log.d(TAG, timeAgo.toString());
        Scanner scanner = new Scanner(timeAgo.toString());
        if (timeAgo.equals(yesterdaySequence) || (timeAgo.charAt(0) >= '0' && timeAgo.charAt(0) <= '9')) {
            if (timeAgo.equals(yesterdaySequence)) {
                return "গতকাল";
            } else {
                int time = scanner.nextInt();
                String changedString = NumericalExchange.toBanglaNumerical(time);
                if (timeAgo.equals((time + secondAgoSequence.toString())) || timeAgo.equals((time + secondsAgoSequence.toString()))) {
                    return changedString + " সেকেন্ড পূর্বে";
                } else if (timeAgo.equals((time + minuteAgoSequence.toString())) || timeAgo.equals((time + minutesAgoSequence.toString()))) {
                    return changedString + " মিনিট পূর্বে";
                } else if (timeAgo.equals((time + hourAgoSequence.toString())) || timeAgo.equals((time + hoursAgoSequence.toString()))) {
                    return changedString + " ঘন্টা পূর্বে";
                } else if (timeAgo.equals((time + dayAgoSequence.toString())) || timeAgo.equals((time + daysAgoSequence.toString()))) {
                    return changedString + " দিন পূর্বে";
                } else if (timeAgo.equals((time + monthAgoSequence.toString())) || timeAgo.equals((time + monthsAgoSequence.toString()))) {
                    return changedString + " দিন পূর্বে";
                }
            }
        } else if ((timeAgo.charAt(0) >= '০' && timeAgo.charAt(0) <= '৯')) {
            String time = scanner.next();
            Log.d(TAG, time);
            String changedString = time;
            if (timeAgo.equals((time + secondAgoSequence.toString())) || timeAgo.equals((time + secondsAgoSequence.toString()))) {
                return changedString + " সেকেন্ড পূর্বে";
            } else if (timeAgo.equals((time + minuteAgoSequence.toString())) || timeAgo.equals((time + minutesAgoSequence.toString()))) {
                return changedString + " মিনিট পূর্বে";
            } else if (timeAgo.equals((time + hourAgoSequence.toString())) || timeAgo.equals((time + hoursAgoSequence.toString()))) {
                return changedString + " ঘন্টা পূর্বে";
            } else if (timeAgo.equals((time + dayAgoSequence.toString())) || timeAgo.equals((time + daysAgoSequence.toString()))) {
                return changedString + " দিন পূর্বে";
            } else if (timeAgo.equals((time + monthAgoSequence.toString())) || timeAgo.equals((time + monthsAgoSequence.toString()))) {
                return changedString + " দিন পূর্বে";
            }
        } else if (timeAgo.equals("গতকাল") || (timeAgo.charAt(0) >= '০' && timeAgo.charAt(0) <= '৯')) {
            return timeAgo;
        }
        return timeAgo.toString();
    }
}
