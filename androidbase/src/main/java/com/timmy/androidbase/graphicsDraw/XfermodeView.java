package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;

public class XfermodeView extends View {

    private Paint mPaint;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        canvas.drawColor(Color.GRAY);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        //绘制黄色圆形
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(150, 150, 100, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //绘制蓝色矩形
        mPaint.setColor(Color.BLUE);
        Rect rectf = new Rect(150, 150, 300, 300);
        canvas.drawRect(rectf, mPaint);
        // 还原混合模式
        mPaint.setXfermode(null);
        canvas.restoreToCount(saved);
    }
}
