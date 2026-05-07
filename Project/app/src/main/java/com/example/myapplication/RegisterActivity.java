package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNickname, etPwd, etPwdConfirm, etSignature;
    private Spinner spGender;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 绑定控件
        initView();

        // 注册按钮点击事件
        btnRegister.setOnClickListener(v -> checkRegisterForm());
    }

    // 初始化控件
    private void initView() {
        etNickname = findViewById(R.id.et_nickname);
        etPwd = findViewById(R.id.et_pwd);
        etPwdConfirm = findViewById(R.id.et_pwd_confirm);
        etSignature = findViewById(R.id.et_signature);
        spGender = findViewById(R.id.sp_gender);
        btnRegister = findViewById(R.id.btn_register);
    }

    // 注册表单校验 + 回传信息
    private void checkRegisterForm() {
        String nickname = etNickname.getText().toString().trim(); // 账号用昵称替代
        String pwd = etPwd.getText().toString().trim();
        String pwdConfirm = etPwdConfirm.getText().toString().trim();

        // 1. 不为空检查
        if (nickname.isEmpty()) {
            Toast.makeText(this, "昵称（账号）不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwdConfirm.isEmpty()) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. 密码二次确认匹配检查
        if (!pwd.equals(pwdConfirm)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. 注册成功：回传信息到登录页
        Intent intent = new Intent();
        intent.putExtra("account", nickname); // 账号=昵称
        intent.putExtra("password", pwd);     // 密码
        setResult(RESULT_OK, intent); // 设置返回结果

        // 4. 提示+自动跳转回登录页
        Toast.makeText(this, "注册成功！自动返回登录页", Toast.LENGTH_LONG).show();
        finish(); // 关闭注册页，返回登录页
    }
}