package com.timmy.aacgank.ui.multimedia.video.muxer;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/**
 * 音频采集线程:
 * AudioRecord音频采集->编码-->交给MediaMuxerThread线程进行音视频混合
 * @author zhuzhonghua
 */
public class AudioCollectThread extends Thread {

    private final WeakReference<MediaMuxerThread> mediaMuxerRef;
    private String TAG = this.getClass().getSimpleName();
    private final MediaCodec.BufferInfo bufferInfo;
    private static final String MIME_TYPE = "audio/mp4a-latm";
    private static final int SAMPLE_RATE = 16000;
    private static final int BIT_RATE = 64000;
    private MediaFormat audioFormat;
    public static final int SAMPLES_PER_FRAME = 1024;
    public static final int FRAMES_PER_BUFFER = 25;
    private static final int TIMEOUT_USEC = 10000;
    private boolean isExit;
    private boolean isStart;
    private MediaCodec mediaCodec;
    private AudioRecord audioRecord;
    private long prevOutputPTSUs = 0;

    public AudioCollectThread(WeakReference<MediaMuxerThread> weakReference) {
        this.mediaMuxerRef = weakReference;
        bufferInfo = new MediaCodec.BufferInfo();
        prepare();
    }

    /**
     * 准备音频数据采集的类
     * AudioRecord初始化
     * MedioCodec
     */
    private void prepare() {
        MediaCodecInfo audioCodecInfo = selectAudioCodec(MIME_TYPE);

        audioFormat = MediaFormat.createAudioFormat(MIME_TYPE, SAMPLE_RATE, 1);
        audioFormat.setInteger(MediaFormat.KEY_BIT_RATE, BIT_RATE);
        audioFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        audioFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, SAMPLE_RATE);
    }

    private static final MediaCodecInfo selectAudioCodec(final String mimeType) {
        MediaCodecInfo result = null;
        // get the list of available codecs
        Log.e("111", "selectAudioCodec");
        final int numCodecs = MediaCodecList.getCodecCount();
        Log.e("111", "selectAudioCodec。。。" + numCodecs);
        for (int i = 0; i < numCodecs; i++) {
            final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                // skipp decoder
                continue;
            }
            final String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                Log.i("AudioCollectThread ", "supportedType:" + codecInfo.getName() + ",MIME=" + types[j]);
                if (types[j].equalsIgnoreCase(mimeType)) {
                    if (result == null) {
                        result = codecInfo;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void run() {
        super.run();

        ByteBuffer buffer = ByteBuffer.allocateDirect(SAMPLES_PER_FRAME);
        int readBytes;
        while (!isExit) {
            if (!isStart) {
                startMediaCodec();
                isStart = true;
            } else {
                buffer.clear();
                //不停的获取音频数据-->编码-->传递给混合线程
                readBytes = audioRecord.read(buffer, SAMPLES_PER_FRAME);
                if (readBytes > 0) {
                    buffer.position(readBytes);
                    buffer.flip();
                    //编码
                    encode(buffer, readBytes, getPTSUs());
                }
            }
        }
    }

    private void encode(ByteBuffer buffer, int length, long presentationTimeUs) {
        if (isExit) {
            return;
        }
        //获取需要编码数据的输入流队列,返回一个ByteBuffer数组
        ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();
        //从输入流队列中取数据进行编码操作
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(TIMEOUT_USEC);
        //向编码器输入数据-->得到编码后的密文
        if (inputBufferIndex >= 0) {
            final ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            if (buffer != null) {
                inputBuffer.put(buffer);
            }
            if (length <= 0) {
                Log.i(TAG, "send BUFFER_FLAG_END_OF_STREAM");
                mediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, presentationTimeUs, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
            } else {
                //输入流入队列
                mediaCodec.queueInputBuffer(inputBufferIndex, 0, length, presentationTimeUs, 0);
            }
        } else if (inputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
            // wait for MediaCodec encoder is ready to encode
            // nothing to do here because MediaCodec#dequeueInputBuffer(TIMEOUT_USEC)
            // will wait for maximum TIMEOUT_USEC(10msec) on each call
        }

        /*获取解码后的数据*/
        final MediaMuxerThread muxer = mediaMuxerRef.get();
        if (muxer == null) {
            Log.w(TAG, "MediaMuxerRunnable is unexpectedly null");
            return;
        }

        //获取编解码之后的数据输出流队列,返回的是一个ByteBuffer数组
        ByteBuffer[] encoderOutputBuffers = mediaCodec.getOutputBuffers();
        int encoderStatus;

        do {
            //从输出队列中取出编码操作之后的数据
            encoderStatus = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
            if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {

            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                encoderOutputBuffers = mediaCodec.getOutputBuffers();
            } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {

                final MediaFormat format = mediaCodec.getOutputFormat();
                // API >= 16
                MediaMuxerThread mediaMuxerRunnable = this.mediaMuxerRef.get();
                if (mediaMuxerRunnable != null) {
                    Log.e(TAG, "添加音轨 INFO_OUTPUT_FORMAT_CHANGED " + format.toString());
                    mediaMuxerRunnable.addTrackIndex(MediaMuxerThread.TRACK_AUDIO, format);
                }

            } else if (encoderStatus < 0) {
                Log.e(TAG, "encoderStatus < 0");
            } else {
                final ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                if ((bufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    bufferInfo.size = 0;
                }

                if (bufferInfo.size != 0 && muxer != null) {
                    bufferInfo.presentationTimeUs = getPTSUs();
                    Log.e(TAG, "发送音频数据 " + bufferInfo.size);
                    muxer.addCollectData(new MediaMuxerThread.MuxerData(MediaMuxerThread.TRACK_AUDIO, encodedData, bufferInfo));
                    prevOutputPTSUs = bufferInfo.presentationTimeUs;
                }
                mediaCodec.releaseOutputBuffer(encoderStatus, false);
            }
        } while (encoderStatus >= 0);
    }

    /**
     * 准备编码器
     */
    private void startMediaCodec() {
        if (mediaCodec != null) {
            return;
        }
        try {
            mediaCodec = MediaCodec.createEncoderByType(MIME_TYPE);
            mediaCodec.configure(audioFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            //开始编码
            mediaCodec.start();

            prepareAudioRecord();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化音频录制对象
     */
    private void prepareAudioRecord() {
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }

        int bufferSize = AudioRecord.getMinBufferSize(
                SAMPLE_RATE
                , AudioFormat.CHANNEL_IN_MONO
                , AudioFormat.ENCODING_PCM_16BIT);

        int buffer_size = SAMPLES_PER_FRAME * FRAMES_PER_BUFFER;
        if (buffer_size < bufferSize) {
            buffer_size = ((bufferSize / SAMPLES_PER_FRAME) + 1) * SAMPLES_PER_FRAME * 2;
        }

        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC
                , SAMPLE_RATE
                , AudioFormat.CHANNEL_IN_MONO
                , AudioFormat.ENCODING_PCM_16BIT
                , buffer_size
        );

        //开始音频采集
        audioRecord.startRecording();
    }

    /**
     * 开始音频数据采集
     */
    public void startCollect(boolean muxerReady) {

    }

    public void exit() {
        isExit = true;
    }

    private long getPTSUs() {
        long result = System.nanoTime() / 1000L;
        // presentationTimeUs should be monotonic
        // otherwise muxer fail to write
        if (result < prevOutputPTSUs) {
            result = (prevOutputPTSUs - result) + result;
        }
        return result;
    }
}
