package com.timmy.baselib.utils;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源管理工具类
 */
public final class ResourceUtil {


    /**
     * 构造方法私有化
     */
    private ResourceUtil() {

    }

    /**
     * 获取资源类
     *
     * @return 返回资源类
     */
    public static Resources getResources() {
        return Utils.getContext().getResources();
    }

    /**
     * 检测资源ID是存在
     *
     * @param resourceId 资源ID
     * @return 存在返回true，否则返回false
     */
    public static boolean exists(@IdRes int resourceId) {
        return getNameWithResId(resourceId) == null;
    }

    public static String getNameWithResId(@IdRes int resourceId) {
        try {
            return Utils.getContext().getResources().getResourceName(resourceId);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getIdWithName(@NonNull String resourceTypeName, @NonNull String resourceName) {
        return Utils.getContext().getResources().getIdentifier(resourceName, resourceTypeName, AppUtil.getAppName(Utils.getContext()));
    }

    public static int getLayoutResIdWithName(@NonNull String resourceName) {
        return getIdWithName("layout", resourceName);
    }

    public static int getStringResIdWithName(@NonNull String resourceName) {
        return getIdWithName("string", resourceName);
    }

    public static String getString(@StringRes int resourceId) {
        return Utils.getContext().getResources().getString(resourceId);
    }

    public static String[] getStringArray(@ArrayRes int resourceId) {
        return Utils.getContext().getResources().getStringArray(resourceId);
    }

    public static String getString(@NonNull String resourceName) {
        return Utils.getContext().getResources().getString(getIdWithName(resourceName, "string"));
    }

    public static String getString(@StringRes int resourceId, @Nullable Object... formatArgs) {
        return Utils.getContext().getResources().getString(resourceId, formatArgs);
    }

    public static String getString(@NonNull String resourceName, @Nullable Object... formatArgs) {
        return Utils.getContext().getResources().getString(getIdWithName(resourceName, "string"), formatArgs);
    }

//    public static int getColor(@ColorRes int resourceId, @NonNull Theme theme) {
//        return Utils.getContext().getResources().getColor(resourceId);
//    }

    public static int getColor(@ColorRes int resourceId) {
//        if (Build.VERSION.SDK_INT >= 23)
//            return Utils.getContext().getResources().getColor(resourceId, null);
//        else
//            return Utils.getContext().getResources().getColor(resourceId);
        return ContextCompat.getColor(Utils.getContext(), resourceId);
    }

    public static AssetManager getAssets() {
        return Utils.getContext().getResources().getAssets();
    }

    public static InputStream openAssetsFile(@NonNull String fileName) {
        try {
            return Utils.getContext().getResources().getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream openAssetsFile(@NonNull String fileName, int accessMode) {
        try {
            return Utils.getContext().getResources().getAssets().open(fileName, accessMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] getAssetsFiles(@NonNull String dir) {
        try {
            return Utils.getContext().getResources().getAssets().list(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取dimen
     *
     * @param resId 尺寸资源id
     * @return
     */
    public static int getDimens(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     *
     * @param resId 图片的资源id
     * @return drawable对象
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(Utils.getContext(), resId);
    }

    /**
     * 获取DisplayMetrics
     *
     * @return 设备的DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

}