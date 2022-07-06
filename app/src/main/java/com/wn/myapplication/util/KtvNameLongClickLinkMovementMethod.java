package com.wn.myapplication.util;

import android.os.Handler;
import android.text.Layout;
import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wn.myapplication.span.LongClickableSpan;

/**
 * Created by bby
 * Date: 2017/9/19.
 */

public class KtvNameLongClickLinkMovementMethod extends LinkMovementMethodCompat {

    private Handler mLongClickHandler = new Handler();
    private boolean mIsLongPressed = false;
    private Long lastClickTime = 0L;
    private int lastX = 0;
    private int lastY = 0;
    @Override
    public boolean onTouchEvent(final TextView widget, Spannable buffer,
                                MotionEvent event) {
        int action = event.getAction();

        try {
            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                lastX = x;
                lastY = y;

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = 0;
                try {
                    off = layout.getOffsetForHorizontal(line, x);
                } catch (IndexOutOfBoundsException e) {

                        e.printStackTrace();

                }
                final LongClickableSpan[] link = buffer.getSpans(off, off, LongClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        mLongClickHandler.removeCallbacksAndMessages(null);
                        if (!mIsLongPressed) {
                            link[0].onClick(widget);
                        }
                        mIsLongPressed = false;
                    } else {
                        lastClickTime = System.currentTimeMillis();
                        mLongClickHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mIsLongPressed = true;
                                link[0].onLongClick(widget);
                            }
                        }, 500);
                    }
                    return true;
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static KtvNameLongClickLinkMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new KtvNameLongClickLinkMovementMethod();
        }

        return sInstance;
    }
    private static KtvNameLongClickLinkMovementMethod sInstance;
}
