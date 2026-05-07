package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_REGISTER = 1001;

    private EditText etAccount, etPwd;
    private CheckBox cbRememberPwd, cbAutoLogin;
    private Button btnLogin, btnGoRegister;
    private DBHelper dbHelper;

    // SharedPreferences 配置
    private SharedPreferences sp;
    private static final String SP_NAME = "login_config";
    private static final String KEY_REMEMBER_PWD = "remember_pwd";
    private static final String KEY_AUTO_LOGIN = "auto_login";
    private static final String KEY_SAVED_ACCOUNT = "saved_account";
    private static final String KEY_SAVED_PASSWORD = "saved_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化数据库
        dbHelper = new DBHelper(this);

        // 初始化 SharedPreferences
        sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);

        // 绑定控件
        initView();

        // 恢复记住密码的状态
        loadSavedState();

        // 设置自动登录联动：勾选自动登录时，同时勾选记住密码
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRememberPwd.setChecked(true);
                }
            }
        });

        // 1. 登录按钮：验证账号密码后跳转到好友列表
        btnLogin.setOnClickListener(v -> {
            String account = etAccount.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();

            if (account.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(LoginActivity.this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 查询数据库验证
            String dbPwd = dbHelper.getPasswordByAccount(account);
            if (dbPwd == null) {
                Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!dbPwd.equals(pwd)) {
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            }

            // 登录成功：处理记住密码和自动登录状态
            saveLoginState(account, pwd);

            // 跳转到好友列表页
            Intent intent = new Intent(LoginActivity.this, FriendListActivity.class);
            startActivity(intent);
        });

        // 2. 注册按钮：跳转到注册页（带返回结果）
        btnGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, REQUEST_CODE_REGISTER);
        });
    }

    // 从 SharedPreferences 恢复记住密码状态
    private void loadSavedState() {
        boolean rememberPwd = sp.getBoolean(KEY_REMEMBER_PWD, false);
        boolean autoLogin = sp.getBoolean(KEY_AUTO_LOGIN, false);

        cbRememberPwd.setChecked(rememberPwd);
        cbAutoLogin.setChecked(autoLogin);

        if (rememberPwd) {
            String savedAccount = sp.getString(KEY_SAVED_ACCOUNT, "");
            String savedPwd = sp.getString(KEY_SAVED_PASSWORD, "");
            etAccount.setText(savedAccount);
            etPwd.setText(savedPwd);
        }
    }

    // 保存登录状态到 SharedPreferences
    private void saveLoginState(String account, String password) {
        SharedPreferences.Editor editor = sp.edit();

        if (cbRememberPwd.isChecked()) {
            editor.putBoolean(KEY_REMEMBER_PWD, true);
            editor.putString(KEY_SAVED_ACCOUNT, account);
            editor.putString(KEY_SAVED_PASSWORD, password);
        } else {
            editor.putBoolean(KEY_REMEMBER_PWD, false);
            editor.remove(KEY_SAVED_ACCOUNT);
            editor.remove(KEY_SAVED_PASSWORD);
        }

        if (cbAutoLogin.isChecked()) {
            editor.putBoolean(KEY_AUTO_LOGIN, true);
        } else {
            editor.putBoolean(KEY_AUTO_LOGIN, false);
        }

        editor.apply();
    }

    // 3. 接收注册页回传的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RESULT_OK && data != null) {
            String account = data.getStringExtra("account");
            String password = data.getStringExtra("password");
            etAccount.setText(account);
            etPwd.setText(password);
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
