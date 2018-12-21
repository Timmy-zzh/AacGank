package com.timmy.customeview.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


public class DefaultLockView extends GestureLockView {

    private float innerRate = 0.2F;
    private float outerRate = 0.91F;

    public DefaultLockView(Context context) {
        super(context);
    }

    @Override
    protected void onDrawNormal(Canvas canvas, Paint mPaint, int mRadius) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(delegate.getNormalInnerColor());
        canvas.drawCircle(mCenterX, mCenterY, mRadius * innerRate, mPaint);
    }

    @Override
    protected void onDrawSelected(Canvas canvas, Paint mPaint, int mRadius) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(delegate.getSelectInnerColor());
        canvas.drawCircle(mCenterX, mCenterY, mRadius * innerRate, mPaint);

        mPaint.setColor(delegate.getSelectOutterColor());
        canvas.drawCircle(mCenterX, mCenterY, mRadius * outerRate, mPaint);
    }

    @Override
    protected void onDrawError(Canvas canvas, Paint mPaint, int mRadius) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(delegate.getErrorInnerColor());
        canvas.drawCircle(mCenterX, mCenterY, mRadius * innerRate, mPaint);

        mPaint.setColor(delegate.getErrorOutterColor());
        canvas.drawCircle(mCenterX, mCenterY, mRadius * outerRate, mPaint);
    }
}
