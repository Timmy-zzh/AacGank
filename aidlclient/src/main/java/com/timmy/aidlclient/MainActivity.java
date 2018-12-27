package com.timmy.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.timmy.androidbase.binder.aidl.Book;
import com.timmy.androidbase.binder.aidl.BookController;

import java.util.List;

/**
 * AIDL客户端,
 * 通过连接Service,获取到服务端的Binder对象,然后调用IBinder.Stub暴露的方法
 */
public class MainActivity extends AppCompatActivity {

    private BookController bookController;
    private boolean connect;
    private List<Book> bookList;
    private TextView books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        books = findViewById(R.id.tv_books);
        bind();
    }

    /**
     * 客户端绑定服务端Service
     */
    private void bind() {
        Intent intent = new Intent();
        intent.setPackage("com.timmy.aacgank");
        intent.setAction("com.timmy.aacgank.aidl.action");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookController = BookController.Stub.asInterface(service);
            connect = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connect = false;
        }
    };

    public void getAllBooks(View view) {
        if (connect) {
            try {
                bookList = bookController.getBookList();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            log();
        }
    }

    private void log() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : bookList) {
            stringBuilder.append(book.getName() + "\n");
        }
        books.setText(stringBuilder);
    }

    public void addABook(View view) {
        if (connect) {
            try {
                Book book = new Book("这是一本新书 InOut");
                bookController.addBookInOut(book);
                getAllBooks(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connect) {
            unbindService(serviceConnection);
        }
    }
}
