package com.timmy.aacgank.ui.multimedia.openGL;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 绘制一个背景色:
 * onSurfaceCreated() - 在View的OpenGL环境被创建的时候调用。
 * onDrawFrame() - 每一次View的重绘都会调用
 * onSurfaceChanged() - 如果视图的几何形状发生变化（例如，当设备的屏幕方向改变时），则调用此方法。
 */
public class MyRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;

    /**
     * 在View的OpenGL环境被创建的时候调用。
     * 绘制
     *
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        GLES20.glClearColor(1.0f,0.0f,0.0f,1.0f);

        //初始化三角形
        triangle = new Triangle();
    }

    /**
     * 如果视图的几何形状发生变化（例如，当设备的屏幕方向改变时），则调用此方法。
     * 图形变化时,设置宽高大小
     *
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    /**
     * 每一次View的重绘都会调用
     * 重新绘制时:
     *
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        triangle.draw();
    }
}
