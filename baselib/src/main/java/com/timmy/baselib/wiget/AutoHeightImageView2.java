package com.timmy.baselib.wiget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.timmy.baselib.utils.LogUtils;

/**
 * 为了更好的展示图片,因为图片的宽度是固定为屏幕的一半,高度的话就根据图片的宽高比进行设置
 * 直接让ImageView自己去测量高度
 */
public class AutoHeightImageView2 extends android.support.v7.widget.AppCompatImageView {
    private int initWidth;
    private int initHeight;

    public AutoHeightImageView2(Context context) {
        super(context);
    }

    public AutoHeightImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInitSize(int initWidth, int initHeight) {
        this.initWidth = initWidth;
        this.initHeight = initHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (initWidth > 0 && initHeight > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            float scale = (float) initHeight / (float) initWidth;
            if (width > 0){
                height = (int) ((float)width * scale);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}