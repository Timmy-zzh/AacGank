package com.timmy.baselib.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;

import java.util.Iterator;
import java.util.List;

//跟App相关的辅助类
public class AppUtil {

    private AppUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = getPackageManager(context);
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 当前应用的版本号
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否安装了指定应用
     * @param context
     * @param packageName
     * @return
     */
    public static  boolean isInstalled(Context context, String packageName) {
        //获取已经安装的程序的包信息
        List<PackageInfo> packageInfos = getPackageManager(context).getInstalledPackages(0);

        for (PackageInfo packageInfo : packageInfos) {
                if(packageInfo.packageName.equalsIgnoreCase(packageName))
                    return true;
        }
        return false;
    }


    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){
        int statusBarHeight =-1;
        //获取status_bar_height资源的ID  
        int resourceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0){
            //根据资源ID获取响应的尺寸值  
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 打电话
     * @param activity
     * @param tel
     */
    public static void call(Activity activity,String tel){
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel));
        activity.startActivity(intent);
    }

    public static String getUserAgent(Context context){
        String userAgent = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            userAgent = "HM" + info.versionName + "(" + Build.MODEL + ","+ "Android" + Build.VERSION.RELEASE + ")";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return userAgent;
    }

    /**
     * 当前是否是默认进程
     * @param context
     * @return
     */
    public static  boolean isDefaultProcess(Context context){
        int pid = android.os.Process.myPid();
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(processName == null || !processName.equalsIgnoreCase(context.getPackageName())){
            return false;
        }

        return true;
    }

    /**
     * 获取android版本号
     * @return
     */
    public static String getAndroidVersionCode(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机版本号
     * @return
     */
    public static String getPhoneVersion(){
        return Build.MODEL;
    }
}
