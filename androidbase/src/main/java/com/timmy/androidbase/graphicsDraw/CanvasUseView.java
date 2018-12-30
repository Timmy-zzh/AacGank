package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.baselib.utils.LogUtils;

/**
 * 在画布上绘制各种图形
 */
public class CanvasUseView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private Rect bitSrc;
    private Rect bitDes;

    public CanvasUseView(Context context) {
        this(context, null);
    }

    public CanvasUseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasUseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //1.创建画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);

        //图片绘制初始化
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_dongman);

        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        //原始图片资源大小
        bitSrc = new Rect(bitmapWidth / 2, 0, bitmapWidth, bitmapHeight / 2);
        int bitLeft = bitmapHeight + 100;
        int bitTop = 1150;
        bitDes = new Rect(bitLeft, bitTop, bitLeft + bitmapWidth, bitTop + bitmapHeight);

    }


    //2.在Canvas上进行图形绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.d("width:" + canvas.getWidth() + " ,height:" + canvas.getHeight());
        //绘制画布背景色
        canvas.drawColor(Color.YELLOW);

        drawText(canvas);
        drawBitmap(canvas);
        drawPath(canvas);
        drawRoundRect(canvas);
        drawRect(canvas);
        drawCircle(canvas);
        drawLine(canvas);
        drawPoint(canvas);
        drawScale(canvas);
    }

    /**
     * 使用画布的旋转，位移实现
     */
    private void drawScale(Canvas canvas) {
        paint.setStrokeWidth(4);
        canvas.save();
        int top = 1800;
        int radius = 200;
        canvas.drawLine(0, top, canvas.getWidth(), top, paint);

        canvas.translate(canvas.getWidth() / 2, top + radius + 50);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, radius, paint);

        //保存刚才画布绘制的图形
        canvas.save();
        canvas.translate(-150, -150);
        //绘制路径文字
        Path path = new Path();
        path.addArc(new RectF(0, 0, 300, 300), -180, 180);
        paint.setTextSize(30);
        canvas.drawTextOnPath("Android Timmy 技术栈 www", path, 50, 0, paint);

        //画布位置回滚到上一次
        canvas.restore();

        //小刻度
        Paint temPaint = new Paint(paint);
        temPaint.setStrokeWidth(2);

        float y = radius;
        int count = 60;
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0, y, 0, y + 25f, temPaint);
                canvas.drawText(String.valueOf(i / 5 + 1), -10f, y + 60f, temPaint);
            } else {
                canvas.drawLine(0, y, 0, y + 15f, temPaint);
            }
            canvas.rotate(360 / count, 0f, 0f);
        }

        //绘制指针
        temPaint.setStyle(Paint.Style.FILL);
        temPaint.setColor(Color.GREEN);
        temPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 25, temPaint);
        temPaint.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, 15, temPaint);
        canvas.drawLine(0, 30, 0, -135, paint);

    }

    private void drawText(Canvas canvas) {
        paint.setTextSize(60);
        int y = 1250 + 350;
        paint.setStrokeWidth(3);
        canvas.drawLine(50, y, 850, y, paint);
        canvas.drawText("Canvas  img画布学习", 50, y, paint);
        canvas.drawText("画刻度表", 50, y + 100, paint);
    }

    private void drawBitmap(Canvas canvas) {
        //绘制整个图片
        canvas.drawBitmap(bitmap, 50, 1150, paint);

        //绘制原始图片的一部分，但是大小和原始图片一样
        canvas.drawBitmap(bitmap, bitSrc, bitDes, paint);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(50, 900);
        path.lineTo(250, 900);
        path.lineTo(150, 1100);
        //路径闭合
        path.close();
        canvas.drawPath(path, paint);

        //绘制不同角度的圆角矩形
        Path roundRectPath = new Path();
        float left = 300;
        float top = 900;
        float right = 600;
        float bottom = 1100;

        RectF rectf = new RectF(left, top, right, bottom);

        int deep = 20;
        float[] radii = {
                2 * deep, 3 * deep,
                4 * deep, deep,
                2 * deep, 3 * deep,
                1, 1
        };
        roundRectPath.addRoundRect(rectf, radii, Path.Direction.CCW);
        canvas.drawPath(roundRectPath, paint);
    }

    private void drawRoundRect(Canvas canvas) {
        float left = 50;
        float top = 650;
        float right = 450;
        float bottom = 850;

        RectF rectf = new RectF(left, top, right, bottom);
        float rx = 15;
        float ry = 15;
        canvas.drawRoundRect(rectf, rx, ry, paint);
    }

    private void drawRect(Canvas canvas) {
        float left = 50;
        float top = 400;
        float right = 450;
        float bottom = 600;
//        canvas.drawRect(left, top, right, bottom, paint);

//        RectF rectf = new RectF(left, top, right, bottom);
//        canvas.drawRect(rectf, paint);

        Rect rect = new Rect(50, 400, 450, 600);
        canvas.drawRect(rect, paint);
    }

    private void drawCircle(Canvas canvas) {
        //圆
        canvas.drawCircle(150, 250, 100, paint);

        //椭圆
        RectF oval = new RectF(300, 150, 600, 350);
        canvas.drawOval(oval, paint);

        //扇形
        RectF oval1 = new RectF(650, 150, 850, 350);
//        RectF oval1 = new RectF(650, 150, 1000, 350);
        float startAngle = 0;
        float sweepAngle = 60;
        //顺时针方向
        canvas.drawArc(oval1, startAngle, sweepAngle, true, paint);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(0, 100, 600, 100, paint);
    }

    //画点
    private void drawPoint(Canvas canvas) {
        paint.setStrokeWidth(15);
        canvas.drawPoint(50, 50, paint);
    }
}
