package com.timmy.aacgank.ui.base;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.thirdparty.glide.ImageUtil;


/**
 * Created by admin on 2017/11/1.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象
    private Context mContext;//上下文对象
    private SparseBooleanArray mBooleanArrays;
    private int viewType;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mViews = new SparseArray<View>();
        mBooleanArrays = new SparseBooleanArray();
    }

    public BaseViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mViews = new SparseArray<View>();
        mBooleanArrays = new SparseBooleanArray();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public LinearLayout getLinearLayout(int viewId) {
        return (LinearLayout) getView(viewId);
    }

    public RelativeLayout getRelativeLayout(int viewId) {
        return (RelativeLayout) getView(viewId);
    }

    public BaseViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    //设置原价等,需要画横线的TextView
    public BaseViewHolder setLineText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        view.setText(value);
        return this;
    }


    public BaseViewHolder setAlpha(int viewId, float value) {
        View view = findViewById(viewId);
        view.setAlpha(value);
        return this;
    }

    public BaseViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public BaseViewHolder setImageView(int viewId, String uri) {
        setImageView(viewId,uri,0);
        return this;
    }

    public BaseViewHolder setImageView(int viewId, String uri,int placeholder) {
        ImageView view = findViewById(viewId);
        if (!TextUtils.isEmpty(uri)) {
            ImageUtil.load(mContext, view, uri,placeholder);
        }
        return this;
    }

    public BaseViewHolder setCircleImageView(int viewId, String uri) {
        ImageView view = findViewById(viewId);
        if (!TextUtils.isEmpty(uri)) {
            ImageUtil.loadGlideCircleTransform(mContext, view, uri, R.mipmap.ic_launcher);
        }
        return this;
    }

    public BaseViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder putBoolean(int key, boolean value) {
        mBooleanArrays.put(key, value);
        return this;
    }

    public boolean getBoolean(int key) {
        return mBooleanArrays.get(key);
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public BaseViewHolder setTextColor(int viewId, int color) {
        TextView view = findViewById(viewId);
        view.setTextColor(view.getResources().getColor(color));
        return this;
    }

    public BaseViewHolder setBackgroundResource(int viewId, int drawable) {
        View view = findViewById(viewId);
        view.setBackgroundResource(drawable);
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int tag) {
        View view = findViewById(viewId);
        view.setVisibility(tag);
        return this;
    }

    public BaseViewHolder setEnable(int viewId, boolean enable) {
        View view = findViewById(viewId);
        view.setEnabled(enable);
        return this;
    }
}
