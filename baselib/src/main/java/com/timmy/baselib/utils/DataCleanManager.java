package com.timmy.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 本应用数据清除管理器
 *
 */
public class DataCleanManager {

    @SuppressLint("SdCardPath")
    private static final String DIR_DATA = "/data/data/";
    private static final String DIR_CACHE = "cache";
    /** 暂不统计 */
    private static final String DIR_DATABASES = "databases";
    private static final String DIR_FILES = "files";
    private static final String DIR_SHARED_PREFS = "shared_prefs";

    private static final long UNIT_K_BYTE = 1024;
    private static final long UNIT_M_BYTE = 1024*1024;
    private static final long UNIT_G_BYTE = 1024*1024*1024;

    private static DecimalFormat DF = new DecimalFormat();

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * @param context 上下文
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * @param context 上下文
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File(DIR_DATA + context.getPackageName() + DIR_DATABASES));
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @param context 上下文
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File(DIR_DATA + context.getPackageName() + DIR_SHARED_PREFS));
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     * @param context 上下文
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 按名字清除本应用数据库
     * @param context 上下文
     * @param dbName 数据库名称
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * @param context 上下文
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param filePath 文件路径
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @param directory 文件夹路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if(item.isFile()){
                    item.delete();
                }else if(item.isDirectory()){
                    deleteFilesByDirectory(item);
                }
            }
        }
    }


    /**
     * 清除本应用所有的数据
     * @param context 上下文
     * @param filepath 文件数组
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        //cleanDatabases(context);
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    private static long getFileSize(File directory){
        if (directory == null) return 0;
        if (!directory.exists()) return 0;

        long size = 0;

        if (directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                if (item.exists()){
                    if (item.isDirectory()) {
                        size += getFileSize(item);
                    } else {
                        size += item.length();
                    }
                }
            }
        } else {
            size = directory.length();
        }
        return size;
    }

    private static long getInternalCacheSize(Context context) {
        return getFileSize(context.getCacheDir());
    }

    private static long getExternalCacheSize(Context context) {
        return getFileSize(context.getExternalCacheDir());
    }

    private static long getSharedPreferenceSize(Context context) {
        return getFileSize(new File(DIR_SHARED_PREFS + context.getPackageName() + DIR_SHARED_PREFS));
    }

    private static long getInternalFileSize(Context context) {
        return getFileSize(context.getFilesDir());
    }

    private static String formatFileSize(double fileSize) {
        String showSize = "";
        DF.applyPattern(".00");
        if (fileSize >= 0 && fileSize < UNIT_K_BYTE) {
            showSize = fileSize + "B";
        } else if (fileSize >= UNIT_K_BYTE && fileSize < UNIT_M_BYTE) {
            showSize = DF.format(fileSize / UNIT_K_BYTE) + "KB";
        } else if (fileSize >= UNIT_M_BYTE && fileSize < UNIT_G_BYTE) {
            showSize = DF.format(fileSize / UNIT_M_BYTE) + "MB";
        } else if (fileSize >= UNIT_G_BYTE) {
            showSize = DF.format(fileSize / UNIT_G_BYTE) + "GB";
        }
        return showSize;
    }

    public static String getCacheSize(Context context){
        double size = getInternalCacheSize(context);
        size += getExternalCacheSize(context);
        size += getSharedPreferenceSize(context);
        size += getInternalFileSize(context);
        return formatFileSize(size);
    }

}