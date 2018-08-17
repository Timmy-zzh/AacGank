package com.timmy.aacgank.ui.multimedia.video;

import android.Manifest;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.aacgank.R;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import io.reactivex.functions.Consumer;

/**
 * 使用MediaExtractor与MediaMuxer解析和封装mp4文件
 * 角色:读取器(Extractor)
 * MediaExtractor的作用是把音频和视频的数据进行分离。
 * MediaMuxer的作用是生成音频或视频文件；还可以把音频与视频混合成一个音视频文件。
 */
public class VideoStudyActivity1 extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private int sourceAudioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_study1);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                        }
                    }
                });
    }

    /**
     * MediaExtractor的API用法
     * 提取MP4文件中的视频部分
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void userMediaExtractor(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                process2();
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void process2() {
        MediaExtractor extractor = new MediaExtractor();
        //在该路劲下先准备好mp4文件
        //
        File file = new File(getExternalFilesDir(null), "input.mp4");
        File outputFile = new File(getExternalFilesDir(null), "output.mp4");
        Log.d(TAG, "file: " + file.getPath());
        Log.d(TAG, "outputFile: " + outputFile.getPath());
        if (!file.exists()) {
            return;
        }
        if (outputFile.exists()) {
            outputFile.delete();
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
                    //选择轨道
                    extractor.selectTrack(i);
                    sourceAudioTrack = i;
                    break;
                }
            }

            //path:输出文件的名称  format:输出文件的格式；当前只支持MP4格式；
            MediaMuxer mediaMuxer = new MediaMuxer(outputFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
//            MediaMuxer mediaMuxer = new MediaMuxer("output.mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            //添加轨道
            //添加通道；更多的是使用MediaCodec.getOutpurForma()或Extractor.getTrackFormat(int index)来获取MediaFormat;也可以自己创建；
            MediaFormat trackFormat = extractor.getTrackFormat(sourceAudioTrack);
            mediaMuxer.addTrack(trackFormat);
            int frameRate = trackFormat.getInteger(MediaFormat.KEY_FRAME_RATE);

            //开始合成文件
            mediaMuxer.start();

            //申请缓存空间
            ByteBuffer inputBuffer = ByteBuffer.allocate(1024 * 1024 * 2);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int sampleSize;

            /**
             * extractor.readSampleData(inputBuffer,0)  把指定通道中的数据按偏移量读取到ByteBuffer中
             */
            while ((sampleSize = extractor.readSampleData(inputBuffer, 0)) > 0) {
                //时间戳
                long sampleTime = extractor.getSampleTime();
                Log.d(TAG, "获取到视频数据 sampleSize:" + sampleSize + ",sampleTime+" + sampleTime);
                //给MediaCodec.BufferInfo 赋值,进行封装
                bufferInfo.offset = 0;
                bufferInfo.size = sampleSize;
                bufferInfo.flags = MediaCodec.BUFFER_FLAG_KEY_FRAME;
                bufferInfo.presentationTimeUs += 1000 * 1000 / frameRate;
                //把ByteBuffer中的数据写入到在构造器设置的文件中；
                mediaMuxer.writeSampleData(sourceAudioTrack, inputBuffer, bufferInfo);
                //读取下一帧数据
                extractor.advance();
            }
            //停止并释放MediaMuxer
            mediaMuxer.stop();
            mediaMuxer.release();

            //释放MediaExtractor
            extractor.release();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
