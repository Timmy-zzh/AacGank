package com.timmy.baselib.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by ps_an on 2016/3/11.
 */
public class ImageUtil {

    public static void load(Context mContext, ImageView view, String uri) {
        load(mContext, view, uri, 0);
    }

    public static void load(Context mContext, ImageView view, String uri, int errId) {
        load(mContext, view, uri, errId, errId);
    }

    public static void load(Context mContext, ImageView view, String uri, int errId, int placeholder) {
        Glide.with(mContext)
                .load(uri)
                .placeholder(placeholder)
                .error(errId)
                .into(view);
    }

    public static void loadCircleImg(Context mContext, final ImageView view, String uri, int placeholder) {
        Glide.with(mContext)
                .load(uri)
                .placeholder(placeholder)
                .error(placeholder)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable drawable = resource.getCurrent();
                        view.setImageDrawable(drawable);
                    }
                });
    }

    public static void loadGlideCircleTransform(Context mContext, ImageView imageView, String uri, int placeholder) {
        RequestManager glideRequest = Glide.with(mContext);
        glideRequest
                .load(uri)
                .placeholder(placeholder)
                .transform(new GlideCircleTransform(mContext))
                .error(placeholder)
                .into(imageView);
    }


    public static void loadGlideRoundCornerImg(Context mContext, ImageView imageView, String uri, int placeholder, int dp) {
        RequestManager glideRequest = Glide.with(mContext);
        glideRequest
                .load(uri)
                .placeholder(placeholder)
                .transform(new GlideRoundCornerTransform(mContext, dp))
                .into(imageView);
    }
}
