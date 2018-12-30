package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;

/**
 * 使用Paint在Canvas上绘制各种图形
 * 其中Paint
 */
public class PaintUseView extends View {

    private Paint mPaint;

    public PaintUseView(Context context) {
        this(context, null);
    }

    public PaintUseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintUseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        //设置alpha透明度，范围为0~255 -- 0为全透明
//        mPaint.setAlpha(255);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
//        mPaint.setDither(true);
        //设置文本大小
//        mPaint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStyle(canvas);
        drawStrokeCap(canvas);
        drawStrokeJoin(canvas);
        drawStrokeMiter(canvas);
    }

    /**
     * 设置画笔倾斜度
     *
     * @param canvas
     */
    private void drawStrokeMiter(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        Path path = new Path();
        path.moveTo(50, 950);
        path.lineTo(250, 1000);
        path.lineTo(50, 1150);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        mPaint.setStrokeMiter(170f);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 多线条连接拐角弧度
     *
     * @param canvas
     */
    private void drawStrokeJoin(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        Path path = new Path();
        path.moveTo(50, 700);
        path.lineTo(250, 750);
        path.lineTo(50, 900);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);

        path.moveTo(300, 700);
        path.lineTo(500, 750);
        path.lineTo(300, 900);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, mPaint);

        path.moveTo(550, 700);
        path.lineTo(750, 750);
        path.lineTo(550, 900);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);
    }

    /**
     * 设置笔刷的图形样式
     * 如圆形样式Cap.ROUND
     * 方形样式Cap.SQUARE
     */
    private void drawStrokeCap(Canvas canvas) {
        mPaint.setStrokeWidth(50);
        canvas.drawLine(50, 400, 800, 400, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(50, 500, 800, 500, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(50, 600, 800, 600, mPaint);
    }

    /**
     * 设置绘制样式
     * FILL            (0),  绘制内容
     * STROKE          (1),  绘制边框
     * FILL_AND_STROKE (2);  一起绘制
     */
    private void drawStyle(Canvas canvas) {
        mPaint.setStrokeWidth(30);
        Rect rect = new Rect(50, 50, 350, 250);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, mPaint);

        Rect rect2 = new Rect(400, 50, 700, 250);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect2, mPaint);

        Rect rect3 = new Rect(750, 50, 1050, 250);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(rect3, mPaint);
    }
}