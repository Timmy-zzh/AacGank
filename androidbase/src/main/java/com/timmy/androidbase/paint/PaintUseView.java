package com.timmy.androidbase.paint;

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
     * 设置路径效果，PathEffect有6个子类实现
     * ComposePathEffect, CornerPathEffect, DashPathEffect, DiscretePathEffect, PathDashPathEffect, SumPathEffect
     */
    private void initPathEffect() {
        // 实例化路径
        mPath = new Path();
        // 定义路径的起点
        mPath.moveTo(10, 50);
        // 定义路径的各个点
        for (int i = 0; i <= 30; i++) {
            mPath.lineTo(i * 35, (float) (Math.random() * 100));
        }
        //什么都不处理
        pathEffects[0] = null;
        //虚实线间距
        float mPhase = 5;
        //参数1：线段之间的圆滑程度
        pathEffects[1] = new CornerPathEffect(10);
        //参数1：间隔线条长度(length>=2)，如float[] {20, 10}的偶数参数20定义了我们第一条实线的长度，
        //而奇数参数10则表示第一条虚线的长度，后面不再有数据则重复第一个数以此往复循环；参数2：虚实线间距
        pathEffects[2] = new DashPathEffect(new float[]{20, 10}, mPhase);
        //参数1:值越小杂点越密集；参数2:杂点突出的大小，值越大突出的距离越大
        pathEffects[3] = new DiscretePathEffect(5.0f, 10.0f);
        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CCW);
        //定义路径虚线的样式,参数1：path；参数2：实线的长度；参数3：虚实线间距
        pathEffects[4] = new PathDashPathEffect(path, 20, mPhase, PathDashPathEffect.Style.ROTATE);
        pathEffects[5] = new ComposePathEffect(pathEffects[2], pathEffects[4]);
        //ComposePathEffect和SumPathEffect都可以用来组合两种路径效果,具体区别（不知道如何描述）小伙伴们自己试试
        pathEffects[6] = new SumPathEffect(pathEffects[4], pathEffects[3]);
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
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

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
        mPaint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.ADD));
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_food);
//        canvas.drawBitmap(bitmap, 0, 0, mPaint);
//
//        canvas.drawText("Paint API使用", 50, 200, mPaint);

        /*
         * 绘制路径效果PathEffect
         */
//        for (int i = 0; i < pathEffects.length; i++) {
//            mPaint.setPathEffect(pathEffects[i]);
//            canvas.drawPath(mPath, mPaint);
//            // 每绘制一条将画布向下平移250个像素
//            canvas.translate(0, 250);
//        }

//        int radius = Math.min(bitmap.getHeight(), bitmap.getWidth());
//        canvas.drawCircle(radius, radius, radius, mPaint);

//        canvas.drawCircle(500, 500, 300, mPaint);

        //画背景色
        canvas.drawARGB(255, 139, 139, 186);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        //绘制黄色圆形
        int radius = 100;
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(radius, radius, radius, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        //绘制蓝色矩形
        mPaint.setColor(Color.BLUE);
        RectF rectf = new RectF(radius, radius, radius * 2.5f, radius * 2.5f);
        canvas.drawRect(rectf, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(saved);

    }
}
