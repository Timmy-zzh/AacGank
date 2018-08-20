package com.timmy.aacgank.ui.multimedia.openGL;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OpenGLTriangleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MySurfaceView surfaceView = new MySurfaceView(this);
        setContentView(surfaceView);
    }
}
