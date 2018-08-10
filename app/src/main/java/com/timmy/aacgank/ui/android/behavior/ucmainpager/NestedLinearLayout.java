package com.timmy.aacgank.ui.android.behavior.ucmainpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * UC首页头部包裹控件:模仿RecyclerlView写法,将事件传递给CoordinatorLayout的子控件Behavior使用
 * 1.实现NestedScrollingChild接口
 * 2.嵌套滑动辅助类NestedScrollingChildHelper接收实现
 * 3.滑动事件传递
 */
public class NestedLinearLayout extends LinearLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper mScrollingChildHelper;
    private int mLastTouchX;
    private int mLastTouchY;
    private final int[] mScrollConsumed = new int[2];
    private final int[] mScrollOffset = new int[2];

    public NestedLinearLayout(Context context) {
        this(context, null);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Re-set whether nested scrolling is enabled so that it is set on all API levels
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastTouchX = (int) (event.getX() + 0.5f);
                mLastTouchY = (int) (event.getY() + 0.5f);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                final int x = (int) (event.getX() + 0.5f);
                final int y = (int) (event.getY() + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset);
                break;
        }
        return true;
    }

    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }
}
