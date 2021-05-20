package com.example.de_tai_di_dong.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private float mInitialDownY;
    private int mTouchSlop = 90000000;
    private boolean didit = false;

    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
        //mTouchSlop += ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //mTouchSlop += ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialDownY = ev.getY();
                didit = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float yDiff = ev.getY() - mInitialDownY;

                if (didit && yDiff < mTouchSlop) {
                    return false;
                }
                didit = true;

        }
        return super.onInterceptTouchEvent(ev);
    }

    public int getTouchSlop() {
        return mTouchSlop;
    }
    public void setTouchSlop(int mTouchSlop) {
        this.mTouchSlop = mTouchSlop;
    }
}
