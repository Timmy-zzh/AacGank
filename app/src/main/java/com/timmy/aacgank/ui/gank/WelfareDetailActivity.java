package com.timmy.aacgank.ui.gank;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityWelfareDetailBinding;
import com.timmy.aacgank.ui.daily.DailyActivity;
import com.timmy.aacgank.ui.daily.aac.DailyViewModel;
import com.timmy.aacgank.ui.gank.aac.GankViewModel;
import com.timmy.baselib.base.activity.DjBaseContentActivity;
import com.timmy.baselib.image.ImageUtil;

public class WelfareDetailActivity extends DjBaseContentActivity<ActivityWelfareDetailBinding> {

    private Gank gank;
    private GankViewModel viewModel;

    public static void startAction(Activity activity, View shareView, String imageUrl) {
        //添加切换动画，MD风格的
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                shareView,
                activity.getResources().getString(R.string.image_transition));
        Intent intent = new Intent(activity, WelfareDetailActivity.class);
//        intent.putExtra("title", title);
//        intent.putExtra("date", date);
        intent.putExtra("imageUrl", imageUrl);
        ActivityCompat.startActivity(activity, intent, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welfare_detail);
        showToolbar(false);
        gank = (Gank) getIntent().getSerializableExtra("data");
        viewModel = ViewModelProviders.of(this).get(GankViewModel.class);
        subscribeUI();
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        ImageUtil.loadImage(this,imageUrl,binding.ivImage);
        //

    }

    private void subscribeUI() {


    }
}
