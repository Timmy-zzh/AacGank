package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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

/**
 * 使用Paint在Canvas上绘制各种图形
 * 其中Paint
 */
public class MaskFilterView extends View {

    private Paint mPaint;

    public MaskFilterView(Context context) {
        this(context, null);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        /**
         * Paint的setMaskFilter不被GPU支持,为了确保画笔的setMaskFilter能供起效，
         * 我们需要禁用掉GPU硬件加速或AndroidManifest.xml文件中设置android:hardwareAccelerated为false
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //View从API Level 11才加入setLayerType方法
            //设置软件渲染模式绘图
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        /**
         * 设置滤镜的效果，MaskFilter有两个子类实现BlurMaskFilter, EmbossMaskFilter
         * 参数1：模糊延伸半径，必须>0；
         * 参数2：有四种枚举
         * NORMAL，同时绘制图形本身内容+内阴影+外阴影,正常阴影效果
         * INNER，绘制图形内容本身+内阴影，不绘制外阴影
         * OUTER，不绘制图形内容以及内阴影，只绘制外阴影
         * SOLID，只绘制外阴影和图形内容本身，不绘制内阴影
         * BlurMaskFilter绘制的Bitmap基本完全不受影响
         */
        mPaint.setMaskFilter(new BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID));
        canvas.drawRect(50, 50, 500, 550, mPaint);

        /**
         *  设置浮雕滤镜效果，
         *  参数1：光源指定方向；
         *  参数2:环境光亮度，取值0-1,值越小越暗；
         *  参数3：镜面高光反射系数，值越小反射越强；
         *  参数4：模糊延伸半径
         */
        mPaint.setMaskFilter(
                new EmbossMaskFilter(new float[]{1, 1, 1},
                        0.4f,
                        8f,
                        23f));
        canvas.drawRect(50, 600, 500, 1000, mPaint);
    }

    /**
     * @param canvas
     */
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
////        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_food);
////        canvas.drawBitmap(bitmap, 0, 0, mPaint);
////
////        canvas.drawText("Paint API使用", 50, 200, mPaint);
//
////        int radius = Math.min(bitmap.getHeight(), bitmap.getWidth());
////        canvas.drawCircle(radius, radius, radius, mPaint);
//
////        canvas.drawCircle(500, 500, 300, mPaint);
//
//        //画背景色
//        canvas.drawARGB(255, 139, 139, 186);
//
//        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
//        //绘制黄色圆形
//        int radius = 100;
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawCircle(radius, radius, radius, mPaint);
//
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//
//        //绘制蓝色矩形
//        mPaint.setColor(Color.BLUE);
//        RectF rectf = new RectF(radius, radius, radius * 2.5f, radius * 2.5f);
//        canvas.drawRect(rectf, mPaint);
//        mPaint.setXfermode(null);
//
//        canvas.restoreToCount(saved);
//
//    }
}
