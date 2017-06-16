package com.app.julie.oneproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * Created by hubert
 * <p>
 * Created on 2017/6/8.
 */

public class TimePickerScroller extends ScrollView {

    public TimePickerScroller(Context context) {
        super(context);
    }

    public TimePickerScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimePickerScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = ev.getX();
                if (x < 150) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_HOVER_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
