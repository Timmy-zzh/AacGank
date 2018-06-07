package com.timmy.baselib.databinding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.baselib.R;
import com.timmy.baselib.image.ImageUtil;
import com.timmy.baselib.wiget.AutoHeightImageView;


/**
 * @描述: 数据绑定工具类
 */
public class BindingUtil {

    @BindingAdapter({"circleImageUrl"})
    public static void loadCircleImage(ImageView view, String url) {
        ImageUtil.loadCircleImage(view.getContext(), view, url, R.mipmap.ic_launcher_round);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        ImageUtil.loadImage(view.getContext(), url, view);
    }

    @BindingAdapter({"autoImageUrl"})
    public static void loadImage(AutoHeightImageView view, String url) {
        ImageUtil.loadImage(view.getContext(), url, view);
    }


    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView view, String url, Drawable resourceId) {
        ImageUtil.loadImage(view.getContext(), url, view);
    }

    @BindingAdapter({"htmlText"})
    public static void htmlData(TextView textView, String htmlData) {
        if (!TextUtils.isEmpty(htmlData)) {
            textView.setText(Html.fromHtml(htmlData));
        }
    }

    @BindingAdapter({"adapter"})
    public static void bindAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"adapter", "orientation"})
    public static void bindAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        layoutManager.setOrientation(orientation);//LinearLayoutManager.HORIZONTAL
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"setSelected"})
    public static void setTextSelected(TextView textView, boolean selected) {
//        textView.setTextColor(selected ? MvvmApplication.getInstance().getResources().getColor(R.color.colorPrimary) : MvvmApplication.getInstance().getResources().getColor(R.color.textPrimary));
//        textView.setBackgroundColor(selected ? MvvmApplication.getInstance().getResources().getColor(R.color.background) : MvvmApplication.getInstance().getResources().getColor(R.color.white));
    }


    @BindingConversion
    public static String convertFloatToString(float value) {
        return value + "";
    }


    @BindingConversion
    public static String convertDoubleToString(double value) {
        return value + "";
    }
}
