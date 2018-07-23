package com.timmy.aacgank.ui.movie.adapter;

import android.widget.ImageView;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.movie.DoubanMovie;
import com.timmy.aacgank.databinding.ItemMovieBinding;
import com.timmy.adapterlib.BaseDataBindingAdapter;
import com.timmy.adapterlib.BaseViewHolder;
import com.timmy.baselib.image.ImageUtil;

/**
 * {"福利", "Android", "iOS", "休息视频", "拓展资源", "前端", "all"};
 * 根据不同类型展示不同界面
 */
public class MovieAdapter extends BaseDataBindingAdapter<DoubanMovie, ItemMovieBinding> {

    public MovieAdapter() {
        super(R.layout.item_movie);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemMovieBinding binding, DoubanMovie item) {
        binding.setMovie(item);
//        ImageView imageView = helper.getView(R.id.iv);
//        ImageUtil.loadImage(helper.getContext(), item.images.medium, imageView);
    }
}