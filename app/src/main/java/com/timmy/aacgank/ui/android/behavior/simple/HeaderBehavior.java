package com.timmy.aacgank.ui.android.behavior.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义Behavior情况二:某个view监听CoordinatorLayout里的滑动状态
 * (主要关注:onStartNestedScroll和onNestedPreScroll方法。)
 *
 * 作用于TextView上
 * 监听RecyclerView滑动距离:
 * 用于控制RecyclerView的头部的展示和显示
 */
public class HeaderBehavior extends CoordinatorLayout.Behavior<TextView> {

    private int lastPosition;
    private boolean downReach;
    private boolean upReach;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
     * 只监听RecyclerView垂直方向上的滑动事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull TextView child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        Log.d("Timmy", "HeaderBehavior  axes:" + axes);
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
     * 1.情况一:
     * RecyclreView在TextView下面,RecyclerView开始向上滑动,TextView也要跟随一起向上滑动
     * 此时RecyclerView的第一个position为0,
     * 通过
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy                表示当前事件滑动的距离,需要叠加进行使用 dy>0 向上滑动,dy<0向下滑动
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child,
                                  @NonNull View target,
                                  int dx, int dy,
                                  @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d("HeaderBehavior  Timmy", "dy:" + dy);
        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (canScroll(child, dy) && position == 0) {
                float fixedDy = child.getTranslationY() - dy;
                Log.d("HeaderBehavior  Timmy", " --------child.getTranslationY():" + child.getTranslationY());
                if (fixedDy < -child.getHeight()) { //TextView滑动超出屏幕
                    fixedDy = -child.getHeight();
                } else if (fixedDy > 0) {
                    fixedDy = 0;
                }
                child.setTranslationY(fixedDy);
                //让CoornidatorLayout消费滑动事件
                consumed[1] = dy;
            }
        }
    }

    /**
     * 当TextView滑动的高度TranstionY等于其高度时,不让CoornidatorLayout消费事件
     *
     * @param child
     * @param scrollY
     * @return
     */
    private boolean canScroll(TextView child, int scrollY) {
        Log.d("  Timmy", "canScroll scrollY:" + scrollY + ",child.getTranslationY():" + child.getTranslationY());
        Log.d("  Timmy", "canScroll upReach:" + upReach + ",downReach:" + downReach);
        /**
         * child.getTranslationY() == -child.getHeight() TextView完全滑出屏幕
         * scrollY > 0 向上滑动
         * !upReach TextView未滑动超出屏幕
         */
        if (scrollY > 0 && child.getTranslationY() == -child.getHeight()) {
            return false;
        }
        return true;
    }

    /**
     * 嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
     * 1.情况一:
     * RecyclreView在TextView下面,RecyclerView开始向上滑动,TextView也要跟随一起向上滑动
     * 此时RecyclerView的第一个position为0,
     * 通过
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy                表示当前事件滑动的距离,需要叠加进行使用 dy>0 向上滑动,dy<0向下滑动
     * @param consumed
     * @param type
     */
//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child,
//                                  @NonNull View target,
//                                  int dx, int dy,
//                                  @NonNull int[] consumed, int type) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        Log.d("HeaderBehavior  Timmy", "dy:" + dy);
//        if (target instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) target;
//            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//            //第一个条目,还要往下滑
//            if (position == 0 && position < lastPosition) {
//                downReach = true;
//            }
//
//            if (canScroll(child, dy) && position == 0) {
//                float fixedDy = child.getTranslationY() - dy;
//                Log.d("HeaderBehavior  Timmy", " --------child.getTranslationY():" + child.getTranslationY());
//                if (fixedDy < -child.getHeight()) { //TextView滑动超出屏幕
//                    fixedDy = -child.getHeight();
//                    upReach = true;
//                } else if (fixedDy > 0) {
//                    fixedDy = 0;
//                }
//                child.setTranslationY(fixedDy);
//
//                //让CoornidatorLayout消费滑动事件
//                consumed[1] = dy;
//            }
//            lastPosition = position;
//        }
//    }
//
//    /**
//     * 当TextView滑动的高度TranstionY等于其高度时,不让CoornidatorLayout消费事件
//     *
//     * @param child
//     * @param scrollY
//     * @return
//     */
//    private boolean canScroll(TextView child, int scrollY) {
//        Log.d("  Timmy", "canScroll scrollY:" + scrollY + ",child.getTranslationY():" + child.getTranslationY());
//        Log.d("  Timmy", "canScroll upReach:" + upReach + ",downReach:" + downReach);
//        /**
//         * child.getTranslationY() == -child.getHeight() TextView完全滑出屏幕
//         * scrollY > 0 向上滑动
//         * !upReach TextView未滑动超出屏幕
//         */
//        if (scrollY > 0 && child.getTranslationY() == -child.getHeight() && upReach) {
//            return false;
//        }
//        if (downReach) {
//            return true;
//        }
//        return true;
//    }
}
