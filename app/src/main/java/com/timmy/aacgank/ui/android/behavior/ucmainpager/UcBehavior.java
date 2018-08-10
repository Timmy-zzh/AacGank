package com.timmy.aacgank.ui.android.behavior.ucmainpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 使用Uc首页效果
 * 嵌套滑动:NestedScrolling
 * NestedScrolling 提供了一套父 View 和子 View 滑动交互机制。要完成这样的交互，
 * 父 View 需要实现 NestedScrollingParent 接口，而子 View 需要实现 NestedScrollingChild 接口，
 * 系统提供的 NestedScrollView 控件就实现了这两个接口
 */
public class UcBehavior extends CoordinatorLayout.Behavior {

    private String TAG = "UcBehavior";

    public UcBehavior() {
    }

    public UcBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        Log.d(TAG, "onMeasureChild");
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }


    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        Log.d(TAG, "onLayoutChild");
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.d(TAG, "onTouchEvent");
        return super.onTouchEvent(parent, child, ev);
    }

    /**
     * 嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
     * 只监听RecyclerView垂直方向上的滑动事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        Log.d(TAG, "onStartNestedScroll");
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    /**
     * 嵌套滑动结束（ACTION_UP或ACTION_CANCEL）
     */
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                   @NonNull View child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        Log.d(TAG, "onStopNestedScroll");
    }

    /**
     * 嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d(TAG, "onNestedPreScroll");
    }

    /**
     * 嵌套滑动进行中，要监听的子 View的滑动事件已经被消费
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull View child, @NonNull View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.d(TAG, "onNestedScroll");
    }

    /**
     * 要监听的子View即将快速滑动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout,
                                    @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    /**
     * 要监听的子 View在快速滑动中
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout,
                                 @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }
}
