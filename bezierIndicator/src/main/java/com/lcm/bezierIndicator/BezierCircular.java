package com.lcm.bezierIndicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * ****************************************************************
 * Author: LCM
 * Date: 2017/7/17 下午7:16
 * Desc:
 * *****************************************************************
 */

public class BezierCircular {
    private static final String TAG = "BezierCircular";

    private static final float C = 0.551915024494f; //常量


    //圆中心坐标
    float centerX;
    float centerY;

    //圆半径
    float radius;

    private PointF currentPoint;
    private PointF targetPoint;
    private float mDifference;

    private float stretchDistance;
    private float cDistance;

    private float moveDistance;

    private float[] mData = new float[8];  //顺时针记录绘制圆形的四个数据点
    private float[] mCtrl = new float[16];  //顺时针记录绘制圆形的八个控制点

    public BezierCircular(float radius) {
        this.radius = radius;
        stretchDistance = radius / 3 * 2;
        mDifference = radius * C;
        cDistance = mDifference * 0.45f;
    }

    public void setCenter(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;

    }



    public void initControlPoint() {

        //初始化数据点
        mData[0] = centerX;
        mData[1] = centerY + radius;

        mData[2] = centerX + radius;
        mData[3] = centerY;

        mData[4] = centerX;
        mData[5] = centerY - radius;

        mData[6] = centerX - radius;
        mData[7] = centerY;

        //初始化控制点
        mCtrl[0] = mData[0] + mDifference;
        mCtrl[1] = mData[1];

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifference;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifference;

        mCtrl[6] = mData[4] + mDifference;
        mCtrl[7] = mData[5];

        mCtrl[8] = mData[4] - mDifference;
        mCtrl[9] = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifference;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifference;

        mCtrl[14] = mData[0] - mDifference;
        mCtrl[15] = mData[1];
    }


    public void setCurrentAndTarget(PointF currentPoint, PointF targetPoint) {
        this.currentPoint = currentPoint;
        this.targetPoint = targetPoint;
        float distance = targetPoint.x - currentPoint.x;
        moveDistance = distance > 0 ? distance - 2 * stretchDistance : distance + 2 * stretchDistance;
    }

    public void setProgress(float progress) {
        if ((progress > 0 && progress <= 0.2) || (progress < 0 && progress >= -0.2)) {
            model1(progress);
        } else if ((progress > 0.2 && progress <= 0.5) || (progress < -0.2 && progress >= -0.5)) {
            model2(progress);
        } else if ((progress > 0.5 && progress <= 0.8) || (progress < -0.5 && progress >= -0.8)) {
            model3(progress);
        } else if ((progress > 0.8 && progress <= 0.9) || (progress < -0.8 && progress >= -0.9)) {
            model4(progress);
        } else if ((progress > 0.9 && progress < 1) || (progress < -0.9 && progress > -1)) {
            model5(progress);
        }
//        } else if (progress >= 1 || progress <= -1) {
//            Log.i(TAG,"-------------------------------------------");
////            centerX = targetPoint.x;
////            centerY = targetPoint.y;
////            initControlPoint();
//        }
    }


    public void model1(float progress) {
        if (progress > 0)
            mData[2] = centerX + radius + stretchDistance * progress * 5;

        if (progress < 0)
            mData[6] = centerX - radius + stretchDistance * progress * 5;

        mCtrl[2] = mData[2];
        if (progress > 0)
            mCtrl[3] = mData[3] + mDifference + cDistance * progress * 5;

        mCtrl[4] = mData[2];
        if (progress > 0)
            mCtrl[5] = mData[3] - mDifference - cDistance * progress * 5;

        mCtrl[10] = mData[6];
        if (progress < 0)
            mCtrl[11] = mData[7] - mDifference + cDistance * progress * 5;

        mCtrl[12] = mData[6];
        if (progress < 0)
            mCtrl[13] = mData[7] + mDifference - cDistance * progress * 5;
    }

    public void model2(float progress) {
        model1(progress > 0 ? 0.2f : -0.2f);

        progress = progress > 0 ? (progress - 0.2f) * (10f / 3) : (progress + 0.2f) * (10f / 3);
        //初始化数据点
        mData[0] = centerX + stretchDistance * progress;

        if (progress > 0)
            mData[2] = centerX + radius + stretchDistance * (1 + progress);
        else
            mData[2] = centerX + radius;


        mData[4] = centerX + stretchDistance * progress;

        if (progress < 0)
            mData[6] = centerX - radius - stretchDistance + stretchDistance * progress;
        else
            mData[6] = centerX - radius;


        //初始化控制点
        mCtrl[0] = mData[0] + mDifference;

        mCtrl[2] = mData[2];
        if (progress > 0)
            mCtrl[3] = mData[3] + mDifference + cDistance;
        else
            mCtrl[3] = mData[3] + mDifference - cDistance * progress;

        mCtrl[4] = mData[2];
        if (progress > 0)
            mCtrl[5] = mData[3] - mDifference - cDistance;
        else
            mCtrl[5] = mData[3] - mDifference + cDistance * progress;


        mCtrl[6] = mData[4] + mDifference;

        mCtrl[8] = mData[4] - mDifference;

        mCtrl[10] = mData[6];
        if (progress > 0)
            mCtrl[11] = mData[7] - mDifference - cDistance * progress;
        else
            mCtrl[11] = mData[7] - mDifference - cDistance;

        mCtrl[12] = mData[6];
        if (progress > 0)
            mCtrl[13] = mData[7] + mDifference + cDistance * progress;
        else
            mCtrl[13] = mData[7] + mDifference + cDistance;


        mCtrl[14] = mData[0] - mDifference;
    }

    public void model3(float progress) {
        model2(progress > 0 ? 0.5f : -0.5f);
        progress = progress > 0 ? (progress - 0.5f) * (10f / 3) : (progress + 0.5f) * (10f / 3);

        //初始化数据点
        if (progress > 0)
            mData[0] = centerX + moveDistance * progress + stretchDistance;
        else
            mData[0] = centerX - moveDistance * progress - stretchDistance;


        if (progress > 0)
            mData[2] = centerX + moveDistance * progress + radius + 2 * stretchDistance;
        else
            mData[2] = centerX - moveDistance * progress + radius;


        if (progress > 0)
            mData[4] = centerX + moveDistance * progress + stretchDistance;
        else
            mData[4] = centerX - moveDistance * progress - stretchDistance;


        if (progress > 0)
            mData[6] = centerX + moveDistance * progress - radius;
        else
            mData[6] = centerX - moveDistance * progress - radius - 2 * stretchDistance;

        //初始化控制点
        mCtrl[0] = mData[0] + mDifference;

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifference + cDistance;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifference - cDistance;

        mCtrl[6] = mData[4] + mDifference;

        mCtrl[8] = mData[4] - mDifference;

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifference - cDistance;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifference + cDistance;

        mCtrl[14] = mData[0] - mDifference;
    }

    public void model4(float progress) {


        model3(progress > 0 ? 0.8f : -0.8f);

        progress = progress > 0 ? (progress - 0.8f) * 10 : (progress + 0.8f) * 10;

        //初始化数据点
        if (progress > 0)
            mData[0] = centerX + moveDistance + stretchDistance + stretchDistance * progress;
        else
            mData[0] = centerX + moveDistance - stretchDistance + stretchDistance * progress;

        if (progress > 0)
            mData[2] = centerX + moveDistance + radius + 2 * stretchDistance;
        else
            mData[2] = centerX + moveDistance + radius + stretchDistance * progress;

        if (progress > 0)
            mData[4] = centerX + moveDistance + stretchDistance + stretchDistance * progress;
        else
            mData[4] = centerX + moveDistance - stretchDistance + stretchDistance * progress;

        if (progress > 0)
            mData[6] = centerX + moveDistance - radius + stretchDistance * progress;
        else
            mData[6] = centerX + moveDistance - radius - 2 * stretchDistance;

        //初始化控制点
        mCtrl[0] = mData[0] + mDifference;

        mCtrl[2] = mData[2];
        if (progress > 0)
            mCtrl[3] = mData[3] + mDifference + cDistance - cDistance * progress;
        else
            mCtrl[3] = mData[3] + mDifference + cDistance;

        mCtrl[4] = mData[2];
        if (progress > 0)
            mCtrl[5] = mData[3] - mDifference - cDistance + cDistance * progress;
        else
            mCtrl[5] = mData[3] - mDifference - cDistance;

        mCtrl[6] = mData[4] + mDifference;

        mCtrl[8] = mData[4] - mDifference;

        mCtrl[10] = mData[6];
        if (progress > 0)
            mCtrl[11] = mData[7] - mDifference - cDistance;
        else
            mCtrl[11] = mData[7] - mDifference - cDistance - cDistance * progress;

        mCtrl[12] = mData[6];
        if (progress > 0)
            mCtrl[13] = mData[7] + mDifference + cDistance;
        else
            mCtrl[13] = mData[7] + mDifference + cDistance + cDistance * progress;

        mCtrl[14] = mData[0] - mDifference;
    }

    public void model5(float progress) {
        model4(progress > 0 ? 0.9f : -0.9f);

        progress = progress > 0 ? (progress - 0.9f) * 10 : (progress + 0.9f) * 10;

        //初始化数据点
        if (progress > 0)
            mData[0] = centerX + moveDistance + 2 * stretchDistance;
        else
            mData[0] = centerX + moveDistance - 2 * stretchDistance;

        if (progress > 0)
            mData[2] = centerX + moveDistance + radius + 2 * stretchDistance;
        else
            mData[2] = (float) (centerX + moveDistance + radius - stretchDistance - (Math.sin(Math.PI * 3 / 2 * Math.abs(progress) - Math.PI / 2) + 1) * stretchDistance);


        if (progress > 0)
            mData[4] = centerX + moveDistance + 2 * stretchDistance;
        else
            mData[4] = centerX + moveDistance - 2 * stretchDistance;

        if (progress > 0)
            mData[6] = (float) (centerX + moveDistance - radius + stretchDistance + (Math.sin(Math.PI * 3 / 2 * progress - Math.PI / 2) + 1) * stretchDistance);
        else
            mData[6] = centerX + moveDistance - radius - 2 * stretchDistance;


        //初始化控制点
        mCtrl[0] = mData[0] + mDifference;

        mCtrl[2] = mData[2];
        if (progress < 0)
            mCtrl[3] = mData[3] + mDifference + cDistance + cDistance * progress;

        mCtrl[4] = mData[2];
        if (progress < 0)
            mCtrl[5] = mData[3] - mDifference - cDistance - cDistance * progress;

        mCtrl[6] = mData[4] + mDifference;

        mCtrl[8] = mData[4] - mDifference;

        mCtrl[10] = mData[6];
        if (progress > 0)
            mCtrl[11] = mData[7] - mDifference - cDistance + cDistance * progress;

        mCtrl[12] = mData[6];
        if (progress > 0)
            mCtrl[13] = mData[7] + mDifference + cDistance - cDistance * progress;

        mCtrl[14] = mData[0] - mDifference;
    }



    public void drawCircle(Canvas canvas, Paint mPaint) {
        Path path = new Path();
        path.moveTo(mData[0], mData[1]);

        path.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2], mData[3]);
        path.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4], mData[5]);
        path.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6], mData[7]);
        path.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0], mData[1]);

        canvas.drawPath(path, mPaint);
    }


    public void resetCircular(PointF pointF) {
        setCenter(pointF.x, pointF.y);
        initControlPoint();
    }

}
