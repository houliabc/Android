package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 注意：新增RESULT_OK的请求码
public class LoginActivity extends AppCompatActivity {

    // 请求码：标识从注册页返回
    private static final int REQUEST_CODE_REGISTER = 1001;

    private EditText etAccount, etPwd;
    private CheckBox cbRememberPwd, cbAutoLogin;
    private Button btnLogin, btnGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 绑定控件
        initView();

        // 1. 登录按钮：跳转到好友列表（无需验证账号密码）
        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();

            // 简单提示（无需验证）
            if (account.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入账号密码（无需验证）", Toast.LENGTH_SHORT).show();
                return;
            }

            // 跳转到好友列表页
            Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
            startActivity(intent);
        });

        // 2. 注册按钮：跳转到注册页（带返回结果）
        btnGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            // 启动注册页，等待返回结果
            startActivityForResult(intent, REQUEST_CODE_REGISTER);
        });
    }

    // 3. 接收注册页回传的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 检查请求码和结果码
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK && data != null) {
            // 获取注册页回传的账号和密码
            String account = data.getStringExtra("account");
            String password = data.getStringExtra("password");

            // 自动填入账号和密码框
            etAccount.setText(account);
            etPwd.setText(password);

            // 提示
            Toast.makeText(this, "已自动填入注册的账号密码", Toast.LENGTH_SHORT).show();
        }
    }

    // 初始化控件
    private void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
        btnLogin = findViewById(R.id.btn_login);
        btnGoRegister = findViewById(R.id.btn_go_register);
    }
}