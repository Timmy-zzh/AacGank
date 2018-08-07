package com.timmy.aacgank.ui.person.exoplayer;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.timmy.aacgank.R;

import java.net.URL;

/**
 * ExoPlayer视频播放器使用
 */
public class ExoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        PlayerView playerView = findViewById(R.id.player_view);

        //step1  创建TrackSelector
        Handler mainHandler = new Handler();
        //测量播放宽带
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        //创建轨道选择工厂
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        //创建轨道选择器实例
        TrackSelector trackSelection = new DefaultTrackSelector(factory);

        //stop2 创建播放器
        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelection);

        /**
         * 3.--------------------将播放器附加到View上
         * PlayerView 封装了一个PlayerControlView和一个显示视频的Surface
         */
        playerView.setPlayer(exoPlayer);

        //4.准备播放数据
        // 在播放期间测量带宽。 如果不需要，可以为null
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        //加载数据的工厂
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "TimmyExoPlayer"), defaultBandwidthMeter);

        //解析数据的工厂 数据读取器
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //创建MediaSource
//        String testUrl = "http://dw-w6.dwstatic.com/54/3/1622/2166193-100-1464903053.mp4";
        String testUrl = "http://v3-dy-z.ixigua.com/6ffe983462506af2af8e58c4f1071420/5b691dae/video/m/2205d77ef575d7049fabad3ecf3d2b567901157c4a50000cb8f104d258a/";
//        String testUrl = "http://v3-dy-y.ixigua.com/221e33165a21e47e12aa35f46c75f0ea/5b691cec/video/m/220ad3c39951c1e45acba400a71df5214a711570bc60000b265ec1148e6/";
        Uri mp4Uri = Uri.parse(testUrl);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4Uri);
        /**
         * 开始播放
         * prepare  MediaSource在播放开始时通过ExoPlayer.prepare注入。
         */
        exoPlayer.prepare(mediaSource);

        /**
         * 5.控制播放
         * 播放器准备就绪后，可以通过播放器上的调用方法来控制播放。 例如:
         setPlayWhenReady可用于开始和暂停播放
         各种seekTo方法可用于在媒体内搜索
         setRepeatMode可用于控制媒体是否以及如何循环播放
         并且setPlaybackParameters可用于调整播放速度和音调。
         */
        /**
         * 6.释放播放器
         * ExoPlayer.release
         */

    }
}
