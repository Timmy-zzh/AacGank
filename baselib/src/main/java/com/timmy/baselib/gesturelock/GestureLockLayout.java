package com.timmy.baselib.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GestureLockLayout extends ViewGroup {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_SELECTED = 1;
    public static final int STATE_ERROR = 2;

    //连接节点集合
    private final List<GestureLockView> nodeList = new ArrayList<>(); // 已经连线的节点链表
    private float lastX; // 当前手指坐标x
    private float lastY; // 当前手指坐标y
    //结果回调监听器接口
    private GestureCallback callback;
    //    //震动管理器
    private Vibrator vibrator;
    //画线用的画笔
    private Paint mLinkPaint;
    //绘制背景的画笔
    private Paint bgPaint;
    private float specSize;
    private int mCurrStatus = 0;
    float[] position = new float[]{0f, 0.5f, 1.0f};
    private AttributeDelegate delegate;

    /**
     * 构造函数
     */
    public GestureLockLayout(@NonNull Context context) {
        super(context);
        init(context, null, 0);
    }

    public GestureLockLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GestureLockLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        delegate = new AttributeDelegate(context, attrs);
        // 初始化振动器
        if (delegate.isVibrateEnable() && !isInEditMode()) {
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }
        // 初始化画笔
        mLinkPaint = new Paint(Paint.DITHER_FLAG);
        mLinkPaint.setStyle(Style.STROKE);
        mLinkPaint.setStrokeWidth(delegate.getLinkWidth());
        mLinkPaint.setColor(delegate.getLinkSelectColor());
        mLinkPaint.setAntiAlias(true); // 抗锯齿

        bgPaint = new Paint(Paint.DITHER_FLAG);
        bgPaint.setStyle(Style.STROKE);
        bgPaint.setStrokeWidth(delegate.getBgLineWidth());
        bgPaint.setColor(delegate.getBgLineColor());
        bgPaint.setAntiAlias(true); // 抗锯齿

        // 清除FLAG，否则 onDraw() 不会调用，原因是 ViewGroup 默认透明背景不需要调用 onDraw()
        setWillNotDraw(false);

        try {
            Class<?> gestureLockClass;
            try {
                if (!TextUtils.isEmpty(delegate.getCustomeGestureLockPath())) {
                    gestureLockClass = Class.forName(delegate.getCustomeGestureLockPath());
                } else {
                    gestureLockClass = DefaultLockView.class;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                gestureLockClass = DefaultLockView.class;
            }
            Constructor constructor = gestureLockClass.getConstructor(Context.class);

            // 构建node,通过xml中传入控件的全类名，进行反射创建
            for (int n = 0; n < delegate.getLockSize(); n++) {
                GestureLockView gestureLockView = (GestureLockView) constructor.newInstance(getContext());
                gestureLockView.setAttributeDelegate(n + 1, delegate, vibrator);
                addView(gestureLockView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 我们让高度等于宽度 - 方法有待验证
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = measureSize(widthMeasureSpec); // 测量宽度
        specSize = size / (3 * delegate.getDepth() - 1);
        setMeasuredDimension(size, size);
    }

    /**
     * 测量长度
     */
    private int measureSize(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec); // 得到模式
        int specSize = MeasureSpec.getSize(measureSpec); // 得到尺寸
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                return specSize;
            case MeasureSpec.UNSPECIFIED:
            default:
                return 0;
        }
    }

    /**
     * 在这里进行node的布局
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            specSize = (right - left) / (3 * delegate.getDepth() - 1);
            for (int n = 0; n < delegate.getLockSize(); n++) {
                GestureLockView node = (GestureLockView) getChildAt(n);
                // 获取3*3宫格内坐标
                int row = n / delegate.getDepth();  //行 0 0 0 1 1 1 2 2 2
                int col = n % delegate.getDepth();  //列 0 1 2 0 1 2 0 1 2
                // 计算实际的坐标
                int l = (int) (col * 3 * specSize);
                int t = (int) (row * 3 * specSize);
                int r = (int) (l + specSize * 2);
                int b = (int) (t + specSize * 2);
                node.layout(l, t, r, b);
            }
        }
    }

    /**
     * 系统绘制回调-主要绘制连线
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //画底线
        if (delegate.isDrawBgEnable()) {
            int count = 3 * delegate.getDepth() - 1;
            for (int i = 1; i < count; i++) {
                //行
                float startX = 0;
                float startY = i * specSize;
                float stopX = specSize * count;
                float stopY = i * specSize;
                bgPaint.setShader(new LinearGradient(startX, startY, stopX, stopY, delegate.getBgShardColors(), position, Shader.TileMode.CLAMP));
                canvas.drawLine(startX, startY, stopX, stopY, bgPaint);

                //列
                startX = i * specSize;
                startY = 0;
                stopX = i * specSize;
                stopY = specSize * count;
                bgPaint.setShader(new LinearGradient(startX, startY, stopX, stopY, delegate.getBgShardColors(), position, Shader.TileMode.CLAMP));
                canvas.drawLine(startX, startY, stopX, stopY, bgPaint);
            }
        }

        if (mCurrStatus == STATE_ERROR) {
            mLinkPaint.setColor(delegate.getLinkErrorColor());
        } else {
            mLinkPaint.setColor(delegate.getLinkSelectColor());
        }
        // 先绘制已有的连线
        for (int n = 1; n < nodeList.size(); n++) {
            GestureLockView firstNode = nodeList.get(n - 1);
            GestureLockView secondNode = nodeList.get(n);
            canvas.drawLine(firstNode.getCenterX(), firstNode.getCenterY(), secondNode.getCenterX(), secondNode.getCenterY(), mLinkPaint);
        }
        // 如果已经有点亮的点，则在点亮点和手指位置之间绘制连线
        if (nodeList.size() > 0) {
            GestureLockView lastNode = nodeList.get(nodeList.size() - 1);
            canvas.drawLine(lastNode.getCenterX(), lastNode.getCenterY(), lastX, lastY, mLinkPaint);
        }
    }

    /**
     * 在这里处理手势
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                nodeList.clear();
                for (int i = 0; i < getChildCount(); i++) {
                    View c = getChildAt(i);
                    if (c instanceof GestureLockView) {
                        ((GestureLockView) c).setLockerState(STATE_NORMAL, false);
                    }
                }
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                mCurrStatus = STATE_NORMAL;
                mLinkPaint.setColor(delegate.getLinkSelectColor());
                break;
            case MotionEvent.ACTION_MOVE:
                lastX = event.getX(); // 这里要实时记录手指的坐标
                lastY = event.getY();
                GestureLockView currentNode = getNodeAt(lastX, lastY);
                if (currentNode != null && !currentNode.isNodeSelected()) { // 碰触了新的未点亮节点
                    if (nodeList.size() > 0) { // 之前有点亮的节点
                        if (delegate.isAutoLink()) { // 开启了中间节点自动连接
                            GestureLockView lastNode = nodeList.get(nodeList.size() - 1);
                            GestureLockView middleNode = getNodeBetween(lastNode, currentNode);
                            if (middleNode != null && !middleNode.isNodeSelected()) { // 存在中间节点没点亮
                                // 点亮中间节点
                                middleNode.setLockerState(STATE_SELECTED, true);
                                nodeList.add(middleNode);
                            }
                        }
                    }
                    mCurrStatus = STATE_SELECTED;
                    // 点亮当前触摸节点
                    currentNode.setLockerState(STATE_SELECTED, false);
                    nodeList.add(currentNode);
                }
                // 有点亮的节点才重绘
                if (nodeList.size() > 0) {
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (nodeList.size() > 0) { // 有点亮的节点
                    GestureLockView lockView = nodeList.get(nodeList.size() - 1);
                    lastX = lockView.getCenterX();
                    lastY = lockView.getCenterY();
                    invalidate();

                    if (callback != null) {
                        callback.onGestureFinished(generateCurrentNumbers());
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 生成当前数字列表
     */
    @NonNull
    private String generateCurrentNumbers() {
        StringBuffer stringBuffer = new StringBuffer(nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            GestureLockView node = nodeList.get(i);
            stringBuffer.append(node.getNumber());
        }
        return stringBuffer.toString();
    }


    /**
     * 获取给定坐标点的Node，返回null表示当前手指在两个Node之间
     */
    private GestureLockView getNodeAt(float x, float y) {
        for (int n = 0; n < getChildCount(); n++) {
            GestureLockView node = (GestureLockView) getChildAt(n);
            if (!(x >= node.getLeft() && x < node.getRight())) {
                continue;
            }
            if (!(y >= node.getTop() && y < node.getBottom())) {
                continue;
            }
            return node;
        }
        return null;
    }

    /**
     * 获取两个Node中间的Node，返回null表示没有中间node
     */
    @Nullable
    private GestureLockView getNodeBetween(@NonNull GestureLockView na, @NonNull GestureLockView nb) {
        if (na.getNumber() > nb.getNumber()) { // 保证 na 小于 nb
            GestureLockView nc = na;
            na = nb;
            nb = nc;
        }
        if (na.getNumber() % 3 == 1 && nb.getNumber() - na.getNumber() == 2) { // 水平的情况
            return (GestureLockView) getChildAt(na.getNumber());
        } else if (na.getNumber() <= 3 && nb.getNumber() - na.getNumber() == 6) { // 垂直的情况
            return (GestureLockView) getChildAt(na.getNumber() + 2);
        } else if ((na.getNumber() == 1 && nb.getNumber() == 9) || (na.getNumber() == 3 && nb.getNumber() == 7)) { // 倾斜的情况
            return (GestureLockView) getChildAt(4);
        } else {
            return null;
        }
    }

    public void cleanDrawLineState(long delayTime) {
        cleanDrawLineState(delayTime, false);
    }

    public void cleanDrawLineState(long delayTime, boolean showErrorStatus) {
        if (delayTime > 0 && nodeList != null && nodeList.size() > 0) {
            int size = nodeList.size();
            if (showErrorStatus) {
                mCurrStatus = STATE_ERROR;
                for (int i = 0; i < size; i++) {
                    GestureLockView lockView = nodeList.get(i);
                    lockView.setLockerState(STATE_ERROR, false);
                }
            } else {
                mCurrStatus = STATE_SELECTED;
            }
            invalidate();
        }
        new Handler().postDelayed(new CleanStatusRunnable(), delayTime);
    }

    private class CleanStatusRunnable implements Runnable {
        @Override
        public void run() {
            for (GestureLockView lockView : nodeList) {
                lockView.setLockerState(STATE_NORMAL, false);
            }
            nodeList.clear();
            invalidate();
        }
    }

    public interface GestureCallback {
        void onGestureFinished(@NonNull String numbers);
    }

    public void setGestureCallback(@Nullable GestureCallback callback) {
        this.callback = callback;
    }
}
