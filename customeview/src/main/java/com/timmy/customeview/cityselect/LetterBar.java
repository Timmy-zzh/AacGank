package com.timmy.customeview.cityselect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.baselib.utils.DensityUtil;
import com.timmy.customeview.R;


/**
 * 右侧字母排序
 * 功能：
 * 1。先绘制所有的字母
 * 2.手指滑动指定的字母
 */
public class LetterBar extends View {

    private Paint selectPaint;
    private Paint leftBgPaint;
    private Paint leftTextPaint;
    private int letterTextSize = 30;
    private String[] letterArr;
    private int itemHeight;
    private Paint letterPaint;
    private int letterNum;
    private int pointX;
    private int baseLine;
    private Paint letterBgPaint;
    private int currPosition = -1;
    private int oldPosition; //之前选中的字母标示
    private OnLetterChangeListener mListener;
    private int leftBaseLine;
    private int letterTextColor;
    private int selectTextColor;
    private int hintTextSize;
    private int hintTextColor;
    private int hintBgColor;
    private int paddingTop;
    private int paddingBottom;
    private Path leftBgPath;

    public LetterBar(Context context) {
        this(context, null);
    }

    public LetterBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        letterArr = context.getResources().getStringArray(R.array.LetterList);
        letterNum = letterArr.length;
        //文本大小
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterBar);
            letterTextSize = typedArray.getDimensionPixelSize(R.styleable.LetterBar_letter_text_size, 30);
            letterTextColor = typedArray.getColor(R.styleable.LetterBar_letter_text_color, 0XFFA008);
            selectTextColor = typedArray.getColor(R.styleable.LetterBar_select_text_color, 0XFFFFFF);
            hintTextSize = typedArray.getDimensionPixelSize(R.styleable.LetterBar_hint_text_size, 48);
            hintTextColor = typedArray.getColor(R.styleable.LetterBar_hint_text_color, 0XFFFFFF);
            hintBgColor = typedArray.getColor(R.styleable.LetterBar_hint_bg_color, 0XD8D8D8);
            typedArray.recycle();
        }

        initPaint();
    }

    private void initPaint() {
        letterBgPaint = new Paint();

        letterPaint = new Paint();
        letterPaint.setColor(letterTextColor);
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
        selectPaint.setColor(selectTextColor);
        selectPaint.setTextSize(letterTextSize);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        leftTextPaint = new Paint();
        leftTextPaint.setColor(hintTextColor);
        leftTextPaint.setTextSize(hintTextSize);
        leftTextPaint.setTextAlign(Paint.Align.CENTER);

        leftBgPaint = new Paint();
        leftBgPaint.setColor(hintBgColor);

        leftBgPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        paddingTop = DensityUtil.dp2px(30);
        paddingBottom = DensityUtil.dp2px(20);

        int letterHeight = getMeasuredHeight() - paddingTop - paddingBottom;

        itemHeight = letterHeight / letterArr.length;
//        Log.d("Timmy", "mHeight: " + getMeasuredHeight() + ",itemHeight:" + itemHeight);
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
        currPosition = (int) ((y - paddingTop) / itemHeight);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //控制触摸范围
                if (x < getMeasuredWidth() - letterTextSize * 2.5 || y < paddingTop) {
                    return false;
                }
                if (currPosition >= letterNum || currPosition < 0) {
                    return true;
                }
                if (oldPosition != currPosition && mListener != null) {
                    mListener.onLetterChange(letterArr[currPosition]);
                }
                oldPosition = currPosition;
                //绘制
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currPosition >= letterNum || currPosition < 0) {
                    return true;
                }
                if (oldPosition != currPosition && mListener != null) {
                    mListener.onLetterChange(letterArr[currPosition]);
                }
                oldPosition = currPosition;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                currPosition = -1;
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
        if (currPosition >= 0) {
            drawSelectLetter(canvas);
        }
    }

    private void drawLetter(Canvas canvas) {
        //绘制背景
        RectF rectF = new RectF();
        rectF.left = pointX - letterTextSize;
        rectF.right = pointX + letterTextSize;
        rectF.top = paddingTop;
        rectF.bottom = getMeasuredHeight() - paddingBottom;

        letterBgPaint.reset();
        letterBgPaint.setColor(Color.parseColor("#f5f5f5"));
        letterBgPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 30, 30, letterBgPaint);

        letterBgPaint.reset();
        letterBgPaint.setColor(Color.parseColor("#2d2d2d"));
        letterBgPaint.setStrokeWidth(2);
        letterBgPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, 30, 30, letterBgPaint);


        int letterY;
        for (int i = 0; i < letterNum; i++) {
            if (i == currPosition) {
                continue;
            }
            letterY = itemHeight * i + baseLine + paddingTop;
//            Log.d("Timmy", " i: " + i + ",letterY:" + letterY);
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
        selectPaint.reset();
        selectPaint.setStyle(Paint.Style.FILL);
        selectPaint.setColor(letterTextColor);
        int cx = pointX;
        int cy = (int) (itemHeight * (currPosition + 0.5) + 0.5 + paddingTop);
        int radius = (int) (letterTextSize * 0.7 + 0.5f);
        canvas.drawCircle(cx, cy, radius, selectPaint);

        selectPaint.reset();
        selectPaint.setColor(selectTextColor);
        selectPaint.setTextSize(letterTextSize);
        selectPaint.setTextAlign(Paint.Align.CENTER);
        int letterY = itemHeight * currPosition + baseLine + paddingTop;
        canvas.drawText(letterArr[currPosition], pointX, letterY, selectPaint);

        //绘制背景
        int leftCx = (int) (pointX - 1.5 * letterTextSize);//提示图形距离右侧的位置
        int hintRadius = DensityUtil.dp2px(20);
        int tempX = DensityUtil.dp2px(26); //切线距离圆心的位置

        leftBgPath.reset();
        leftBgPath.moveTo(leftCx, cy);
        RectF rectF = new RectF();
        rectF.left = leftCx - tempX - hintRadius;
        rectF.right = leftCx - tempX + hintRadius;
        rectF.top = cy - hintRadius;
        rectF.bottom = cy + hintRadius;
        leftBgPath.arcTo(rectF, 60, 240);

        leftBgPath.close();
        canvas.drawPath(leftBgPath, leftBgPaint);

        int leftLetterY = itemHeight * currPosition + leftBaseLine + paddingTop;
        canvas.drawText(letterArr[currPosition], leftCx - tempX, leftLetterY, leftTextPaint);
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
