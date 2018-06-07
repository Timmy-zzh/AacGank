package com.timmy.aacgank.ui.gank.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseViewHolder;
import com.timmy.baselib.utils.DensityUtils;
import com.timmy.baselib.utils.LogUtils;

import java.util.Random;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 * 1.福利图片展示做成瀑布流
 * 2.主要处理图片的高度,因为图片的宽度是固定为屏幕一半,高度就根据原始图片的宽高比进行缩放.
 */
public class WelfareAdapter extends BaseDataBindingAdapter<Gank, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ItemWelfareBinding binding, Gank meizhi) {
        binding.setGank(meizhi);
//        String text = "拉数控刀具法拉克记得了拉开大姐夫拉金德拉萨的减肥啦觉得浪费空间啦手机发的啦睡觉地方啦拉放假啊哦诶人抛弃偶尔去哦ajdfalkjdflajfdal;垃圾发电垃圾啦";
//        int limit = text.length();
//        int end = new Random().nextInt(limit) > 3 ? new Random().nextInt(limit) - 1 : 3;
//        binding.tv.setText(text.substring(0, end));
//ImageView
//        Glide.with(helper.getContext()).load(meizhi.getUrl())// 加载图片
////                .error(errorimg)// 设置错误图片
//                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
////                .placeholder(errorimg)// 设置占位图
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
//                .into(binding.iv);

//        final ViewGroup.LayoutParams layoutParams = binding.iv.getLayoutParams();
//        //拿到原始图片的宽高
//        Glide.with(helper.getContext())
//                .load(meizhi.getUrl())
//                .asBitmap()//返回一个Bitmap对象
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        int bitWidth = resource.getWidth();
//                        int bithight = resource.getHeight();
//                        LogUtils.d("width:" + bitWidth + ",height:" + bithight);
//                        //计算出图片的宽高比，然后按照图片的比列去缩放图片
//                        float bitScalew = bitWidth / bithight;
//
////                        BitmapFactory.Options options = new BitmapFactory.Options();
////                        options.inJustDecodeBounds = true;
//
//                        layoutParams.width = DensityUtils.getScreenWidth(helper.getContext()) / 2;
//                        layoutParams.height = (int) ((DensityUtils.getScreenWidth(helper.getContext()) / 2) / bitScalew);
//                        binding.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                        binding.iv.setAdjustViewBounds(true);
//                        binding.iv.setLayoutParams(layoutParams);
////                        binding.iv.setImageBitmap(resource);
//                    }
//                });
//
//        Glide.with(helper.getContext()).load(meizhi.getUrl())// 加载图片
////                .error(errorimg)// 设置错误图片
//                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
////                .placeholder(errorimg)// 设置占位图
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
//                .into(binding.iv);

    }

//    @Override
//    protected void convert(ItemWelfareBinding binding, Gank item) {
//        binding.setGank(item);
//        ViewGroup.LayoutParams layoutParams = binding.getRoot().getLayoutParams();
//        layoutParams.height = 100 + (new Random(100).nextInt() % 3) * 30;
//        binding.getRoot().setLayoutParams(layoutParams);
//    }
}