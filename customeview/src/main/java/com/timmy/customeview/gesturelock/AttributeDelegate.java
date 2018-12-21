package com.timmy.customeview.gesturelock;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.timmy.baselib.R;


public class AttributeDelegate {

    private final String customeGestureLockPath;
    private final int linkSelectColor;
    private final int linkErrorColor;
    private final boolean drawBgEnable;
    private final float bgLineWidth;
    private final int bgLineColor;
    private final int[] bgShardColors;
    private final float linkWidth;
    private final int normalInnerColor;
    private final int normalOutterColor;
    private final int selectInnerColor;
    private final int selectOutterColor;
    private final int errorInnerColor;
    private final int errorOutterColor;
    private final boolean autoLink;
    private final boolean vibrateEnable;
    private final int vibrateAnimatTime;
    private final int depth;
    private final int linkSelectedAnim;

    AttributeDelegate(Context context, AttributeSet attrs) {
        // 获取定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockLayout);
        //自定义手势密码单个键盘
        customeGestureLockPath = a.getString(R.styleable.GestureLockLayout_gesture_lock_custome);
        //背景
        drawBgEnable = a.getBoolean(R.styleable.GestureLockLayout_draw_bg_enable, false);
        bgLineWidth = a.getDimension(R.styleable.GestureLockLayout_bg_line_width, 4);
        bgLineColor = a.getColor(R.styleable.GestureLockLayout_bg_line_color, 0XFF53868B);
        bgShardColors = new int[]{0XFFFFFFFF, bgLineColor, 0XFFFFFFFF};
        //连接线
        linkSelectColor = a.getColor(R.styleable.GestureLockLayout_link_select_color, 0XFFEEB422);
        linkErrorColor = a.getColor(R.styleable.GestureLockLayout_link_error_color, 0XFFFF0000);
        linkWidth = a.getDimension(R.styleable.GestureLockLayout_link_width, 8);
        linkSelectedAnim = a.getResourceId(R.styleable.GestureLockLayout_link_selected_anim,0);

        //Node内外圆颜色
        normalInnerColor = a.getColor(R.styleable.GestureLockLayout_normal_inner_color, 0XFF6B6B6B);
        normalOutterColor = a.getColor(R.styleable.GestureLockLayout_normal_outter_color, 0XFF6B6B6B);
        selectInnerColor = a.getColor(R.styleable.GestureLockLayout_select_inner_color, 0XFFFFFF00);
        selectOutterColor = a.getColor(R.styleable.GestureLockLayout_select_outter_color, 0X99FFD39B);
        errorInnerColor = a.getColor(R.styleable.GestureLockLayout_error_inner_color, 0XFFFF4040);
        errorOutterColor = a.getColor(R.styleable.GestureLockLayout_error_outter_color, 0XAAFF6347);

        autoLink = a.getBoolean(R.styleable.GestureLockLayout_auto_link, false);
        vibrateEnable = a.getBoolean(R.styleable.GestureLockLayout_vibrate_enable, false);
        vibrateAnimatTime = a.getInt(R.styleable.GestureLockLayout_vibrate_animat_time, 20);

        depth = a.getInt(R.styleable.GestureLockLayout_depth, 3);
        a.recycle();
    }

    public int getLockSize() {
        return depth * depth;
    }

    public String getCustomeGestureLockPath() {
        return customeGestureLockPath;
    }

    public int getLinkSelectColor() {
        return linkSelectColor;
    }

    public int getLinkErrorColor() {
        return linkErrorColor;
    }

    public boolean isDrawBgEnable() {
        return drawBgEnable;
    }

    public float getBgLineWidth() {
        return bgLineWidth;
    }

    public int getBgLineColor() {
        return bgLineColor;
    }

    public int[] getBgShardColors() {
        return bgShardColors;
    }

    public float getLinkWidth() {
        return linkWidth;
    }

    public int getNormalInnerColor() {
        return normalInnerColor;
    }

    public int getNormalOutterColor() {
        return normalOutterColor;
    }

    public int getSelectInnerColor() {
        return selectInnerColor;
    }

    public int getSelectOutterColor() {
        return selectOutterColor;
    }

    public int getErrorInnerColor() {
        return errorInnerColor;
    }

    public int getErrorOutterColor() {
        return errorOutterColor;
    }

    public boolean isAutoLink() {
        return autoLink;
    }

    public boolean isVibrateEnable() {
        return vibrateEnable;
    }

    public int getVibrateAnimatTime() {
        return vibrateAnimatTime;
    }

    public int getDepth() {
        return depth;
    }

    public int getLinkSelectedAnim() {
        return linkSelectedAnim;
    }
}
