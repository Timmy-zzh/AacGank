package com.timmy.androidbase.graphicsDraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;


/**
 * 图形绘制：
 * 1。画布Canvas使用
 * 2。画笔Pant
 */
public class GraphicsDrawActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_draw);
    }

    public void canvasStudy(View view) {
        startActivity(new Intent(this, CanvasStudyActivity.class));
    }

    public void paintBaseUse(View view) {
        startActivity(new Intent(this, PaintStudyActivity.class));
    }

    public void pathEffect(View view) {
        startActivity(new Intent(this, PathEffectActivity.class));
    }

    public void colorFilter(View view) {
        startActivity(new Intent(this, ColorFilterActivity.class));
    }

    public void shader(View view) {
        startActivity(new Intent(this, ShaderActivity.class));
    }

    public void maskFilter(View view) {
        startActivity(new Intent(this, MaskFilterActivity.class));
    }

    public void shadowLayer(View view) {
        startActivity(new Intent(this, ShadowLayerActivity.class));
    }

    public void xfermode(View view) {
        startActivity(new Intent(this, XfermodeActivity.class));
    }

    public void drawText(View view) {
        startActivity(new Intent(this, XfermodeActivity.class));
    }
}
