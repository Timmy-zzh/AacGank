package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;


public class ColorFilterView extends View {

    private Paint mPaint;
    private Bitmap bitmap;

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化
        mPaint = new Paint();
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置是否抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔颜色   --代码中用十六进制0x来表示
        mPaint.setColor(Color.RED);
        //设置线条宽度
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        //设置alpha透明度，范围为0~255 -- 0为全透明
//        mPaint.setAlpha(255);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
//        mPaint.setDither(true);
        //设置文本大小
//        mPaint.setTextSize(60);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_dongman);
    }


    /**
     * 设置颜色过滤 ColorFilter有三个子类去实现ColorMatrixColorFilter、LightingColorFilter和PorterDuffColorFilter
     */
    private void initColorMatrix() {
        /**
         * 第一行表示的R（红色）的向量，
         * 第二行表示的G（绿色）的向量，
         * 第三行表示的B（蓝色）的向量，
         * 最后一行表示A（透明度）的向量，
         * 这一顺序必须要正确不能混淆！这个矩阵不同的位置表示的RGBA值，
         * 其范围在0.0F至2.0F之间，1为保持原图的RGB值。
         * 每一行的第五列数字表示偏移值。
         */
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5f, 0, 0, 0, 0,
                0, 0.5f, 0, 0, 0,
                0, 0, 0.5f, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        /**
         * LightingColorFilter (int mul, int add)
         * mul全称是colorMultiply意为色彩倍增
         * 参数2：add全称是colorAdd意为色彩添加
         */
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00, 0x000000));
        /**
         * PorterDuffColorFilter (int color, PorterDuff.Mode mode)，
         * 参数1：16进制表示的颜色值；
         * 参数2：PorterDuff内部类Mode中的一个常量值，这个值表示混合模式
         */
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.ADD));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 50, 0, mPaint);

        initColorMatrix();
        canvas.drawBitmap(bitmap, 50, 50 + bitmap.getHeight(), mPaint);

        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00, 0x000000));
        canvas.drawBitmap(bitmap, 50, (50 + bitmap.getHeight()) * 2, mPaint);
    }
}