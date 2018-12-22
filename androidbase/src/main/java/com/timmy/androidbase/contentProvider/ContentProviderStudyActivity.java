package com.timmy.androidbase.contentProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.timmy.androidbase.R;
import com.timmy.baselib.base.BaseActivity;

import io.reactivex.functions.Consumer;

/**
 * 内容提供者:
 */
public class ContentProviderStudyActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();
    private TextView tvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        tvContact = findViewById(R.id.tv_contacts);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.d(TAG, "访问联系人权限申请通过");
                        }
                    }
                });

    }

    public void operateMethod1(View view) {
        //设置Uri
        Uri uri = Uri.parse("content://com.timmy.myprovider/user");
        //准备需要操作的数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Groable");

        //获取ContentResolver
        ContentResolver contentResolver = getContentResolver();
        //通过ContentResolver向数据库表中插入数据
        contentResolver.insert(uri, values);

        //获取数据
        Cursor cursor = contentResolver.query(uri, new String[]{"_id", "name"}, null, null, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            Log.d(TAG, "id:" + _id + " ,name:" + name);
        }
        cursor.close();

    }

    /**
     * 获取联系人信息:
     * 注意实际应用中,放在子线程获取
     */
    public void getContacts(View view) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor == null) {
            Log.d(TAG, "获取联系人数据表出错");
            return;
        }
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("ID:");
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            sb.append(contactId);

            sb.append("\t\t");
            sb.append("名字:");
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            sb.append(contactName);

            //根据id获取对应的电话号码

            Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            if (phoneCursor != null) {
                sb.append("\t\t");
                sb.append("号码:");
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    sb.append(phoneNumber);
                }
                sb.append("\n");
                phoneCursor.close();
            }

        }
        cursor.close();
        tvContact.setText(sb.toString());
    }
}