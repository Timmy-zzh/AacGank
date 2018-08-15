package com.timmy.aacgank.ui.multimedia.audio;

import android.Manifest;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.aacgank.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    private AudioRecord audioRecord;
    private Thread captureThread;
    private boolean isStart;
    private String TAG = this.getClass().getSimpleName();
    private FileOutputStream outputStream;
    private Button btnStart;
    private Button btnStop;
    /**
     * 缓存的音频大小
     */
    private int bufferSize;
    /**
     * 采样率
     */
    private int mSampleRate = 44100;
    /**
     * AudioRecord能接受的最小缓存大小
     */
    private int mChannel = AudioFormat.CHANNEL_IN_MONO;
    /**
     * 数据位宽
     */
    private int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private AudioTrack audioTrack;
    private FileInputStream fileInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_study);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

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
        //AudioRecord能接受的最小缓存大小
        bufferSize = AudioRecord.getMinBufferSize(
                mSampleRate
                , mChannel
                , mAudioFormat);
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC //音频采集的输入源  --MIC(由手机麦克风输入)
                , mSampleRate         // 采样率，44100Hz保证兼容所有Android手机的采样率
                , mChannel   //通道数  CHANNEL_IN_MONO单通道/CHANNEL_IN_STEREO 双通道
                , mAudioFormat//数据位宽  ENCODING_PCM_16BIT（16bit) 兼容
                , bufferSize * 2 //音频缓冲区的大小  值不能低于一帧“音频帧”（Frame）的大小
        );

        initModeStaticSource();
    }

    //开始音频采集
    public void startAudioRecord(View view) {
        btnStart.setText("采集中...");
        setAudioPath();
        //判断之前是否有线程在运行，如有则取消该线程
        if (captureThread != null) {

        }
        audioRecord.startRecording();
        isStart = true;
        //启动录音线程
        captureThread = new Thread(new AudioCaptureRunnable());
        captureThread.start();
    }

    public void stopAudioRecord(View view) {
        btnStart.setText("音频采集");
        isStart = false;
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            captureThread = null;
        }
    }

    /**
     * 设置音频文件保存的路径
     */
    private void setAudioPath() {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.pcm");
        if (file.exists()){
            file.delete();
        }
        try {
            outputStream = new FileOutputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convetPcmToWav(View view) {
        PcmToWavUtil pcmToWavUtil = new PcmToWavUtil(mSampleRate, mChannel, mAudioFormat);

        File pcmFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.pcm");
        File wavFile = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.wav");
        if (!wavFile.mkdirs()) {
            Log.e(TAG, "wavFile Directory not created");
        }
        if (wavFile.exists()) {
            wavFile.delete();
        }
        pcmToWavUtil.pcmToWav(pcmFile.getAbsolutePath(), wavFile.getAbsolutePath());
    }

    /**
     * 获取音频数据的线程:
     * 不断的从AudioRecord读取音频数据
     */
    private class AudioCaptureRunnable implements Runnable {

        @Override
        public void run() {
            try {
                int captureRecord;
                //录制过程中，获取到的新音频数据存放区
                byte[] tempBuffer = new byte[bufferSize];

                while (isStart) {
                    //获取
                    captureRecord = audioRecord.read(tempBuffer, 0, bufferSize);
                    if (captureRecord == AudioRecord.ERROR_INVALID_OPERATION || captureRecord == AudioRecord.ERROR_BAD_VALUE) {
                        continue;
                    }
                    Log.d(TAG, "获取到音频数据：" + captureRecord);
                    //获取到数据
                    if (captureRecord != 0 && captureRecord != -1) {
                        /**
                         * 获取到数据了captureRecord,
                         * 开启IO流写入数据文件
                         * 在此可以对录制音频的数据进行二次处理 比如变声，压缩，降噪，增益等操作
                         * 我们这里直接将pcm音频原数据写入文件 这里可以直接发送至服务器 对方采用AudioTrack进行播放原数据
                         */
                        outputStream.write(tempBuffer, 0, captureRecord);
                    }
                }

                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 音频数据播放：
     * 采集的音频数据为pcm文件，为最原始的文件，不能播放
     * 需要将其转换为wav格式后播放
     * 使用AudioTrack进行音频播放
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playAudioRecord(View view) {
        playInModeStream();
    }

    /**
     * 播放，使用stream模式
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void playInModeStream() {
        /*
         * SAMPLE_RATE_INHZ 对应pcm音频的采样率
         * channelConfig 对应pcm音频的声道
         * AUDIO_FORMAT 对应pcm音频的格式
         * */
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        final int minBufferSize = AudioTrack.getMinBufferSize(mSampleRate, channelConfig, mAudioFormat);
        audioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(mSampleRate)
                        .setEncoding(mAudioFormat)
                        .setChannelMask(channelConfig)
                        .build(),
                minBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        audioTrack.play();

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "timAudio.pcm");
        try {
            fileInputStream = new FileInputStream(file);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] tempBuffer = new byte[minBufferSize];
                        while (fileInputStream.available() > 0) {
                            int readCount = fileInputStream.read(tempBuffer);
                            if (readCount == AudioTrack.ERROR_INVALID_OPERATION ||
                                    readCount == AudioTrack.ERROR_BAD_VALUE) {
                                continue;
                            }
                            if (readCount != 0 && readCount != -1) {
                                audioTrack.write(tempBuffer, 0, readCount);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] audioData;

    /**
     * 播放，使用static模式
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playInModeStatic(View view) {
        // static模式，需要将音频数据一次性write到AudioTrack的内部缓冲区
// R.raw.ding铃声文件的相关属性为 22050Hz, 8-bit, Mono
//        AudioTrack audioTrack1 = new AudioTrack(AudioManager.STREAM_MUSIC, 44100
//                , AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT
//                , audioData.length, AudioTrack.MODE_STATIC);
//        audioTrack1.write(audioData, 0, audioData.length);
//        audioTrack1.play();

        audioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(22050)
                        .setEncoding(AudioFormat.ENCODING_PCM_8BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                audioData.length,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        Log.d(TAG, "Writing audio data...");
        audioTrack.write(audioData, 0, audioData.length);
        Log.d(TAG, "Starting playback");
        audioTrack.play();
        Log.d(TAG, "Playing");
    }

    /**
     * MODE_STATIC：这种模式下，在play之前只需要把所有数据通过一次write调用传递到AudioTrack中的内部缓冲区，后续就不必再传递数据了。
     * 这种模式适用于像铃声这种内存占用量较小，延时要求较高的文件。但它也有一个缺点，就是一次write的数据不能太多，
     * 否则系统无法分配足够的内存来存储全部数据。
     */
    private void initModeStaticSource() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = getResources().openRawResource(R.raw.ding);
                    try {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        for (int b; (b = in.read()) != -1; ) {
                            out.write(b);
                        }
                        Log.d(TAG, "Got the data");
                        audioData = out.toByteArray();
                    } finally {
                        in.close();
                    }
                } catch (IOException e) {
                    Log.wtf(TAG, "Failed to read", e);
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void v) {
                Log.i(TAG, "Creating track...audioData.length = " + audioData.length);
            }

        }.execute();
    }

    /**
     * 停止播放
     */
    public void pauseAudioRecord(View view) {
        if (audioTrack != null) {
            Log.d(TAG, "Stopping");
            audioTrack.stop();
            Log.d(TAG, "Releasing");
            audioTrack.release();
            Log.d(TAG, "Nulling");
        }
    }

}
