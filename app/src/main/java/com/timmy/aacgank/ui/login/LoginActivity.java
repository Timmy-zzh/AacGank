package com.timmy.aacgank.ui.login;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.timmy.aacgank.R;
import com.timmy.aacgank.ui.MainActivity;
import com.timmy.baselib.utils.SpHelper;


/**
 * 登录界面
 * 1。TextInputLayout 使用
 * 2。EditText 输入删除功能
 * 3。虚拟键盘唤起时，顶部Logo往上移动并执行动画
 * 监听键盘显示隐藏：然后进行图标的放大缩小动画
 * 4。登陆按钮Selector点击切换，
 * 5。隐藏密码输入内容
 */
public class LoginActivity extends AppCompatActivity implements
        TextWatcher, CompoundButton.OnCheckedChangeListener {

    private TextInputLayout tilUserName;
    private TextInputLayout tilPassword;
    private Button btnLogin;
    private ConstraintLayout rootView;
    private int recordInvisibleHeight; //记录之前不可见区域的高度
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        handlerKeyBoard();
    }

    private void handlerKeyBoard() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取RootView在窗体的可视区域
                rootView.getWindowVisibleDisplayFrame(rect);
                Log.d("Timmy", rect.left + " " + rect.top + " " + rect.right + " " + rect.bottom);
                //获取不可见区域，被遮挡的控件高度
                int rootInvisibleHeight = rootView.getRootView().getHeight() - rect.bottom;
                Log.d("Timmy", "rootInvisibleHeight:" + rootInvisibleHeight + " ,recordInvisibleHeight:" + recordInvisibleHeight);

                if (Math.abs(rootInvisibleHeight - recordInvisibleHeight) > 200) {
                    if (rootInvisibleHeight > 200) {
                        int[] location = new int[2];
                        //获取控件在窗体的坐标
                        //rootView.getLocationInWindow(location);

                        //键盘显示
                        ivLogo.setVisibility(View.GONE);
                    } else {
                        //键盘隐藏
                        ivLogo.setVisibility(View.VISIBLE);
                    }
                }
                recordInvisibleHeight = rootInvisibleHeight;
            }
        });
    }

    private void initView() {
        rootView = findViewById(R.id.root_view);
        ivLogo = findViewById(R.id.iv_logo);
        tilUserName = findViewById(R.id.til_user_name);
        tilPassword = findViewById(R.id.til_password);
        btnLogin = findViewById(R.id.btn_login);
        CheckBox checkBox = findViewById(R.id.cb_password_show);
        checkBox.setOnCheckedChangeListener(this);
//        TextView tvForgetPw = findViewById(R.id.tv_forget_pw);
//        TextView tvGestureLogin = findViewById(R.id.tv_login_gesture);
        if (tilUserName.getEditText() != null && tilPassword.getEditText() != null) {
            EditText etUserName = tilUserName.getEditText();
            EditText etPassword = tilPassword.getEditText();
//            etUserName.setText("Timmy");
//            etPassword.setText("123456");

            etUserName.addTextChangedListener(this);
            etPassword.addTextChangedListener(this);
            btnLogin.setEnabled(!etUserName.getText().toString().isEmpty()
                    && !etPassword.getText().toString().isEmpty());
        }
    }

    public void login(View view) {
        if (tilUserName.getEditText() != null && tilPassword.getEditText() != null) {
            EditText etUserName = tilUserName.getEditText();
//            if (etUserName.getText().toString().length() != 11) {
//                tilPassword.setError("手机号长度不正确!");
//                return;
//            }
            EditText etPassword = tilPassword.getEditText();
            String password = etPassword.getText().toString().trim();
            if (password.length() >= 4) {
                tilPassword.setErrorEnabled(false);
            } else {
                tilPassword.setErrorEnabled(true);
                tilPassword.setError("密码长度至少4位!");
                return;
            }
        }
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        SpHelper.put("isLogin", true);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void forgetPassword(View view) {
        Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
    }

    public void loginGesture(View view) {
        startActivity(new Intent(this, GestureLoginActivity.class));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (tilUserName.getEditText() != null && tilPassword.getEditText() != null) {
            EditText etUserName = tilUserName.getEditText();
            EditText etPassword = tilPassword.getEditText();
            btnLogin.setEnabled(!etUserName.getText().toString().isEmpty()
                    && !etPassword.getText().toString().isEmpty());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        EditText etPassword = tilPassword.getEditText();
        if (etPassword != null) {
            if (checked) {   // 文本框内容可见
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {          // 文本框内容不可见
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            etPassword.setSelection(etPassword.getText().length());
        }
    }
}
