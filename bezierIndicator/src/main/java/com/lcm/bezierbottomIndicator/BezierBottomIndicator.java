package com.lcm.bezierbottomIndicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/7/17 下午7:34
 * Desc:
 * *****************************************************************
 */

public class BezierBottomIndicator extends ViewGroup {

    private static final String TAG = "BezierBottomIndicator";

    private boolean isAnimitorStart = false;

    private int width = 0;
    private int height = 0;

    private int childSideLength = 0;  //子view外框的边长
    private float childpadding = 20;  //子View的Padding值

    private float defaultLeftRightGap = 10; //左右两边默认的距离


    private List<PointF> anchorList;
    private Paint childBgPaint;

    private Paint bezierPaint;
    private BezierCircular bezierCircular;

    private int checkIndex = 0;
    private int currentPosition = 0;

    public BezierBottomIndicator(Context context) {
        this(context, null);
    }

    public BezierBottomIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierBottomIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BezierBottomIndicator, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.BezierBottomIndicator_leftRightGap) {
                defaultLeftRightGap = typedArray.getDimension(attr, 10);
            } else if (attr == R.styleable.BezierBottomIndicator_childPading) {
                childpadding = typedArray.getDimension(attr, 20);
            }
        }
        typedArray.recycle();

    }


    private void init() {
        //自定义ViewGroup默认不会调用OnDraw()方法
        setWillNotDraw(false);

        anchorList = new ArrayList<>();

        childBgPaint = new Paint();
        childBgPaint.setColor(Color.WHITE);
        childBgPaint.setAntiAlias(true);
        childBgPaint.setStrokeWidth(1);
        childBgPaint.setStyle(Paint.Style.STROKE);


        bezierPaint = new Paint();
        bezierPaint.setColor(Color.YELLOW);
        bezierPaint.setAntiAlias(true);
        bezierPaint.setStyle(Paint.Style.FILL);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = sizeWidth;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = sizeHeight;
        } else {
            height = wm.getDefaultDisplay().getHeight();
        }

        if (getChildCount() != 0) {
            childSideLength = (width - getPaddingRight() - getPaddingLeft()) / getChildCount() > height - getPaddingBottom() - getPaddingTop() ? height - getPaddingBottom() - getPaddingTop() : (width - getPaddingLeft() - getPaddingRight()) / getChildCount();
//        //计算出所有的ChildView的宽和高
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            bezierCircular = new BezierCircular(childSideLength / 2);
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        float childDis = (width - getPaddingLeft() - getPaddingRight() - 2 * defaultLeftRightGap - childSideLength) / (childCount - 1);

        float cWidth = childSideLength - 2 * childpadding;
        float cHeight = cWidth;

        anchorList.clear();
        //计算子控件的位置，强制将子View控制绘制在均分的几个锚点上
        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);

            PointF anchorPoint = new PointF((childDis * i + defaultLeftRightGap + childSideLength / 2 + getPaddingLeft()), getPaddingTop() + childSideLength / 2);
            anchorList.add(anchorPoint);

            childView.layout((int) (anchorPoint.x - cWidth / 2), (int) (anchorPoint.y - cHeight / 2), (int) (anchorPoint.x + cWidth / 2), (int) (anchorPoint.y + cHeight / 2));
        }
        PointF pointF = anchorList.get(0);
        bezierCircular.setCenter(pointF.x, pointF.y);
        bezierCircular.initCotrlPoint();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        bezierCircular.drawCircle(canvas, bezierPaint);
        drawChildBg(canvas);
        super.onDraw(canvas);

    }


    float touchX = 0;
    float touchY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "touchX: " + touchX + "  touchY: " + touchY);
                for (int i = 0; i < anchorList.size(); i++) {
                    PointF pointF = anchorList.get(i);
                    if (touchX > (pointF.x - childSideLength / 2) && touchX < (pointF.x + childSideLength / 2) && touchY > (pointF.y - childSideLength / 2) && touchY < (pointF.y + childSideLength / 2)) {
                        checkIndex = i;
                        onClickIndex(checkIndex);
                    }
                }
                break;
        }
        return true;
    }


    private void onClickIndex(int position) {
        if (!isAnimitorStart) {
            startAnimator(position); //开始动画
            currentPosition = position;
            Log.i(TAG, "点击了第 " + position + " 项！");
        }
    }

    //绘制子View的背景
    private void drawChildBg(Canvas canvas) {
        if (anchorList == null || anchorList.size() == 0) {
            Log.i(TAG, "锚点位置为空");
            return;
        }

        for (int i = 0; i < anchorList.size(); i++) {
            PointF pointF = anchorList.get(i);
            canvas.drawCircle(pointF.x, pointF.y, (childSideLength - 2) / 2, childBgPaint);
        }
    }

    /**
     * 切换动画
     * @param targetPosition
     */
    private void startAnimator(int targetPosition) {
        bezierCircular.setCurrentAndTarget(anchorList.get(currentPosition), anchorList.get(targetPosition));
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, targetPosition > currentPosition ? 1 : -1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bezierCircular.setProgress((Float) animation.getAnimatedValue());
                postInvalidate();
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimitorStart = true;
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimitorStart = false;
                super.onAnimationEnd(animation);
            }
        });

        int count = Math.abs(targetPosition - currentPosition);
        if (count == 0) {
            return;
        }

        int duration = 1000;
        if (count == 1) {
            duration = 800;
        } else if (count == 2) {
            duration = 1000;
        } else if (count == 3) {
            duration = 1200;
        }
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
