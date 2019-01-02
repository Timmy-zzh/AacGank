package com.timmy.androidbase.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.androidbase.R;
import com.timmy.baselib.simple.SimpleAdapter;

import java.util.Arrays;

/**
 * 实现方式二：贝塞尔曲线+PathMeasure
 */
public class CartAnimatorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView ivCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_animator);

        recyclerView = findViewById(R.id.recycler_view);
        ivCart = findViewById(R.id.iv_cart);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] stringArray = getResources().getStringArray(R.array.dateArr);
        recyclerView.setAdapter(new MyAnimationAdapter(this));
    }

    public void playAnimation(int[] position) {
        final TextView textView = new TextView(this);
        textView.setText("+1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.shape_cart_point);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        rootView.addView(textView, new ViewGroup.LayoutParams(50, 50));

        int[] des = new int[2];
        ivCart.getLocationInWindow(des);

        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        Point startPosition = new Point(position[0], position[1]);
        Point endPosition = new Point(des[0] + ivCart.getWidth() / 2, des[1] + ivCart.getHeight() / 2);

        int pointX = (startPosition.x + endPosition.x) / 2 - 100;
        int pointY = startPosition.y - 200;
        Point controllPoint = new Point(pointX, pointY);

        /*
         * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用
         * 这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
         * 一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
         * */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BizierEvaluator(controllPoint), startPosition, endPosition);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                textView.setX(point.x);
                textView.setY(point.y);
            }
        });

        /**
         * 动画结束，移除掉小圆圈
         */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup rootView = (ViewGroup) CartAnimatorActivity.this.getWindow().getDecorView();
                rootView.removeView(textView);
            }
        });
    }
}
