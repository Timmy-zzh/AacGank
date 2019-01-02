package com.timmy.androidbase.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;


/**
 * 动画的使用
 */
public class AnimationUseActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView;
    private ImageView ivFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_use);

        imageView = findViewById(R.id.iv);
        ivFrame = findViewById(R.id.iv_frame);
        textView = findViewById(R.id.tv_num);
    }

    /**
     * 帧动画
     * ﻿渐变透明度动画效果 AlphaAnimation
     * 渐变尺寸缩放动画效果  ScaleAnimation
     * 画面位置移动动画效果  TranslateAnimation
     * 画面旋转动画效果  RotateAnimation
     * <p>
     * 代码实现/xml文件实现
     */
    public void frameAnimation(View view) {
        ivFrame.setImageResource(R.drawable.animation_frame_study);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivFrame.getDrawable();
        animationDrawable.start();
    }

    /**
     * 补间动画:
     * 两种实现方式：
     * 1。xml
     * 2。java代码实现
     * 3.补间动画类型
     * 4.动画的类型
     * AlphaAnimation
     * ScaleAnimation
     * TranslateAnimation
     * RotateAnimation
     */
    public void tweenAnimation(View view) {
        //1
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
//        imageView.startAnimation(animation);

        //2
        //透明度动画， 参数为从开始透明度到结束透明度，值为 0f～1.0f， 0为全透明，1为不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.1f);
        //设置动画时间
        alphaAnimation.setDuration(1000);
        //动画播放后保持当前状态
        alphaAnimation.setFillAfter(true);
        //动画重复执行
        alphaAnimation.setRepeatCount(2);
        //动画重置执行的方式：重新开始执行／反转执行
        // alphaAnimation.setRepeatMode(Animation.RESTART);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        //开始动画
        imageView.startAnimation(alphaAnimation);
    }

    /**
     * 属性动画
     *
     * @param view
     */
    public void valueAnimation(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                imageView.setRotationY(value);
            }
        });
        animator.start();

        //2.
//        ObjectAnimator
//                .ofFloat(imageView, "rotationX", 0f, 360f)
//                .setDuration(300)
//                .start();
    }

    public void propertyValuesHolder(View view) {
        PropertyValuesHolder scaleXPro = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0, 1.0f);
        PropertyValuesHolder scaleYPro = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0, 1.0f);
        PropertyValuesHolder alphaPro = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXPro, scaleYPro, alphaPro).setDuration(1000).start();
    }

    public void reset(View view) {
        imageView.clearAnimation();
    }


    public void typeEvaluatorUse(View view) {
        startActivity(new Intent(this, CartAnimatorActivity.class));
    }


    /**
     * 金额变化
     *
     * @param view
     */
    public void numChange(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 2000);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                textView.setText(String.valueOf(value));
            }
        });
        animator.start();
    }

    /**
     * 揭露动画
     *
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circularReveral(View view) {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(imageView,
                0, 0, 0,
                (float) Math.hypot(imageView.getHeight(), imageView.getWidth()));
        circularReveal.setDuration(2000);
        circularReveal.start();
    }

    /**
     * 转场动画
     *
     进入退出效果 注意这里 创建的效果对象是 Fade()
     getWindow().setEnterTransition(new Fade().setDuration(2000));
     getWindow().setExitTransition(new Fade().setDuration(2000));
     */

}
