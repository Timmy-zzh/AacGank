package com.timmy.aacgank.ui.gank.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.timmy.aacgank.R;
import com.timmy.aacgank.bean.gank.Gank;
import com.timmy.aacgank.databinding.ActivityAndroidDetailBinding;
import com.timmy.baselib.base.activity.TBaseBindingActivity;
import com.timmy.baselib.base.activity.TBaseContentActivity;

import java.util.Random;

/**
 * 高仿淘宝商品详情页实现界面
 * 1.顶部图片轮播
 * 2.顶部状态栏和标记栏改变背景色和指示位置
 *      a.item主要信息介绍
 *      b.评价
 *      c.详情页
 *      d.推荐商品列表
 */
public class AndroidDetailActivity extends TBaseContentActivity<ActivityAndroidDetailBinding> {

    private Gank gank;
    public static final int DEFAULT_STATUS_BAR_ALPHA = 0;

    public static void startAction(Context context, Gank gank) {
        Intent intent = new Intent(context, AndroidDetailActivity.class);
        intent.putExtra("data", gank);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_detail);
        gank = (Gank) getIntent().getSerializableExtra("data");

        Random random = new Random();
       int color = 0xff000000 | random.nextInt(0xffffff);
        baseBinding.toolbar.toolbar.setBackgroundColor(color);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0+
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA));
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏,而且会将状态栏顶上去
            //添加一个状态栏高度的控件
            View statusView = createStatusView(color);

            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(statusView); //到这里还需要将content内容的布局,距离出状态栏的高度

            ViewGroup contentView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            contentView.setFitsSystemWindows(true);
            contentView.setClipToPadding(true);
        }
    }

    private View createStatusView(int color) {
        //获取状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);

        //绘制一个和状态栏一样高度的矩形
        View statusView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
