package com.pipilika.news.utils;

import java.util.Scanner;

/**
 * Created by tuman on 11/8/2015.
 */
public class BanglaTime {

    private static final CharSequence yesterdaySequence = "yesterday";
    private static final CharSequence secondAgoSequence = " seconds ago";
    private static final CharSequence minutesAgoSequence = " minutes ago";
    private static final CharSequence hourAgoSequence = " hour ago";
    private static final CharSequence hoursAgoSequence = " hours ago";
    private static final CharSequence daysAgoSequence = " days ago";

    public static CharSequence getBanglaTime(CharSequence timeAgo) {
        if (timeAgo.equals(yesterdaySequence) || (timeAgo.charAt(0) >= '0' && timeAgo.charAt(0) <= '9')) {
            if (timeAgo.equals(yesterdaySequence)) {
                return "গতকাল";
            } else {
                Scanner scanner = new Scanner(timeAgo.toString());
                int time = scanner.nextInt();
                String changedString = NumericalExchange.toBanglaNumerical(time);
                if (timeAgo.equals((time + secondAgoSequence.toString()))) {
                    return changedString + " সেকেন্ড পূর্বে";
                } else if (timeAgo.equals((time + minutesAgoSequence.toString()))) {
                    return changedString + " মিনিট পূর্বে";
                } else if (timeAgo.equals((time + hourAgoSequence.toString()))) {
                    return changedString + " ঘন্টা পূর্বে";
                } else if (timeAgo.equals((time + hoursAgoSequence.toString()))) {
                    return changedString + " ঘন্টা পূর্বে";
                } else if (timeAgo.equals((time + daysAgoSequence.toString()))) {
                    return changedString + " দিন পূর্বে";
                }
            }
        } else if (timeAgo.equals("গতকাল") || (timeAgo.charAt(0) >= '০' && timeAgo.charAt(0) <= '৯')) {
            return timeAgo;
        }
        return timeAgo.toString();
    }
}
