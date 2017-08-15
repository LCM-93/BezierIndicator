package com.lcm.bezierbottomIndicator.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
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
    private boolean touchable = true;
    private ShadowTransformer shadowTransformer;

    public BezierViewPager(Context context) {
        super(context);
        changeViewPageScroller();
    }

    public BezierViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeViewPageScroller();
    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (touchable) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (touchable) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }


    public void showShadowTransformer(float scaleVal) {
        if (CardAdapter.class.isInstance(getAdapter())) {
            if (shadowTransformer == null) {
                shadowTransformer = new ShadowTransformer(this, (CardAdapter) getAdapter());
            }
            shadowTransformer.enableScaling(true);
            shadowTransformer.setmScaleValue(scaleVal);
        }
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
        private int mDuration = 400;

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
