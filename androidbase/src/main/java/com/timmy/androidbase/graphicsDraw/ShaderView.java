package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;

public class ShaderView extends View {

    private Paint mPaint;
    private Bitmap bitmap;
    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE};

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_dongman);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * tileX、tileY参数Shader.TileMode有三个：
         CLAMP 重复最后一个颜色至最后
         MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
         REPEAT 重复着色的图像水平或垂直方向
         */
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
        mPaint.setShader(bitmapShader);
        Rect rect = new Rect(0, 0
                , bitmap.getWidth() * 2, bitmap.getHeight() * 2);
        canvas.drawRect(rect, mPaint);

        /**线性渐变
         * x0, y0, 起始点
         *  x1, y1, 结束点
         * int[]  colors, 中间依次要出现的几个颜色
         * float[] positions,数组大小跟colors数组一样大，中间依次摆放的几个颜色分别放置在那个位置上(参考比例从左往右)
         *    tile
         */
        canvas.translate(0, bitmap.getHeight() * 2 + 50);
        LinearGradient linearGradient = new LinearGradient(0, 0, 400, 400, colors, null, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient);
        canvas.drawRect(0, 0, 400, 400, mPaint);

        /**
         * 设置光束从中心向四周发散的辐射渐变效果
         */
        canvas.translate(0, 400 + 50);
        RadialGradient radialGradient = new RadialGradient(150, 150, 100, colors, null, Shader.TileMode.MIRROR);
        mPaint.setShader(radialGradient);
        canvas.drawCircle(150, 150, 100, mPaint);

        /**
         *设置绕着某中心点进行360度旋转渐变效果
         */
        canvas.translate(0, 200 + 50);
        SweepGradient sweepGradient = new SweepGradient(150, 150, colors, null);
        mPaint.setShader(sweepGradient);
        canvas.drawCircle(150, 150, 100, mPaint);

        /**
         * 混合
         * //shaderA对应下层图形，shaderB对应上层图形
         ComposeShader(Shader shaderA, Shader shaderB, Xfermode mode)
         ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
         */
        canvas.translate(0, 250 + 50);
        ComposeShader composeShader = new ComposeShader(linearGradient, bitmapShader, PorterDuff.Mode.SRC_OVER);
        mPaint.setShader(composeShader);
        canvas.drawRect(0, 0, 800, 1000, mPaint);
    }
}
