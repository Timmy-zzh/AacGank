package com.timmy.aacgank.ui.person.behavior.ucmainpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.timmy.aacgank.ui.person.behavior.helper.ViewOffsetBehavior;

/**
 * 是什么:UC主页的Header部分的使用的Behavior
 * 作用:
 * 1监听当主页内容Content部分滑动时,头部需要跟着滑动
 * 2.Header部分控件滑动时,内容部分也需要有滑动效果
 */
public class UcHeaderBehavior extends ViewOffsetBehavior<View> {

    private String TAG = this.getClass().getSimpleName();

    public UcHeaderBehavior() {
    }

    public UcHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当内容控件滑动前(ACTION_DOWN),询问头部空间是否要监听此事件
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 当内容控件滑动时(ACTION_MOVE),询问头部控件是否要消费此事件,消费多少
     * dy>0 向上滑动,dy<0向下滑动
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                  @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        float scrollY;

        if (target instanceof NestedLinearLayout) {//头部控件的包裹布局NestedLinearLayout滑动时
             scrollY = child.getTranslationY() - dy;
            Log.d(TAG, "NestedLinearLayout  onDependentViewChanged--scrollY:" + scrollY);
            if (scrollY < -child.getHeight()) {
                scrollY = -child.getHeight();
            } else if (scrollY > 0) {
                scrollY = 0;
            }
            child.setTranslationY(scrollY);
            consumed[1] = dy;

        } else if (target instanceof RecyclerView) {       //内容控件的RecyclerView滑动
            RecyclerView recyclerView = (RecyclerView) target;
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (canScroll(child, dy) && position == 0) {
                 scrollY = child.getTranslationY() - dy;

                if (scrollY < -child.getHeight()) {
                    scrollY = -child.getHeight();
                } else if (scrollY > 0) {
                    scrollY = 0;
                }
                child.setTranslationY(scrollY);
                consumed[1] = dy;
            }
        }
    }

    /**
     * 1.从默认状态开始->RecyclerView往上(往下)滑动,但头部控件未全部滑出屏幕
     * 这个时候头部调用setTranstionY()让头部控件偏移,CoordinatorLayout进行事件消费 consumed[1]设值
     * 2.头部滑出屏幕外,这个时候->RecyclerView上下滑动,CoordinatorLayout事件不消费
     */
    private boolean canScroll(View child, int dy) {
//        Log.d(TAG, "child.getTranslationY():" + child.getTranslationY());
        if (-child.getTranslationY() < child.getHeight()) { // RecyclerView往上(往下)滑动,但头部控件未全部滑出屏幕
            return true;
        } else if (-child.getTranslationY() == child.getHeight() && dy < 0) {
            // RecyclerView往下滑动,第一个Item刚全部出现,临界值
            return true;
        }
        return false;
    }
}
