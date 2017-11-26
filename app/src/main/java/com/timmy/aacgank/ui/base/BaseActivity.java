package com.timmy.aacgank.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseActivity extends AppCompatActivity  {

    static final String LOADING_DIALOG_TAG = "loading_dialog";
    private DialogFragment loadingDialogFragment;
    protected final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        ButterKnife.bind(this);
        init();
    }

    protected void init() {

    }


    protected void initContentView() {
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    @Override
    protected void onStop() {
        super.onStop();
        // clear all the subscriptions
        mDisposable.clear();
    }


    /***************************************** Toolbar *****************************/

    /**
     * 开启回退功能 和设置标题  引入layout文件(toolbar)就可以使用
     */
//    public void setToolbarTitle(String title) {
//        setTitle(title);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            toolbar.setTitle("");
//            setSupportActionBar(toolbar);
//            if (getSupportActionBar() != null)
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            toolbar.hideOverflowMenu();
//        }
//    }
//
//    public void setTitle(String title) {
//        TextView mTvTitle = findViewById(R.id.toolbar_title);
//        if (TextUtils.isEmpty(title)) {
//            return;
//        }
//        if (mTvTitle != null) {
//            mTvTitle.setText(title);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**************************************** 键盘处理 *****************************/

    /**
     * 处理点击空白区域隐藏虚拟键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}