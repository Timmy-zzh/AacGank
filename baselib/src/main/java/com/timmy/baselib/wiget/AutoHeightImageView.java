package com.timmy.baselib.wiget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.timmy.baselib.utils.LogUtils;

/**
 * 为了更好的展示图片,因为图片的宽度是固定为屏幕的一半,高度的话就根据图片的宽高比进行设置
 */
public class AutoHeightImageView extends android.support.v7.widget.AppCompatImageView {
    public AutoHeightImageView(Context context) {
        super(context);
    }

    public AutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Drawable d = getDrawable();
        if (d != null) {
            // ceil not round - avoid thin vertical gaps along the left/right edges
            final int width = MeasureSpec.getSize(widthMeasureSpec);
            //宽度定- 高度根据使得图片的宽度充满屏幕
            final int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            LogUtils.d("AutoHeightImageView>>>width:" + width + ",height:" + height);
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}