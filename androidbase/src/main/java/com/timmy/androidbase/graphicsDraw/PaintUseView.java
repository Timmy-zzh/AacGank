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
    private Path mPath;
    private PathEffect[] pathEffects = new PathEffect[7];
    private Bitmap bitmap;

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


    private void init2() {
        //初始化
        mPaint = new Paint();
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置是否抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔颜色   --代码中用十六进制0x来表示
//        mPaint.setColor(Color.BLACK);
        //设置alpha透明度，范围为0~255 -- 0为全透明
//        mPaint.setAlpha(255);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
//        mPaint.setDither(true);
        /**
         * 设置绘制样式
         *  FILL            (0),  绘制内容
         *  STROKE          (1),  绘制边框
         *  FILL_AND_STROKE (2);  一起绘制
         */
//        mPaint.setStyle(Paint.Style.STROKE);
        //设置文本大小
//        mPaint.setTextSize(60);
        //设置线条宽度
//        mPaint.setStrokeWidth(5);

        /**
         * 设置笔刷的图形样式
         * 如圆形样式Cap.ROUND
         * 方形样式Cap.SQUARE
         */
//        mPaint.setStrokeCap(Paint.Cap.ROUND);

        /**
         * 设置绘制时各图形的结合方式，如影响矩形角的外轮廓
         */
//        mPaint.setStrokeJoin(Paint.Join.ROUND);

        /**
         * 设置文本对齐
         */
//        mPaint.setTextAlign(Paint.Align.CENTER);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_dongman);
//        initColorMatrix();
//        initMaskFilter();
//        initPathEffect();
//        initShaper();
        initXfermode();
    }

    /**
     * 图像混合模式，Xfermode 有个子类去实现PorterDuffXfermode
     */
    private void initXfermode() {

    }

    /**
     * 设置着色器
     * 设置着色器，Shader 子类实现有BitmapShader, ComposeShader, LinearGradient, RadialGradient, SweepGradient
     */
    private void initShaper() {

        /**
         * tileX、tileY参数Shader.TileMode有三个：
         CLAMP 重复最后一个颜色至最后
         MIRROR 重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
         REPEAT 重复着色的图像水平或垂直方向
         */
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);

        /**
         * 设置线性渐变效果
         */
        LinearGradient linearGradient = new LinearGradient(0, 0, 500, 500, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);

        /**
         * 设置光束从中心向四周发散的辐射渐变效果
         */
        RadialGradient radialGradient = new RadialGradient(500, 500, 400, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);

        /**
         *设置绕着某中心点进行360度旋转渐变效果
         */
        SweepGradient sweepGradient = new SweepGradient(500, 500, Color.BLUE, Color.GREEN);

        /**
         * 混合
         * //shaderA对应下层图形，shaderB对应上层图形
         ComposeShader(Shader shaderA, Shader shaderB, Xfermode mode)
         ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)
         */

        mPaint.setShader(sweepGradient);
    }


    /**
     * 设置滤镜的效果，MaskFilter有两个子类实现BlurMaskFilter, EmbossMaskFilter
     */
    private void initMaskFilter() {
        /**
         * 参数1：模糊延伸半径，必须>0；
         * 参数2：有四种枚举
         * NORMAL，同时绘制图形本身内容+内阴影+外阴影,正常阴影效果
         * INNER，绘制图形内容本身+内阴影，不绘制外阴影
         * OUTER，不绘制图形内容以及内阴影，只绘制外阴影
         * SOLID，只绘制外阴影和图形内容本身，不绘制内阴影
         * BlurMaskFilter绘制的Bitmap基本完全不受影响
         */
//        mPaint.setMaskFilter(new BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID));

        /**
         * Paint的setMaskFilter不被GPU支持,为了确保画笔的setMaskFilter能供起效，
         * 我们需要禁用掉GPU硬件加速或AndroidManifest.xml文件中设置android:hardwareAccelerated为false
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //View从API Level 11才加入setLayerType方法
            //设置软件渲染模式绘图
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //设置浮雕滤镜效果，参数1：光源指定方向；参数2:环境光亮度，取值0-1,值越小越暗；参数3：镜面高光反射系数，值越小反射越强；参数4：模糊延伸半径
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 8f, 3f));

    }

//    /**
//     * @param canvas
//     */
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
