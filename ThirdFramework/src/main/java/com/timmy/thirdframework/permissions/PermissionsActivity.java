package com.timmy.thirdframework.permissions;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.timmy.baselib.base.BaseActivity;
import com.timmy.thirdframework.R;
import com.timmy.thirdframework.permissions.core.IPermission;
import com.timmy.thirdframework.permissions.core.PermissionUtils;
import com.timmy.thirdframework.permissions.core.TPermissionActivity;

/**
 * 权限申请框架
 * 1。权限申请，开启一个全透明Activity，在该界面权限申请，并处理申请结果返回
 *
 * 2。AOP切面编程实现
 */
public class PermissionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        TPermissionActivity.requestPermission(this, permissions, 100, new IPermission() {
            @Override
            public void ganted() {
                Toast.makeText(PermissionsActivity.this,
                        "ganted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cancled() {
                Toast.makeText(PermissionsActivity.this,
                        "cancled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied() {
                Toast.makeText(PermissionsActivity.this,
                        "denied", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
