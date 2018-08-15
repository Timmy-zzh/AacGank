package com.timmy.aacgank.ui.multimedia.video;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;

import com.timmy.aacgank.R;

import java.io.IOException;

/**
 * 使用Camera采集视频数据
 */
public class CameraStudyActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private String TAG = this.getClass().getSimpleName();
    private SurfaceView surfaceView;
    private TextureView textureView;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_study);
        surfaceView = findViewById(R.id.surface_view);
        textureView = findViewById(R.id.texture_view);
        initCamera();
    }

    private void initCamera() {
        surfaceView.getHolder().addCallback(this);
        //打开摄像头
        camera = Camera.open();
        //摄像头旋转90度
        camera.setDisplayOrientation(90);

        //设置Camera数据回调的格式
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewFormat(ImageFormat.NV21);
        camera.setParameters(parameters);

        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.d(TAG, "获取到视频数据:" + data);
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.release();
        }
    }

    /**
     * Surface预览
     *
     * @param view
     */
    public void surfaceViewDisplay(View view) {
//        surfaceView.setVisibility(View.VISIBLE);
//        textureView.setVisibility(View.GONE);
    }

    /**
     * TextureView预览
     *
     * @param view
     */
    public void textureViewDisplay(View view) {
//        textureView.setVisibility(View.VISIBLE);
//        surfaceView.setVisibility(View.GONE);
//
//        camera = Camera.open();
//        //摄像头旋转90度
//        camera.setDisplayOrientation(90);
//
//        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
//            @Override
//            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//                try {
//                    camera.setPreviewTexture(surface);
//                    camera.startPreview();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//            }
//
//            @Override
//            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//                camera.release();
//                return false;
//            }
//
//            @Override
//            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//            }
//        });
    }

    /**
     * 防止报错:Camera is being used after Camera.release() was called
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
