package com.timmy.aacgank.ui.cityselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.aacgank.R;


/**
 * 右侧字母排序
 * 功能：
 * 1。先绘制所有的字母
 * 2.手指滑动指定的字母
 */
public class LetterBar extends View {

    private  Paint selectPaint;
    private  Paint leftPaint;
    private  Paint leftTextPaint;
    private int mHeight;
    private int letterTextSize = 30;
    private String[] letterArr;
    private int itemHeight;
    private Paint letterPaint;
    private int letterNum;
    private int pointX;
    private int baseLine;
    private Paint bgPaint;
    private int currPosition = 0;
    private int oldPosition; //之前选中的字母标示
    private OnLetterChangeListener mListener;
    private int leftBaseLine;

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

        initPaint();
    }

    private void initPaint() {

        bgPaint = new Paint();

        letterPaint = new Paint();
        letterPaint.setColor(Color.BLUE);
        letterPaint.setTextSize(letterTextSize);
        letterPaint.setTextAlign(Paint.Align.CENTER);
//
//        //基准线高度，为 itemHeight -
//        Paint.FontMetricsInt fontMetricsInt = letterPaint.getFontMetricsInt();
//        int bottom = fontMetricsInt.bottom;
//        int top = fontMetricsInt.top;
//        int ascent = fontMetricsInt.ascent;
//        int descent = fontMetricsInt.descent;
//        Log.d("Timmy", "bottom: " + bottom +
//                ",top:" + top +
//                ",ascent:" + ascent +
//                ",descent:" + descent
//        );
//        baseLine = itemHeight / 2 - (bottom + top) / 2;

        selectPaint = new Paint();
        selectPaint.setColor(Color.WHITE);
        selectPaint.setTextSize(letterTextSize);
        selectPaint.setTextAlign(Paint.Align.CENTER);


        leftPaint = new Paint();
        leftPaint.setColor(Color.GREEN);

        leftTextPaint = new Paint();
        leftTextPaint.setColor(Color.WHITE);
        leftTextPaint.setTextSize(letterTextSize * 2);
        leftTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        itemHeight = mHeight / letterArr.length;
        Log.d("Timmy", "mHeight: " + mHeight + ",itemHeight:" + itemHeight);
        pointX = (int) (getMeasuredWidth() - letterTextSize * 1.6);

        //基准线高度，为 itemHeight -
        Paint.FontMetricsInt fontMetricsInt = letterPaint.getFontMetricsInt();
        baseLine = itemHeight / 2 - (fontMetricsInt.bottom + fontMetricsInt.top) / 2;

        Paint.FontMetricsInt leftFontMetricsInt = leftTextPaint.getFontMetricsInt();
        leftBaseLine = itemHeight / 2 - (leftFontMetricsInt.bottom + leftFontMetricsInt.top) / 2;
    }

    /**
     * 1.判断手指的位置是否在字母绘制区域
     * 2.判断手指位置在字母列表那个位置
     * 3。绘制选中字母的效果 -- 右侧选中字母的圆形背景和左侧的圆形
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        currPosition = (int) (y / itemHeight);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //控制触摸范围
                if (x < getMeasuredWidth() - letterTextSize * 2.5) {
                    return false;
                }
                //绘制
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldPosition != currPosition && mListener != null) {
                    mListener.onLetterChange(letterArr[currPosition]);
                }
                oldPosition = currPosition;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制字母和背景
        drawLetter(canvas);
        //绘制选中字母
        drawSelectLetter(canvas);
    }

    private void drawLetter(Canvas canvas) {
        //绘制背景
        RectF rectF = new RectF();
        rectF.left = (int) (pointX - letterTextSize);
        rectF.right = (int) (pointX + letterTextSize);
        rectF.top = 0;
        rectF.bottom = mHeight;

        bgPaint.reset();
        bgPaint.setColor(Color.RED);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 30, 30, bgPaint);

        bgPaint.reset();
        bgPaint.setColor(Color.parseColor("#2d2d2d"));
        bgPaint.setStrokeWidth(4);
        bgPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, 30, 30, bgPaint);


        int letterY;
        for (int i = 0; i < letterNum; i++) {
            if (i == currPosition) {
                continue;
            }
            letterY = itemHeight * i + baseLine;
            Log.d("Timmy", " i: " + i + ",letterY:" + letterY);
            canvas.drawText(letterArr[i], pointX, letterY, letterPaint);
        }
    }

    /**
     * 1.先绘制背景
     * 2。绘制选中的文字
     * 3。绘制左侧的圆形
     *
     * @param canvas
     */
    private void drawSelectLetter(Canvas canvas) {
        bgPaint.reset();
        bgPaint.setStyle(Paint.Style.FILL);
        int cx = pointX;

        int cy = (int) (itemHeight * (currPosition + 0.5) + 0.5);
        int radius = (int) (letterTextSize * 0.7 + 0.5f);
        canvas.drawCircle(cx, cy, radius, bgPaint);

        int letterY = itemHeight * currPosition + baseLine;
        Log.d("Timmy", " drawSelectLetter i: " + currPosition + ",letterY:" + letterY);
        canvas.drawText(letterArr[currPosition], pointX, letterY, selectPaint);

        int leftCx = pointX - 3 * letterTextSize;
        int leftRadius = letterTextSize * 2;
        canvas.drawCircle(leftCx, cy, leftRadius, leftPaint);

        int leftLetterY = itemHeight * currPosition + leftBaseLine;
        canvas.drawText(letterArr[currPosition], leftCx, leftLetterY, leftTextPaint);
    }

    //RecyclerView选中了某个字母
    public void selectLetter(String letters) {
        for (int i = 0; i < letterArr.length; i++) {
            if (letters.equalsIgnoreCase(letterArr[i]) && currPosition != oldPosition) {
                currPosition = i;
                oldPosition = currPosition;
                invalidate();
            }
        }
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.mListener = listener;
    }

}
