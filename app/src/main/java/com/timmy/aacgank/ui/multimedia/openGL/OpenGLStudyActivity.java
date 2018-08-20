package com.timmy.aacgank.ui.multimedia.openGL;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.timmy.aacgank.R;

/**
 * OpenGL学习使用
 *
 * @author zhuzhonghua
 */
public class OpenGLStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_glstudy);

    }


    public void drawTriangle(View view) {
        startActivity(new Intent(this,OpenGLTriangleActivity.class));
    }
}
