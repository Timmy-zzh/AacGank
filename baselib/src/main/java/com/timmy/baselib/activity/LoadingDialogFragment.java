package com.timmy.baselib.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.timmy.baselib.R;
import com.timmy.baselib.utils.DensityUtils;


public class LoadingDialogFragment extends DialogFragment {

    public static final String DEFAULT_MSG = "加载中…";
    private static final String DIMABLE = "dimable";
    private static final String MSG = "msg";
    private boolean dimable;
    private String msg;

    public static LoadingDialogFragment newInstance() {
        return newInstance(null);
    }

    public static LoadingDialogFragment newInstance(boolean dimable) {
        return newInstance(dimable, "");
    }

    public static LoadingDialogFragment newInstance(String msg) {
        return newInstance(true, msg);
    }

    public static LoadingDialogFragment newInstance(boolean dimable, String msg) {
        LoadingDialogFragment f = new LoadingDialogFragment();
        Bundle args = new Bundle();
        args.putString(MSG, msg);
        args.putBoolean(DIMABLE, dimable);
        f.setArguments(args);
        return f;
    }

    private OnCancelListener onCancelListener;

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        boolean dimable = getArguments().getBoolean(DIMABLE);
//        String msg = getArguments().getString(MSG, DEFAULT_MSG);
//        ProgressDialog dialog = new ProgressDialog(getContext());
//        dialog.setCancelable(dimable);
//        dialog.setCanceledOnTouchOutside(dimable);
//        dialog.setTitle(msg);
//        return dialog;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dimable = getArguments().getBoolean(DIMABLE);
        msg = getArguments().getString(MSG, DEFAULT_MSG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTips = view.findViewById(R.id.tv);
        tvTips.setText(msg);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    public LoadingDialogFragment show(FragmentManager fragmentManager) {
        super.show(fragmentManager, null);
        return this;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置Dialog的窗体Window的宽高
        Window window = getDialog().getWindow();
//        窗体背景色
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = window.getAttributes();
        //窗体位置为底部
//        lp.gravity = Gravity.BOTTOM;
        //窗体宽度--需要配合背景色才起作用,屏幕宽度的0.7
        lp.width = lp.height = (int) (DensityUtils.getWindowWidth(getActivity()) * 0.3);
//        Logger.d("width:" + lp.width + ",height:" + lp.height);
        window.setAttributes(lp);
        // 0~1 , 1表示完全昏暗
        window.setDimAmount(0.2f);
    }
}