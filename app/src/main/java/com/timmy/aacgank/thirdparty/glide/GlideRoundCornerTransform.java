package com.timmy.aacgank.thirdparty.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class GlideRoundCornerTransform extends BitmapTransformation {

    private final float radius;

    public GlideRoundCornerTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundCornerTransform(Context context, int dp) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;
        //1.获取到原始图片的宽高
        int width = source.getWidth();
        int height = source.getHeight();

        //2.根据原始图片宽高,新建Bitmap
        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null)
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //3.新建画布,传递bitmap图片,在画布上进行绘制
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //4.设置图片Shader
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);

        //方式2 使用Xfermode进行裁剪
//        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(source, 0, 0, paint);

        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
