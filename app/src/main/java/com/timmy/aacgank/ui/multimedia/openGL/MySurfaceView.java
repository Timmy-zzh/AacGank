package com.timmy.aacgank.ui.multimedia.openGL;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MySurfaceView extends GLSurfaceView {

    public MySurfaceView(Context context) {
        super(context);

        //创建OpenGL ES2.0 版本的上下文
        setEGLContextClientVersion(2);

        //创建GLSurfaceView渲染对象
        MyRenderer renderer = new MyRenderer();

        //GLSurfaceView是一个控件,真正控制绘制渲染的是Renderer
        setRenderer(renderer);

    }
}
