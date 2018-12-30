package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 轨迹Path的使用
 */
public class PathUseView extends View {

    private Paint mPaint;
    private Path mPath;

    public PathUseView(Context context) {
        this(context, null);
    }

    public PathUseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathUseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(40);

        mPath = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("二阶贝塞尔曲线", 50, 50, mPaint);

        mPath.moveTo(0, 200);
        mPath.quadTo(200, 100, 400, 200);
        canvas.drawPath(mPath, mPaint);
        canvas.translate(0, 200);
        mPath.reset();

        canvas.drawText("三阶届贝塞尔曲线", 50, 50, mPaint);
        mPath.moveTo(0, 200);
        mPath.cubicTo(100, 100, 250, 455, 400, 200);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();

        canvas.translate(0, 400);
        canvas.drawText("PathMeasure", 50, 50, mPaint);
        RectF rect = new RectF(300,300,600,600);
        mPath.addRect(rect, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(mPath,false);
        canvas.drawPath(mPath,mPaint);
    }
}
