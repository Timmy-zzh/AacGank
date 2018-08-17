package com.timmy.aacgank.ui.multimedia.video.muxer;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Vector;

/**
 * 视频采集线程:
 * Camera视频采集->传递过来->编码-->交给MediaMuxerThread线程进行音视频混合
 */
public class VideoCollectThread extends Thread {

    public static final int IMAGE_HEIGHT = 1080;
    public static final int IMAGE_WIDTH = 1920;

    private static final String TAG = "VideoCollectThread";

    // 编码相关参数
    //H.264 Advanced Video
    private static final String MIME_TYPE = "video/avc";
    // 帧率
    private static final int FRAME_RATE = 25;
    private static final int IFRAME_INTERVAL = 10; // I帧间隔（GOP）
    private static final int TIMEOUT_USEC = 10000; // 编码超时时间

    private static final int COMPRESS_RATIO = 256;
    private static final int BIT_RATE = IMAGE_HEIGHT * IMAGE_WIDTH * 3 * 8 * FRAME_RATE / COMPRESS_RATIO; // bit rate CameraWrapper.

    // 视频宽高参数
    private int mWidth;
    private int mHeight;
    private final Object lock = new Object();

    private final WeakReference<MediaMuxerThread> mediaMuxerRef;

    // 存储每一帧的数据 Vector 自增数组
    private Vector<byte[]> frameBytes;
    private byte[] mFrameData;
    private MediaCodec.BufferInfo bufferInfo;
    private MediaCodecInfo mediaCodecInfo;
    private MediaFormat mediaFormat;
    private boolean isExit;
    private boolean isStart;
    private MediaCodec mediaCodec;
    private boolean isMuxerReady = false;//混合线程是否准备好了


    /**
     * 视频采集的宽高
     */
    public VideoCollectThread(int width, int height, WeakReference<MediaMuxerThread> weakReference) {
        this.mWidth = width;
        this.mHeight = height;
        this.mediaMuxerRef = weakReference;
        frameBytes = new Vector<>();
        prepare();
    }

    /**
     * 编码类初始化MediaCodec
     */
    private void prepare() {
        mFrameData = new byte[this.mWidth * this.mHeight * 3 / 2];
        bufferInfo = new MediaCodec.BufferInfo();
        mediaCodecInfo = selectCodec(MIME_TYPE);
        if (mediaCodecInfo == null) {
            return;
        }

        mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE, this.mWidth, this.mHeight);
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE);
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, FRAME_RATE);
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, IFRAME_INTERVAL);
    }

    /**
     * 找到视频编码格式信息
     *
     * @param mimeType
     * @return
     */
    private MediaCodecInfo selectCodec(String mimeType) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (String type : types) {
                if (type.equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }

    /**
     * 数据传递:Camera->MediaMuxerThread-->this
     */
    public void add(byte[] data) {
        if (frameBytes != null && isMuxerReady) {
            frameBytes.add(data);
        }
    }


    @Override
    public void run() {
        super.run();
        while (!isExit) {
            if (!isStart) {

                if (isMuxerReady) {
                    startMediaCodec();
                }

            } else if (!frameBytes.isEmpty()) {
                byte[] bytes = frameBytes.remove(0);
                //编码视频数据
                encodeFrame(bytes);
            }
        }
    }

    private void encodeFrame(byte[] input) {
        //将原始数据的N21数据转换为I420
        NV21toI420SemiPlanar(input, mFrameData, this.mWidth, this.mHeight);

        //获取需要编码数据的输入流队列，返回的是一个ByteBuffer数组
        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
        //获取编解码之后的数据输出流队列，返回的是一个ByteBuffer数组
        ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();
        //获取输入流队列的位置index
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(TIMEOUT_USEC);
        if (inputBufferIndex >= 0) {
            //拿到需要的输入流 容器
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            //先清空容器,再添加摄像头输入的数据
            inputBuffer.clear();
            inputBuffer.put(input);
            //将提取出来的输入流队列容器 入队列
            mediaCodec.queueInputBuffer(inputBufferIndex, 0, mFrameData.length, System.nanoTime() / 1000, 0);
        } else {

        }

        //一样的逻辑:获取输出流队列的位置index
        int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
        do {
            if (inputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {

            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                //有输出数据
                //先获取输出流格式
                MediaFormat outputFormat = mediaCodec.getOutputFormat();
                MediaMuxerThread mediaMuxerThread = this.mediaMuxerRef.get();
                if (mediaMuxerThread != null) {
                    //数据传递
                    mediaMuxerThread.addTrackIndex(MediaMuxerThread.TRACK_VIDEO, outputFormat);
                }
            } else if (outputBufferIndex < 0) {

            } else {
                //获取到编码后的数据
                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                if (outputBuffer == null) {
                    throw new RuntimeException("outputBuffer ==null");
                }
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    bufferInfo.size = 0;
                }
                if (bufferInfo.size == 0) {
                    MediaMuxerThread mediaMuxerThread = this.mediaMuxerRef.get();
                    if (mediaMuxerThread != null && !mediaMuxerThread.isVideoTrackAdd()) {
                        MediaFormat outputFormat = mediaCodec.getOutputFormat();
                        mediaMuxerThread.addTrackIndex(MediaMuxerThread.TRACK_VIDEO, outputFormat);
                    }

                    outputBuffer.position(bufferInfo.offset);
                    outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                    if (mediaMuxerThread != null) {
                        mediaMuxerThread.addCollectData(new MediaMuxerThread.MuxerData(MediaMuxerThread.TRACK_VIDEO, outputBuffer, bufferInfo));
                    }
                }

                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
            }
            outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
        } while (outputBufferIndex >= 0); //不断的获取
    }

    /**
     * 初始化MediaCodec
     */
    private void startMediaCodec() {
        try {
//            mediaCodec = MediaCodec.createByCodecName(mediaCodecInfo.getName());
            mediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
            isStart = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startCollect(boolean muxerReady) {
        isMuxerReady = muxerReady;
    }


    public void exit() {
        isExit = true;
    }

    private static void NV21toI420SemiPlanar(byte[] nv21bytes, byte[] i420bytes, int width, int height) {
        System.arraycopy(nv21bytes, 0, i420bytes, 0, width * height);
        for (int i = width * height; i < nv21bytes.length; i += 2) {
            i420bytes[i] = nv21bytes[i + 1];
            i420bytes[i + 1] = nv21bytes[i];
        }
    }
}
