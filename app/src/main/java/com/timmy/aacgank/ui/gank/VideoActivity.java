package com.timmy.aacgank.ui.gank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.timmy.aacgank.R;
import com.timmy.aacgank.databinding.ActivityVideoBinding;
import com.timmy.baselib.base.activity.DjBaseContentActivity;
import com.timmy.baselib.utils.LogUtils;

import cn.jzvd.JZVideoPlayer;

public class VideoActivity extends DjBaseContentActivity<ActivityVideoBinding> {

    public static void startAction(Context activity, String url) {
        LogUtils.d("VideoActivity:" + url);
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        showToolbar(false);

        String url = getIntent().getStringExtra("url");
        LogUtils.d("VideoActivity2222:" + url);

        binding.videoPlayer.setUp(url,
                JZVideoPlayer.SCREEN_WINDOW_NORMAL
                , "妹子");

    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
