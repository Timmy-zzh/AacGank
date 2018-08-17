package com.timmy.aacgank.ui.multimedia.video.muxer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Vector;

/**
 * 音视频混合线程
 */
public class MediaMuxerThread extends Thread {

    private String TAG = this.getClass().getSimpleName();
    private static MediaMuxerThread mediaMuxerThread;
    private FileUtils fileHelper;
    private AudioCollectThread audioThread;
    private VideoCollectThread videoThread;
    private MediaMuxer mediaMuxer;

    public static final int TRACK_VIDEO = 0;
    public static final int TRACK_AUDIO = 1;
    private int videoTrackIndex;
    private int audioTrackIndex;
    private boolean isExit;

    private Vector<MuxerData> muxerDatas;
    private final Object lock = new Object();

    // 音轨添加状态
    private volatile boolean isVideoTrackAdd;
    private volatile boolean isAudioTrackAdd;


    private MediaMuxerThread() {
    }

    /**
     * 启动混合线程
     * 执行run方法->在run方法中开启音频采集线程和视频处理线程
     */
    public static void startMuxer() {
        if (mediaMuxerThread == null) {
            synchronized (MediaMuxerThread.class) {
                if (mediaMuxerThread == null) {
                    mediaMuxerThread = new MediaMuxerThread();
                    mediaMuxerThread.start();
                }
            }
        }
    }

    /**
     * 停止音视频混合线程
     * 停止音频线程和视频采集线程
     */
    public static void stopMuxer() {
        if (mediaMuxerThread != null) {
            mediaMuxerThread.exit();
            try {
                mediaMuxerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mediaMuxerThread = null;
        }

    }

    private void exit() {
        if (audioThread != null) {
            audioThread.exit();
            try {
                audioThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            audioThread = null;
        }
        if (videoThread != null) {
            videoThread.exit();
            try {
                videoThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            videoThread = null;
        }
        //标示是否退出
        isExit = true;
    }


    public static void addCameraFrameData(byte[] data) {
        if (mediaMuxerThread != null) {
            mediaMuxerThread.addVideoData(data);
        }
    }

    private void addVideoData(byte[] data) {
        if (videoThread != null) {
            videoThread.add(data);
        }
    }

    @Override
    public void run() {
        super.run();
        //初始化音频线程和视频线程
        initMuxer();

        while (!isExit) {
            if (muxerDatas.isEmpty()) {
                //没有数据-->等待
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //有数据,开始混合
//                if (fileHelper.requestSwapFile()){
//
//                }
                MuxerData data = muxerDatas.remove(0);
                int track;
                if (data.trackIndex == TRACK_VIDEO) {
                    track = videoTrackIndex;
                } else {
                    track = audioTrackIndex;
                }

                //写入混合数据
                Log.d(TAG, "data:" + data.byteBuf);
                mediaMuxer.writeSampleData(track, data.byteBuf, data.bufferInfo);

            }
        }
        //结束数据采集
        readyStop();
    }

    private void readyStop() {
        //停止并释放MediaMuxer
        mediaMuxer.stop();
        mediaMuxer.release();
        mediaMuxer = null;
    }

    /**
     * 音频,视频线程,传递数据到混合线程,有数据才开始混合操作
     * 先添加轨道,后传递数据
     */
    public synchronized void addTrackIndex(int index, MediaFormat mediaFormat) {
        if (isMuxerStart()) {
            return;
        }
        /* 如果已经添加了，就不做处理了 */
        if ((index == TRACK_AUDIO && isAudioTrackAdd()) || (index == TRACK_VIDEO && isVideoTrackAdd())) {
            return;
        }

        if (mediaMuxer != null) {

            int track = mediaMuxer.addTrack(mediaFormat);
            if (index == TRACK_VIDEO) {
                videoTrackIndex = track;
                isVideoTrackAdd = true;
            } else {
                audioTrackIndex = track;
                isAudioTrackAdd = true;
            }
            mediaMuxer.start();
        }
    }

    /**
     * 当前音视频合成器是否运行了
     *
     * @return
     */
    public boolean isMuxerStart() {
        return isAudioTrackAdd && isVideoTrackAdd;
    }

    /**
     * 当前是否添加了音轨
     *
     * @return
     */
    public boolean isAudioTrackAdd() {
        return isAudioTrackAdd;
    }

    /**
     * 当前是否添加了视频轨
     *
     * @return
     */
    public boolean isVideoTrackAdd() {
        return isVideoTrackAdd;
    }

    public void addCollectData(MuxerData data) {
        muxerDatas.add(data);
        synchronized (lock) {
            //唤醒线程
            lock.notify();
//            lock.notifyAll();
        }
    }

    /**
     * 开启音频线程和视频采集线程
     */
    private void initMuxer() {
        muxerDatas = new Vector<>();
        fileHelper = new FileUtils();
        audioThread = new AudioCollectThread(new WeakReference<MediaMuxerThread>(this));
        videoThread = new VideoCollectThread(1920, 1080, new WeakReference<MediaMuxerThread>(this));

        audioThread.start();
        videoThread.start();

        //生成视频文件名称
        fileHelper.requestSwapFile(true);
        readyStart(fileHelper.getNextFileName());
    }

    /**
     * 初始化MediaMuxer对象,准备开始接收音频,视频线程传递的数据,开始混合操作
     *
     * @param fileName
     */
    private void readyStart(String fileName) {
        muxerDatas.clear();

        try {
            mediaMuxer = new MediaMuxer(fileName, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            if (audioThread != null) {
                audioThread.startCollect(true);
            }
            if (videoThread != null) {
                videoThread.startCollect(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 封装需要传输的数据类型
     */
    public static class MuxerData {

        int trackIndex;
        ByteBuffer byteBuf;
        MediaCodec.BufferInfo bufferInfo;

        public MuxerData(int trackIndex, ByteBuffer byteBuf, MediaCodec.BufferInfo bufferInfo) {
            this.trackIndex = trackIndex;
            this.byteBuf = byteBuf;
            this.bufferInfo = bufferInfo;
        }
    }
}
