package com.timmy.aacgank.ui.gank.adapter;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ItemWelfareBinding;
import com.timmy.baselib.adapterlib.BaseDataBindingAdapter;
import com.timmy.baselib.adapterlib.BaseViewHolder;
import com.timmy.baselib.image.ImageUtil;

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
    protected void convert(final BaseViewHolder helper, final ItemWelfareBinding binding, Gank gank) {
        binding.iv.setInitSize(gank.getSrcWidth(), gank.getSrcHeight());
//        ImageUtil.loadImage(helper.getContext(), gank.getUrl(), binding.iv);
        Glide.with(helper.getContext())
                .load(gank.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .crossFade()
                .into(binding.iv);
    }
}