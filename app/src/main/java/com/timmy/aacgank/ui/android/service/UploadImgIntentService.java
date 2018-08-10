package com.timmy.aacgank.ui.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 使用IntetnService模拟图片上传
 * 1.内部开启了子线程
 * 2.并使用Handle处理耗时操作
 * 3.当耗时操作处理完后,IntentService会自动销毁该服务
 */
public class UploadImgIntentService extends IntentService {

    private String TAG = this.getClass().getSimpleName();
    private static final String ACTION_UPLOAD_IMG = "com.timmy.intentService.uploadImg";
    public static final String EXTRA_IMG_PATH = "extra.imgpath";

    public static void startUploadImg(Context context, String imgPath) {
        Intent intent = new Intent(context, UploadImgIntentService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, imgPath);
        context.startService(intent);
    }

    public UploadImgIntentService() {
        super("UploadImgIntentService");
    }

    /**
     * 耗时操作逻辑实现
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_UPLOAD_IMG)) {
                String imgPath = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(imgPath);
            }
        }
    }

    /**
     * 模拟图片上传
     *
     * @param imgPath
     */
    private void handleUploadImg(String imgPath) {
        try {
            Thread.sleep(3000);
            //图片上传成功后,发送通知告诉Acivity结果,让其更新UI -->发送本地广播
            Intent intent = new Intent(ServiceStudyActivity.ACTION_UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH, imgPath);
            sendBroadcast(intent);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

