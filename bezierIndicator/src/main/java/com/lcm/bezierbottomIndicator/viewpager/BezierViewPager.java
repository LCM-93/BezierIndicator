package com.lcm.bezierbottomIndicator.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/8/15 上午9:49
 * Desc:
 * *****************************************************************
 */

public class BezierViewPager extends ViewPager {


    public BezierViewPager(Context context) {
        super(context);
        changeViewPageScroller();
    }

    public BezierViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeViewPageScroller();
    }






    //反射机制 控制 viewpager滑动时间  为800
    private void changeViewPageScroller() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller scroller;
            scroller = new FixedSpeedScroller(getContext(), new AccelerateDecelerateInterpolator());
            mField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class FixedSpeedScroller extends Scroller {
        private int mDuration = 450;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy,
                                int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }

    }
}
