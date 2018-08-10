package com.timmy.aacgank.ui.video.audio;

import android.Manifest;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.aacgank.R;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.functions.Consumer;

/**
 * 使用AudioRecord采集音频数据
 * 1.配置参数，初始化内部的音频缓冲区
 * 2.开始采集
 * 3.需要一个线程，不断地从 AudioRecord 的缓冲区将音频数据“读”出来，注意，这个过程一定要及时，
 * 否则就会出现“overrun”的错误，该错误在音频开发中比较常见，意味着应用层没有及时地“取走”音频数据，导致内部的音频缓冲区溢出。
 * 4.停止采集，释放资源
 */
public class AudioStudyActivity extends AppCompatActivity {

    private int bufferSize;
    private AudioRecord audioRecord;
    private Thread captureThread;
    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_study);

//        init();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(new String[]{
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            init();
                        }
                    }
                });

    }

    private void init() {
        bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC //音频采集的输入源  --MIC(由手机麦克风输入)
                , 44100         // 采样率，44100Hz保证兼容所有Android手机的采样率
                , AudioFormat.CHANNEL_IN_MONO   //通道数  CHANNEL_IN_MONO单通道/CHANNEL_IN_STEREO 双通道
                , AudioFormat.ENCODING_PCM_16BIT//数据位宽  ENCODING_PCM_16BIT（16bit) 兼容
                , bufferSize * 2 //音频缓冲区的大小  值不能低于一帧“音频帧”（Frame）的大小
        );
    }

    //开始音频采集
    public void startAudioRecord(View view) {
//        setAudioPath();

        bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC //音频采集的输入源  --MIC(由手机麦克风输入)
                , 44100         // 采样率，44100Hz保证兼容所有Android手机的采样率
                , AudioFormat.CHANNEL_IN_MONO   //通道数  CHANNEL_IN_MONO单通道/CHANNEL_IN_STEREO 双通道
                , AudioFormat.ENCODING_PCM_16BIT//数据位宽  ENCODING_PCM_16BIT（16bit) 兼容
                , bufferSize * 2 //音频缓冲区的大小  值不能低于一帧“音频帧”（Frame）的大小
        );

        audioRecord.startRecording();
        isStart = true;
        //启动录音线程
        captureThread = new Thread(new AudioCaptureRunnable());
        captureThread.start();
    }

    /**
     * 设置音频文件保存的路径
     */
    private void setAudioPath() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.pcm");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (Exception e) {

        }
    }

    /**
     * 获取音频数据的线程:
     * 不断的从AudioRecord读取音频数据
     */
    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {

            File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.pcm");
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(file);


                int captureRecord;
                byte[] tempBuffer = new byte[bufferSize];

                while (isStart) {
                    //获取
                    captureRecord = audioRecord.read(tempBuffer, 0, bufferSize);
                    if (captureRecord == AudioRecord.ERROR_INVALID_OPERATION || captureRecord == AudioRecord.ERROR_BAD_VALUE) {
                        continue;
                    }

                    //获取到数据
                    if (captureRecord != 0 && captureRecord != -1) {
                        /**
                         * 获取到数据了captureRecord,
                         * 开启IO流写入数据文件
                         * 在此可以对录制音频的数据进行二次处理 比如变声，压缩，降噪，增益等操作
                         * 我们这里直接将pcm音频原数据写入文件 这里可以直接发送至服务器 对方采用AudioTrack进行播放原数据
                         */
                        os.write(tempBuffer, 0, captureRecord);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
