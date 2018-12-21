package com.timmy.customeview.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;


public abstract class GestureLockView extends View {

    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int number;
    private int mState = GestureLockLayout.STATE_NORMAL;
    protected Vibrator vibrator;
    private int mRadius;
    protected int mCenterX, mCenterY;
    protected AttributeDelegate delegate;

    public GestureLockView(Context context) {
        super(context);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAttributeDelegate(int number, AttributeDelegate delegate, Vibrator vibrator) {
        this.number = number;
        this.delegate = delegate;
        this.vibrator = vibrator;
    }

    public AttributeDelegate getDelegate() {
        return delegate;
    }

    public boolean isNodeSelected() {
        return mState == GestureLockLayout.STATE_SELECTED;
    }

    public int getLockerState() {
        return mState;
    }

    int getCenterX() {
        return (getLeft() + getRight()) / 2;
    }

    int getCenterY() {
        return (getTop() + getBottom()) / 2;
    }

    int getNumber() {
        return number;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRadius = Math.min(w, h) / 2;
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    public void setLockerState(int state, boolean isMid) {
        if (state == GestureLockLayout.STATE_SELECTED) {
            if (delegate.isVibrateEnable() && !isMid) { // 震动
                vibrator.vibrate(delegate.getVibrateAnimatTime());
            }
            //动画
            if (delegate.getLinkSelectedAnim() != 0) { // 播放动画
                startAnimation(AnimationUtils.loadAnimation(getContext(), delegate.getLinkSelectedAnim()));
            }
        } else {
            clearAnimation();
        }
        mState = state;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        switch (mState) {
            case GestureLockLayout.STATE_NORMAL:
                onDrawNormal(canvas, mPaint, mRadius);
                break;
            case GestureLockLayout.STATE_SELECTED:
                onDrawSelected(canvas, mPaint, mRadius);
                break;
            case GestureLockLayout.STATE_ERROR:
                onDrawError(canvas, mPaint, mRadius);
                break;
        }
    }

    protected abstract void onDrawNormal(Canvas canvas, Paint mPaint, int mRadius);

    protected abstract void onDrawSelected(Canvas canvas, Paint mPaint, int mRadius);

    protected abstract void onDrawError(Canvas canvas, Paint mPaint, int mRadius);

}
