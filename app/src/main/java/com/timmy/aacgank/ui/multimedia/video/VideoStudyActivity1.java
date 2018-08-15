package com.timmy.aacgank.ui.multimedia.video;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.timmy.aacgank.R;

import java.io.File;
import java.io.IOException;

/**
 * 使用MediaExtractor与MediaMuxer解析和封装mp4文件
 * 角色:读取器(Extractor)
 */
public class VideoStudyActivity1 extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_study1);
    }

    /**
     * MediaExtractor的API用法
     */
    public void userMediaExtractor(View view) {
        MediaExtractor extractor = new MediaExtractor();
        //在该路劲下先准备好mp4文件
        //
        File file = new File(getExternalFilesDir(null), "test.mp4");
        Log.d(TAG, "file: " + file.getPath());
        if (!file.exists()) {
            return;
        }

        try {
            //1.设置文件路径,(本地/网络)
            extractor.setDataSource(file.getAbsolutePath());
            //2.获取源文件的通道数
            int trackCount = extractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                //3.获取指定(index)的通道格式
                MediaFormat trackFormat = extractor.getTrackFormat(i);
                //获取轨道格式类型
                String mime = trackFormat.getString(MediaFormat.KEY_MIME);
                //查找视频轨道
                if (mime.startsWith("video/")) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
