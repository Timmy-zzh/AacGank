package com.timmy.aacgank.ui.cityselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.timmy.aacgank.R;


/**
 * 右侧字母排序
 * 功能：
 * 1。先绘制所有的字母
 */
public class LetterBar extends View {

    private int mHeight;
    private int letterTextSize = 30;
    private String[] letterArr;
    private int itemHeight;
    private Paint letterPaint;
    private int letterNum;
    private int pointX;
    private Paint.FontMetricsInt fontMetricsInt;
    private int baseLine;

    public LetterBar(Context context) {
        this(context, null);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        letterArr = context.getResources().getStringArray(R.array.LetterList);
        letterNum = letterArr.length;
        //文本大小
        if (attrs != null) {

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        itemHeight = mHeight / letterArr.length;
        Log.d("Timmy", "mHeight: " + mHeight + ",itemHeight:" + itemHeight);


        letterPaint = new Paint();
        letterPaint.setColor(Color.BLUE);
        letterPaint.setTextSize(letterTextSize);
        letterPaint.setTextAlign(Paint.Align.CENTER);

        pointX = getMeasuredWidth() - 60;

        //基准线高度，为 itemHeight -
        Paint.FontMetricsInt fontMetricsInt = letterPaint.getFontMetricsInt();
        int bottom = fontMetricsInt.bottom;
        int top = fontMetricsInt.top;
        int ascent = fontMetricsInt.ascent;
        int descent = fontMetricsInt.descent;
        Log.d("Timmy", "bottom: " + bottom +
                ",top:" + top +
                ",ascent:" + ascent +
                ",descent:" + descent
        );
        baseLine = itemHeight /2 -(bottom + top) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int letterY;
        for (int i = 0; i < letterNum; i++) {
            letterY = itemHeight * i + baseLine;
            Log.d("Timmy", " i: " + i + ",letterY:" + letterY);
            canvas.drawText(letterArr[i], pointX, letterY, letterPaint);
        }
    }
}
