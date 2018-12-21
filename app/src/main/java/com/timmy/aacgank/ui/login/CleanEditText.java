package com.timmy.aacgank.ui.login;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * 带清除按钮的EditText
 * 可以在xml中指定删除图标
 */
public class CleanEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    private Drawable cleanDrawable;
    private boolean hasFocus;

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        cleanDrawable = getCompoundDrawables()[2];
        if (cleanDrawable == null) {
            cleanDrawable = getResources().getDrawable(R.mipmap.ic_delete);
        }
        //cleanDrawable.getIntrinsicWidth() 这个的值是怎么计算的
        cleanDrawable.setBounds(0, 0,
                cleanDrawable.getIntrinsicWidth(), cleanDrawable.getIntrinsicHeight());
        Log.d("Timmy", "getIntrinsicWidth: " + cleanDrawable.getIntrinsicWidth()
                + ",getIntrinsicHeight:" + cleanDrawable.getIntrinsicHeight());
        //默认设置图标隐藏
        setCleanIconVisible(false);
        //焦点改变监听
        setOnFocusChangeListener(this);
        //输入框文本内容变化监听
        addTextChangedListener(this);
    }

    private void setCleanIconVisible(boolean visible) {
        Drawable right = visible ? cleanDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {  //判断是否点击了删除图标的位置
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && event.getX() < (getWidth() - getPaddingRight());
                if (touchable) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setCleanIconVisible(getText().length() > 0);
        } else {
            setCleanIconVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (hasFocus) {
            setCleanIconVisible(charSequence.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
