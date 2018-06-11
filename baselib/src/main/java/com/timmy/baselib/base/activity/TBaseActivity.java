package com.timmy.baselib.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.timmy.baselib.R;
import com.timmy.baselib.base.helper.ILoadingDialog;
import com.timmy.baselib.base.helper.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Activity基类:
 * 1.处理加载Dialog显示,
 * 2.状态栏,
 * 3.和aac框架mDisposable初始化
 */
public abstract class TBaseActivity extends AppCompatActivity implements ILoadingDialog {

    static final String LOADING_DIALOG_TAG = "loading_dialog";
    private DialogFragment loadingDialogFragment;
    protected final CompositeDisposable mDisposable = new CompositeDisposable();
//    private ImmersionBar mImmersionBar;

    public void startAction(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
    }

    /**
     * 状态栏设置
     * https://github.com/gyf-dev/ImmersionBar
     */
    protected void setStatusBar() {
        //在BaseActivity里初始化
//        mImmersionBar = ImmersionBar.with(this)
//                .statusBarDarkFont(true, 0.2f)
//                .fitsSystemWindows(true);
//        mImmersionBar.init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // clear all the subscriptions
        mDisposable.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mImmersionBar != null) {
//            mImmersionBar.destroy();  //在BaseActivity里销毁
//        }
//        AppManager.getInstance().killActivity(this);
    }

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

    /****************************************** 键盘处理 *****************************
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

    /***************************************** 开启权限设置界面 *****************************/

    /**
     * 打开系统权限设置界面
     *
     * @param requestCode 请求Code
     * @param msg         弹窗提示内容
     */
    protected void openPermissionsSetting(final int requestCode, String msg) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("帮助")
//                .setMessage(getResources().getString(R.string.permission_tips))
                .setMessage(msg)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, requestCode);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /***************************************** ILoadDataView *****************************/

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog(LOADING_DEFULT_TIPS);
    }

    @Override
    public final void showLoadingDialog(String loadingTitle) {
        if (!loadingTitle.contains("…")) {
            loadingTitle = loadingTitle + "…";
        }
        showDefaultStyleLoadingDialog(loadingTitle);
    }

    // 显示用默认样式的Loading对话框
    private void showDefaultStyleLoadingDialog(String loadingTitle) {
        hideDefaultStyleLoadingDialog();
        DialogFragment newFragment = LoadingDialog.newInstance(loadingTitle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(newFragment, LOADING_DIALOG_TAG);
        ft.commitAllowingStateLoss();
        loadingDialogFragment = newFragment;
    }

    @Override
    public void hideLoadingDialog() {
        hideLoadingDialog(LOADING_DEFULT_TIPS);
    }

    @Override
    public final void hideLoadingDialog(String msg) {
        hideDefaultStyleLoadingDialog();
    }

    //  隐藏默认样式的loading对话框
    private void hideDefaultStyleLoadingDialog() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
            loadingDialogFragment = null;
        }
    }
}