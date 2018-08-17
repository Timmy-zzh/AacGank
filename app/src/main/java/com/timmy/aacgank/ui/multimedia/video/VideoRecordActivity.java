package com.timmy.aacgank.ui.multimedia.video;

import android.Manifest;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.multimedia.video.muxer.MediaMuxerThread;

import java.io.IOException;

import io.reactivex.functions.Consumer;

/**
 * 音视频录制:
 * 使用AudioRecord、AudioTrack、Camera、 MediaExtractor、MediaMuxer API、MediaCodec
 * 目标:串联整个音视频录制流程，完成音视频的采集、编码、封包成 mp4 输出。
 * 方案:
 * 1.Android音视频采集的方法：预览用SurfaceView，视频采集用Camera类，音频采集用AudioRecord。
 * 2.使用MediaCodec 类进行编码压缩，视频压缩为H.264，音频压缩为aac，使用MediaMuxer 将音视频合成为MP4。
 *
 * 操作:一共有三个线程进行操作
 * 1.音频数据采集线程:主要通过AudioRecord进行采集,采集的数据经过MediaCodec进行编码后,-->
 * 2.视频数据采集线程:主要通过Camera获取,视频数据采集后也需要经过编码
 * 3.将采集到的音频数据和视频数据通过MediaMuxer将两种数据混合成一个mp4文件
 *
 * @author zhuzhonghua
 */
public class VideoRecordActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private String TAG = this.getClass().getSimpleName();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Button btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);

        surfaceView = findViewById(R.id.surface_view);
        btnRecord = findViewById(R.id.btn_record);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                        }
                    }
                });

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    /**
     * @param view
     */
    public void startVideoRecord(View view) {
        if (btnRecord.getText().equals("开始录制")) {
            btnRecord.setText("视频录制中...");
            //1.打开摄像头
            openCamera();
            MediaMuxerThread.startMuxer();

        } else {
            btnRecord.setText("开始录制");
            //->停止录制
            MediaMuxerThread.stopMuxer();

            stopCamera();
        }
    }

    private void openCamera() {
//        CameraManager
        /**
         * Camera.CameraInfo.CAMERA_FACING_FRONT 前置摄像头
         * Camera.CameraInfo.CAMERA_FACING_BACK  后置摄像头
         */
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        //旋转90度
        camera.setDisplayOrientation(90);
        //设置Camera数据回调的格式
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        //设置预览宽高,必须和编解码个设置一致
        parameters.setPreviewSize(1920, 1080);

        try {
            camera.setParameters(parameters);
            camera.setPreviewDisplay(surfaceHolder);
            //设置摄像头获取到的视频数据
            camera.setPreviewCallback(this);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭摄像头
     */
    private void stopCamera() {
        // 停止预览并释放资源
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.surfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        stopCamera();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //获取到摄像头数据
        Log.d(TAG,"摄像头数据:"+data);
        MediaMuxerThread.addCameraFrameData(data);
    }
}
