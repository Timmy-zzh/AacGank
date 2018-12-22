package com.timmy.androidbase.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_use);

        imageView = findViewById(R.id.iv);
        textView = findViewById(R.id.tv_num);
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
}
