package com.timmy.androidbase.behavior.simple;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义Behavior情况一:某个view监听另一个view的状态变化，例如大小、位置、显示状态等
 * (TextView监听RecyclerView的滑动,而进行位置和透明度的改变)
 * (主要关注:layoutDependsOn和onDependentViewChanged方法)
 *
 * 该Behavior需要作用在一个顶部TextView上:
 * 主要实现逻辑为: 监听RecyclerView滑动,TextView的位置进行变化
 * RecyclerView上滑时,TextView自动下滑,当TextView全部显示后,继续上划Title固定
 * RecyclerView下滑, 当列表顶部和Title底部重合时,title开始自动上滑直到完全隐藏
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    private float scrollY;//列表顶部和Title底部重合时, RecyclerView滑动的距离

    public TitleBehavior() {
    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定使用该Behavior的View要监听那个类型的View的状态变化
     *
     * @param parent     代表CoordinatorLayout
     * @param child      代表使用该Behavior的View--TextView
     * @param dependency 代表要监听的View。这里要监听RecyclerView。
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    /**
     * 当被监听的View状态变化时会调用该方法--RecyclerView滑动时调用该方法
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d("Timmy", "dependency.getY():" + dependency.getY() + ",child.getHeight():" + child.getHeight());
        if (scrollY == 0) {     //这是一个固定值:为初始RecyclerView顶部到Title底部的距离
            scrollY = dependency.getY() - child.getHeight();
        }
        Log.d("Timmy", "scrollY:" + scrollY);
        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        Log.d("Timmy", "dy--:" + dy);
        float y = -(dy / scrollY) * child.getHeight();
        Log.d("Timmy", "y-------:" + y);
        //滑动距离
        child.setTranslationY(y);
        //透明度
        child.setAlpha(1-(dy/scrollY));
        return true;
    }
}
