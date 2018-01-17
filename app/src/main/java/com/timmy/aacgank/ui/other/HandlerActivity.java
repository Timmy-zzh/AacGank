package com.timmy.aacgank.ui.other;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.timmy.aacgank.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 开启子线程访问网络请求图片数据,交给Handler处理,在UI线程进行展示
 * 步骤:
 * 1.点击请求网络图片,handler发送一条消息,标示开始网络请求, UI界面展示:loading
 * 2.获取网络图片成功,发送消息标示成功,并且将图片数据传递处理, UI展示图片
 * 3.网络请求失败,提示
 */
public class HandlerActivity extends AppCompatActivity {

    private String TAG = "HandlerActivity";
    private static final int MSG_START = 0;
    private static final int MSG_SUCCESS = 1;
    private static final int MSG_FAIL = 2;
    private static final String URL_IMAGE = "http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg";
    private static final String URL_IMAGE_FAIL = "http://7xi8d6.com1.z0.glb.clouddn.cm/20171219115747_tH0TN5_Screenshot.jpeg";

    private ImageView imageView;
    private ProgressBar progressBar;
    private UIHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        imageView = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);
        handler = new UIHandler();
    }

    public void handlerUse(View view) {
        handler.sendEmptyMessage(MSG_START);
        Thread thread = new Thread(new ImageRunnable(URL_IMAGE));
        thread.start();
    }

    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START:
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    /////
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    break;
                case MSG_SUCCESS:
                    imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                case MSG_FAIL:
                    imageView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(HandlerActivity.this, "获取图片失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    class ImageRunnable implements Runnable {
        private String imageUrl;
        private InputStream inputStream;

        public ImageRunnable(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                int code = connection.getResponseCode();
                Log.d(TAG, "code:" + code);
                inputStream = connection.getInputStream();

                //获取Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                handler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();

                //发送消息的方法
//                handler.obtainMessage();
//
//                Message message = Message.obtain();
//                message.obj = bitmap;
//                message.what = MSG_SUCCESS;
//
//                handler.sendMessage(message);
//                handler.sendEmptyMessage(1);
//                handler.sendEmptyMessageAtTime(1,1000);
//                handler.sendEmptyMessageDelayed(0,2000);
//                handler.sendMessageAtFrontOfQueue(message);
//                handler.sendMessageAtTime(message,3000);


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(MSG_FAIL);
                Log.d(TAG, "Exception:" + e.toString());
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

