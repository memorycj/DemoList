package com.wn.myapplication.util;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * fix : https://bugly.qq.com/v2/crash-reporting/crashes/771c9fa2ab/6655014/report?pid=1&search=LinkMovementMethod&searchType=detail&bundleId=&channelId=&version=all&tagList=&start=0&date=all
 * <p>
 * google issue : https://issuetracker.google.com/issues/113348914
 */
public class LinkMovementMethodCompat extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        boolean result = false;
        try {
            result = super.onTouchEvent(widget, buffer, event);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static LinkMovementMethodCompat getInstance() {
        if (sInstance == null)
            sInstance = new LinkMovementMethodCompat();

        return sInstance;
    }

    private static LinkMovementMethodCompat sInstance;

}
