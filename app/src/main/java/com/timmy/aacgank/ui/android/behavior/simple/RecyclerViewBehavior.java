package com.timmy.aacgank.ui.android.behavior.simple;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 作用于RecyclerView上:控制RecyclerView的位置
 * 监听TextView的滑动变化
 */
public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof TextView;
    }

    /**
     * 通过TextView的位置来控制RecyclerView的位置
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        // dependency.getTranslationY() TextView滑动的距离
        float y = dependency.getHeight() + dependency.getTranslationY();
//        Log.d("Timmy", "RecyclerViewBehavior-- dependency.getTranslationY():" + dependency.getTranslationY());
        if (y < 0) {
            y = 0;
        }
        child.setY(y);
        return true;
    }
}
