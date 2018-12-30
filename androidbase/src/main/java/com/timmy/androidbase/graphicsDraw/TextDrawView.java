package com.timmy.androidbase.graphicsDraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.baselib.utils.LogUtils;

public class TextDrawView extends View {

    private Paint mPaint;

    public TextDrawView(Context context) {
        this(context, null);
    }

    public TextDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrikeThruText(true);
        canvas.drawText("文本删除线", 50, 50, mPaint);
        mPaint.setStrikeThruText(false);
//        mPaint.reset();

        mPaint.setUnderlineText(true);
        canvas.drawText("文本下划线", 50, 100, mPaint);
        mPaint.setUnderlineText(false);

        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        canvas.drawText("字体类型", 50, 150, mPaint);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        canvas.drawText("粗体", 350, 150, mPaint);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        //文字倾斜
//        mPaint.setTextSkewX(-0.25f);
        canvas.drawText("斜体", 550, 150, mPaint);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        //加载自定义字体
//        Typeface typeface = Typeface.create("", Typeface.NORMAL);

        canvas.translate(0, 200);
        canvas.drawLine(0, 0, 800, 0, mPaint);
//        文本对其方式
        canvas.drawLine(200, 0, 200, 350, mPaint);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("左对齐", 200, 100, mPaint);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("中对齐", 200, 200, mPaint);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("右对齐", 200, 300, mPaint);
        mPaint.setTextAlign(Paint.Align.LEFT);

        canvas.translate(0, 350);
        //1计算文本长度
        String content = "天行健，君子以自强不息";
        float width1 = mPaint.measureText(content);
        LogUtils.d("width1:" + width1);
        //2
        Rect rect = new Rect();
        mPaint.getTextBounds(content, 0, content.length(), rect);
        LogUtils.d("width:" + rect.width() + " ,height:" + rect.height());

        LogUtils.d("width3:" + getTextWidth(mPaint, content));
//        mPaint.breakText(content, )

        canvas.drawText("获取文本宽高", 50, 50, mPaint);

        //基准线问题
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float ascent = fontMetrics.ascent;
        float leading = fontMetrics.leading;
        float descent = fontMetrics.descent;
        float bottom = fontMetrics.bottom;

        LogUtils.d("ascent:" + ascent + " ,top:" + top
                + " ,descent:" + descent + " ,leading:" + leading
                + " ,bottom:" + bottom
        );
        canvas.drawText("基准线 FontMetrics:", 50, 100, mPaint);
        canvas.drawText("FontMetrics.top: " + top, 50, 150, mPaint);
        canvas.drawText("FontMetrics.ascent: " + ascent, 50, 200, mPaint);
        canvas.drawText("FontMetrics.leading: " + leading, 50, 250, mPaint);
        canvas.drawText("FontMetrics.descent: " + descent, 50, 300, mPaint);
        canvas.drawText("FontMetrics.bottom: " + bottom, 50, 350, mPaint);

    }

    //3. 精确计算文字的宽度：
    public static int getTextWidth(Paint mPaint, String str) {
        float iSum = 0;
        if (str != null && !str.equals("")) {
            int len = str.length();
            float widths[] = new float[len];
            mPaint.getTextWidths(str, widths);
            for (int i = 0; i < len; i++) {
                iSum += Math.ceil(widths[i]);
            }
        }
        return (int) iSum;
    }
}
