package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

        mPath = new Path();

        initPath();
    }

    /**
     *
     */
    private void initPath() {
        /**
         * 画线操作
         */
        // 设置起始点位置
        mPath.moveTo(50, 50);
        //设置连接点位置
        mPath.lineTo(500, 500);

        //添加路径
        //添加圆弧
        mPath.addArc(new RectF(400, 400, 500, 500), 45, -180);

        //贝塞尔曲线
//        mPath.quadTo();

//        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, 50, 500, 500, mPaint);

        canvas.drawPath(mPath, mPaint);
    }
}
